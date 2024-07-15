package com.closing.inventory.service.sale;

import com.closing.inventory.model.product.Product;
import com.closing.inventory.model.sale.Sale;
import com.closing.inventory.repository.product.ProductRepository;
import com.closing.inventory.repository.sale.SaleRepository;
import com.closing.inventory.service.product.ProductMovementsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SaleService {

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMovementsService productMovementsService;

    public String create(Sale saleSend){
        if (!(productRepository.existsByNameAndSizeAndWidth(saleSend.getProduct().getName(), saleSend.getProduct().getSize(), saleSend.getProduct().getWidth()))){
            return "Não é possível realizar a venda, pois o produto não se encontra no banco de dados!";
        }
        Product productFound = productRepository.findByNameAndSizeAndWidth(saleSend.getProduct().getName(), saleSend.getProduct().getSize(), saleSend.getProduct().getWidth());
        Sale sale = new Sale(productFound, saleSend.getValue(), saleSend.getAmount(), saleSend.getObservation());
        if (productFound.getAmount() < -sale.getAmount()){
            return "Não é possível realizar está ação!\nO produto encontrado possui: " + productFound.getAmount() + " Un.\nVocê está vender: " + sale.getAmount() + " Un.\nOu seja:\nA quantidade do produto ficará negativa.";
        }
        productFound.setAmount(productFound.getAmount() + saleSend.getAmount());
        productRepository.save(productFound);
        saleRepository.save(sale);
        productMovementsService.registerMovements(productFound, saleSend.getAmount());
        return "Venda adiciona ao banco de dados com sucesso!";
    }

    public String StringListAll() {
        List<Sale> saleList = saleRepository.findAll();
        return saleList.stream()
                .sorted(Comparator.comparing(Sale::getLocalDateTime).reversed())
                .map(sale -> "Nome: " + sale.getProduct().getName() + ", Tamanho " + sale.getProduct().getSize() + "Cm, Largura: " + sale.getProduct().getWidth() + "Mm\nvalor R$: " + sale.getValue() + "\nQuantidade vendida: " + sale.getAmount() + "\nObservação: " + sale.getObservation() + "\nData: " + sale.getLocalDateTime() + "\n-----------------------------------")
                .collect(Collectors.joining("\n"));
    }

    public String StringListStock() {
        List<Sale> SaleList = saleRepository.findAllNonRepeatingEntities();
        return SaleList.stream()
                .sorted(Comparator.comparing(Sale::getLocalDateTime).reversed())
                .map(sale -> "Produto: " + sale.getProduct().getName() + " Tamanho: " + sale.getProduct().getSize() + " Cm, Largura: " + sale.getProduct().getWidth() + "\n-----------------------------------")
                .collect(Collectors.joining("\n"));
    }
}
