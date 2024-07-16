package com.closing.inventory.service.sale;

import com.closing.inventory.dto.SaleRequestDTO;
import com.closing.inventory.infra.security.TokenService;
import com.closing.inventory.model.product.Product;
import com.closing.inventory.model.sale.Sale;
import com.closing.inventory.model.user.User;
import com.closing.inventory.repository.product.ProductRepository;
import com.closing.inventory.repository.sale.SaleRepository;
import com.closing.inventory.repository.user.UserRepository;
import com.closing.inventory.service.product.ProductMovementsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SaleService {

    private final SaleRepository saleRepository;
    private final ProductRepository productRepository;
    private final ProductMovementsService productMovementsService;
    private final TokenService tokenService;
    private final UserRepository userRepository;

    public String create(SaleRequestDTO body){
        Product productFound = this.productRepository.findByNameAndSizeAndWidth(body.name(), body.size(), body.width());
        if (productFound == null) return "Não é possível realizar a venda, pois o produto não se encontra no banco de dados!";
        BigDecimal convertedValue = new BigDecimal(body.value().replace(",", "."));

        if (body.token() != null) {
            var email = this.tokenService.validateToken(body.token());
            User user = this.userRepository.findByUsername(email).orElseThrow(() -> new RuntimeException("User not found"));
            if (user != null) {
                Sale sale = new Sale(productFound, convertedValue, body.amount(), body.observation(), user);
                if (productFound.getAmount() < sale.getAmount()) return "Não é possível realizar está ação!\n" + "O produto encontrado possui: " + productFound.getAmount() + " Un.\n" + "Você está vender: " + sale.getAmount() + " Un.\n" + "Ou seja:\n" + "A quantidade do produto ficará negativa.";
                productFound.setAmount(productFound.getAmount() - sale.getAmount());
                this.productRepository.save(productFound);
                this.saleRepository.save(sale);
                this.productMovementsService.registerMovements(productFound, -sale.getAmount(), user);
                return "Venda adiciona ao banco de dados com sucesso!";
            }
        }
        return "Não é possivel realizar esta operação pois seu token esta invalido!";
    }

    public String StringListAll() {
        List<Sale> listSale = this.saleRepository.findAll();
        return listSale.stream()
                .sorted(Comparator.comparing(Sale::getLocalDateTime).reversed())
                .map(sale -> "Nome: " + sale.getProduct().getName() + ", Tamanho " + sale.getProduct().getSize() + "Cm, Largura: " + sale.getProduct().getWidth() + "Mm\nvalor R$: " + sale.getValue() + "\nQuantidade vendida: " + sale.getAmount() + "\nObservação: " + sale.getObservation() + "\nData: " + sale.getLocalDateTime() + "\nUsúario: " + sale.getUser().getUsername() +  "\n-----------------------------------")
                .collect(Collectors.joining("\n"));
    }

    public String StringListStock() {
        List<Sale> listStock = this.saleRepository.findAllNonRepeatingEntities();
        return listStock.stream()
                .sorted(Comparator.comparing(Sale::getLocalDateTime).reversed())
                .map(sale -> "Produto: " + sale.getProduct().getName() + " Tamanho: " + sale.getProduct().getSize() + " Cm, Largura: " + sale.getProduct().getWidth() + "\n-----------------------------------")
                .collect(Collectors.joining("\n"));
    }
}
