package Hibernate_Code_First_9.Lab_Shampoo_company.entities.shampoos;

import Hibernate_Code_First_9.Lab_Shampoo_company.entities.labels.BasicLabel;

import java.math.BigDecimal;

public class PinkPanther extends BasicShampoo{
    private static final String BRAND = "Pink Panther";
    private static final BigDecimal PRICE = new BigDecimal("8.50");
    private static final Size SIZE = Size.MEDIUM;

    public PinkPanther(BasicLabel label) {
        super(PRICE, BRAND, SIZE, label);
    }
}
