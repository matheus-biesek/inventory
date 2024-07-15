package com.closing.inventory.service.product;

import com.closing.inventory.model.product.Product;
import com.closing.inventory.model.product.ProductMovements;
import com.closing.inventory.repository.product.ProductMovementsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductMovementsService {

    @Autowired
    private ProductMovementsRepository productMovementsRepository;

    public void registerMovements(Product product, double amount){
        ProductMovements productMovements = new ProductMovements(product, LocalDateTime.now(), amount);
        productMovementsRepository.save(productMovements);
    }

    public List<ProductMovements> findListProduct(Product product) {
        return productMovementsRepository.findByProduct(product);
    }

    public String stringListAll(){
        List<ProductMovements> productMovements = productMovementsRepository.findAll();
        return productMovements.stream()
                .sorted(Comparator.comparing(ProductMovements::getLocalDateTime).reversed())
                .map(mov ->  "Produto: " + mov.getProduct().getName() + " Tamanho: " + mov.getProduct().getSize() + " Cm, Largura: " + mov.getProduct().getWidth() + "Mm\nMovimentação: " + mov.getMovementsAmount() + "\nData: " + mov.getLocalDateTime() + "\n-----------------------------------")
                .collect(Collectors.joining("\n"));
    }
}
