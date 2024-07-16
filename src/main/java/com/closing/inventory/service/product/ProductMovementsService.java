package com.closing.inventory.service.product;

import com.closing.inventory.model.product.Product;
import com.closing.inventory.model.product.ProductMovements;
import com.closing.inventory.model.user.User;
import com.closing.inventory.repository.product.ProductMovementsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductMovementsService {

    private final ProductMovementsRepository productMovementsRepository;

    public void registerMovements(Product product, int amount, User user){
        ProductMovements productMovements = new ProductMovements(product, amount, LocalDateTime.now(), user);
        this.productMovementsRepository.save(productMovements);
    }

    public List<ProductMovements> findListProduct(Product product) {
        return this.productMovementsRepository.findByProduct(product);
    }

    public String stringListAll(){
        List<ProductMovements> productMovements = this.productMovementsRepository.findAll();
        return productMovements.stream()
                .sorted(Comparator.comparing(ProductMovements::getLocalDateTime).reversed())
                .map(mov ->  "Produto: " + mov.getProduct().getName() + " Tamanho: " + mov.getProduct().getSize() + " Cm, Largura: " + mov.getProduct().getWidth() + "Mm\nMovimentação: " + mov.getAmount() + "\nData: " + mov.getLocalDateTime() + "\nUsúario: " + mov.getUser().getUsername() + "\n-----------------------------------")
                .collect(Collectors.joining("\n"));
    }
}
