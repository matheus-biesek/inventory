package com.closing.inventory.model.material;

import com.closing.inventory.model.father.InventoryItems;
import com.closing.inventory.model.expenditure.ExpenditureCapex;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

@Table(name = "material")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Material extends InventoryItems {

    @OneToMany(mappedBy = "material", cascade = CascadeType.ALL)
    private List<MaterialMovements> materialMovements;

    @OneToMany(mappedBy = "material", cascade = CascadeType.ALL)
    private List<ExpenditureCapex> listMaterialExpenditureCapex;

    public Material(String name, String size, String width, int amount) {
        setName(name);
        setSize(size);
        setWidth(width);
        setAmount(amount);
        this.materialMovements = Collections.emptyList();
    }

}
