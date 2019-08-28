package Hibernate_Code_First_9.Lab_Shampoo_company.entities.shampoos;

import Hibernate_Code_First_9.Lab_Shampoo_company.entities.labels.BasicLabel;

import java.math.BigDecimal;

public class FreshNuke extends BasicShampoo {
    private static final String BRAND = "Fresh Nuke";
    private static final BigDecimal PRICE = new BigDecimal("9.33");
    private static final Size SIZE = Size.BIG;

    public FreshNuke(BasicLabel label) {
        super(PRICE, BRAND, SIZE, label);
    }

}
