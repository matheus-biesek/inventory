package com.closing.inventory.model.expenditure;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
// is better to change this class for record.
public class ExpenditureSend {

    private String name;

    private String size;

    private String width;

    private BigDecimal value;

    private String observation;

    private int amount;
}
