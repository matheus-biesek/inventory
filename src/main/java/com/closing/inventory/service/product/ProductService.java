package com.closing.inventory.service.product;

import com.closing.inventory.dto.ProductMovementsRequestDTO;
import com.closing.inventory.dto.ProductRequestDTO;
import com.closing.inventory.infra.security.TokenService;
import com.closing.inventory.model.product.Product;
import com.closing.inventory.model.product.ProductMovements;
import com.closing.inventory.model.sale.Sale;
import com.closing.inventory.model.user.User;
import com.closing.inventory.repository.product.ProductRepository;
import com.closing.inventory.repository.sale.SaleRepository;
import com.closing.inventory.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final SaleRepository saleRepository;
    private final ProductMovementsService productMovementsService;
    private final TokenService tokenService;
    private final UserRepository userRepository;

    public String stringListStock() {
        List<Product> listProducts = this.productRepository.findAll();
        if (listProducts.isEmpty()) return "Não há produtos no estoque!";
        return listProducts.stream()
                .map(product -> "Produto: " + product.getName() + ", Tamanho " + product.getSize() + " Cm , Largura: " + product.getWidth() + ", Mm\nQtd atual: " + product.getAmount() +" Un\n-----------------------------------")
                .collect(Collectors.joining("\n"));
    }

    public String create(ProductRequestDTO body) {
        if (this.productRepository.existsByNameAndSizeAndWidth(body.name(), body.size(), body.width())) return "O produto ja existe no banco de dados!";
        if (body.token() != null){
            var email = this.tokenService.validateToken(body.token());
            User user = this.userRepository.findByUsername(email).orElseThrow(() -> new RuntimeException("User not found"));
            if (user != null){
                Product product = new Product(body.name(), body.size(), body.width(), 0);
                this.productRepository.save(product);
                this.productMovementsService.registerMovements(product, 0, user);
                return "Produto adicionado com sucesso!";
            }
        }
        return "Não é possivel realizar esta operação pois seu token esta invalido!";
    }

    public String stringListMovements(ProductRequestDTO body) {
        Product productFound = this.productRepository.findByNameAndSizeAndWidth(body.name(), body.size(), body.width());
        if (productFound == null) return "O produto não existe no banco de dados!";
        List<ProductMovements> movements = this.productMovementsService.findListProduct(productFound);
        String movementsString = movements.stream()
                .sorted(Comparator.comparing(ProductMovements::getLocalDateTime).reversed())
                .map(mov -> "-----------------------------------\nData: " + mov.getLocalDateTime() + " - Qtd: " + mov.getAmount() + " - Usúario: " + mov.getUser().getUsername())
                .collect(Collectors.joining("\n"));
        return "Produto: " + productFound.getName() + ", Tamanho: " + productFound.getSize() + "Cm, Largura: " + productFound.getWidth() + "Mm\n Qtd atual: " + productFound.getAmount() + "Un\n" + "Histórico de movimentações:\n" + movementsString;
    }

    public String addQuantity(ProductMovementsRequestDTO body){
        Product productFound = this.productRepository.findByNameAndSizeAndWidth(body.name(), body.size(), body.width());
        if (productFound == null) return "O produto não existe no banco de dados!";
        if (body.token() != null) {
            var email = this.tokenService.validateToken(body.token());
            User user = this.userRepository.findByUsername(email).orElseThrow(() -> new RuntimeException("User not found"));
            if (user != null) {
                productFound.setAmount(productFound.getAmount() + body.amount());
                this.productRepository.save(productFound);
                this.productMovementsService.registerMovements(productFound, body.amount(), user);
                return "Movimentação concluida com sucesso!";
            }
        }
        return "Não é possivel realizar esta operação pois seu token esta invalido!";
    }

    public String stringListAllSale(ProductRequestDTO body) {
        Product productFound = this.productRepository.findByNameAndSizeAndWidth(body.name(), body.size(), body.width());
        if (productFound == null) return "O produto não existe no banco de dados!";
        List<Sale> listSale = this.saleRepository.findByProduct(productFound);
        String listSaleString = listSale.stream()
                .sorted(Comparator.comparing(Sale::getLocalDateTime).reversed())
                .map(sale ->  "-----------------------------------\nValor: R$" + sale.getValue() + "\nQtd vendida: " + sale.getAmount() + "Un\nObservação: " + sale.getObservation() + "\nData: " + sale.getLocalDateTime() + "\nUsúario: " + sale.getUser().getUsername())
                .collect(Collectors.joining("\n"));
        return "Produto: " + productFound.getName() + ", Tamanho " + productFound.getSize() + " Cm, Largura: " + productFound.getWidth() + "Mm\n" + listSaleString;
    }
}
