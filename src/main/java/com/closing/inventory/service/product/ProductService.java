package com.closing.inventory.service.product;

import com.closing.inventory.dto.ProductMovementsRequestDTO;
import com.closing.inventory.dto.ProductRequestDTO;
import com.closing.inventory.model.product.Product;
import com.closing.inventory.model.product.ProductMovements;
import com.closing.inventory.model.sale.Sale;
import com.closing.inventory.repository.product.ProductRepository;
import com.closing.inventory.repository.sale.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private ProductMovementsService productMovementsService;

    public String stringListStock() {
        List<Product> listProducts = productRepository.findAll();
        if (listProducts.isEmpty()) return "Não há produtos no estoque!";
        return listProducts.stream()
                .map(product -> "Produto: " + product.getName() + ", Tamanho " + product.getSize() + " Cm , Largura: " + product.getWidth() + ", Mm\nQtd atual: " + product.getAmount() +" Un\n-----------------------------------")
                .collect(Collectors.joining("\n"));
    }

    public String create(ProductRequestDTO body) {
        if (productRepository.existsByNameAndSizeAndWidth(body.name(), body.size(), body.width())) return "O produto ja existe no banco de dados!";
        Product product = new Product(body.name(), body.size(), body.width(), 0);
        productRepository.save(product);
        productMovementsService.registerMovements(product, 0);
        return "Produto adicionado com sucesso!";
    }

    public String stringListMovements(ProductRequestDTO body) {
        Product productFound = productRepository.findByNameAndSizeAndWidth(body.name(), body.size(), body.width());
        if (productFound == null) return "O produto não existe no banco de dados!";
        List<ProductMovements> movements = productMovementsService.findListProduct(productFound);
        String movementsString = movements.stream()
                .sorted(Comparator.comparing(ProductMovements::getLocalDateTime).reversed())
                .map(mov -> "-----------------------------------\nData: " + mov.getLocalDateTime() + " - Qtd: " + mov.getAmount())
                .collect(Collectors.joining("\n"));
        return "Produto: " + productFound.getName() + ", Tamanho: " + productFound.getSize() + "Cm, Largura: " + productFound.getWidth() + "Mm\n Qtd atual: " + productFound.getAmount() + "Un\n" + "Histórico de movimentações:\n" + movementsString;
    }

    public String addQuantity(ProductMovementsRequestDTO body){
        Product productFound = productRepository.findByNameAndSizeAndWidth(body.name(), body.size(), body.width());
        if (productFound == null) return "O produto não existe no banco de dados!";
        productFound.setAmount(productFound.getAmount() + body.amount());
        productRepository.save(productFound);
        productMovementsService.registerMovements(productFound, body.amount());
        return "Movimentação concluida com sucesso!";
    }

    public String stringListAllSale(ProductRequestDTO body) {
        Product productFound = productRepository.findByNameAndSizeAndWidth(body.name(), body.size(), body.width());
        if (productFound == null) return "O produto não existe no banco de dados!";
        List<Sale> listSale = saleRepository.findByProduct(productFound);
        String listSaleString = listSale.stream()
                .sorted(Comparator.comparing(Sale::getLocalDateTime).reversed())
                .map(sale ->  "-----------------------------------\nValor: R$" + sale.getValue() + "\nQtd vendida: " + sale.getAmount() + "Un\nObservação: " + sale.getObservation() + "\nData: " + sale.getLocalDateTime())
                .collect(Collectors.joining("\n"));
        return "Produto: " + productFound.getName() + ", Tamanho " + productFound.getSize() + " Cm, Largura: " + productFound.getWidth() + "Mm\n" + listSaleString;
    }
}
