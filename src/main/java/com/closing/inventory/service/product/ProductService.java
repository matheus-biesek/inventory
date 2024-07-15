package com.closing.inventory.service.product;

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
        if (listProducts.isEmpty()) {
            return "Não há produtos no estoque!";
        }
        return listProducts.stream()
                .map(piteira -> "Produto: " + piteira.getName() + ", Tamanho " + piteira.getSize() + " Cm , Largura: " + piteira.getWidth() + ", Mm\nQtd atual: " + piteira.getAmount() +" Un\n-----------------------------------")
                .collect(Collectors.joining("\n"));
    }

    public String create(Product productSend) {
        if (productRepository.existsByNameAndSizeAndWidth(productSend.getName(), productSend.getSize(), productSend.getWidth())) {
            return "O produto ja existe no banco de dados!";
        }
        productRepository.save(productSend);
        productMovementsService.registerMovements(productSend, 0);
        return "Produto adicionado com sucesso!";
    }

    public String stringListMovements(Product productSent) {
        Product productFound = productRepository.findByNameAndSizeAndWidth(productSent.getName(), productSent.getSize(), productSent.getWidth());
        if (productFound == null) {
            return "O produto encontrado está nulo no banco de dados!";
        }
        List<ProductMovements> movements = productMovementsService.findListProduct(productFound);
        String movementsString = movements.stream()
                .sorted(Comparator.comparing(ProductMovements::getLocalDateTime).reversed())
                .map(mov -> "-----------------------------------\nData: " + mov.getLocalDateTime() + " - Qtd: " + mov.getMovementsAmount())
                .collect(Collectors.joining("\n"));
        return "Produto: " + productFound.getName() + ", Tamanho: " + productFound.getSize() + "Cm, Largura: " + productFound.getWidth() + "Mm\n Qtd atual: " + productFound.getAmount() + "Un\n" + "Histórico de movimentações:\n" + movementsString;
    }

    public String appendQuantity(Product productSent){
        Product productFound = productRepository.findByNameAndSizeAndWidth(productSent.getName(), productSent.getSize(), productSent.getWidth());
        if (productFound == null) {
            return "O produto encontrado está nulo no banco de dados!";
        }
        productFound.setAmount(productFound.getAmount() + productSent.getAmount());
        productRepository.save(productFound);
        productMovementsService.registerMovements(productFound, productSent.getAmount());
        return "Movimentação concluida com sucesso!";
    }

    public String stringListAllSale(Product productSent) {
        Product productFound = productRepository.findByNameAndSizeAndWidth(productSent.getName(), productSent.getSize(), productSent.getWidth());
        if (productFound == null) {
            return "O produto encontrado está nulo no banco de dados!";
        }
        List<Sale> listSale = saleRepository.findByProduct(productFound);
        String listSaleString = listSale.stream()
                .sorted(Comparator.comparing(Sale::getLocalDateTime).reversed())
                .map(sale ->  "-----------------------------------\nValor: R$" + sale.getValue() + "\nQtd vendida: " + sale.getAmount() + "Un\nObservação: " + sale.getObservation() + "\nData: " + sale.getLocalDateTime())
                .collect(Collectors.joining("\n"));
        return "Produto: " + productFound.getName() + ", Tamanho " + productFound.getSize() + " Cm, Largura: " + productFound.getWidth() + "Mm\n" + listSaleString;
    }
}
