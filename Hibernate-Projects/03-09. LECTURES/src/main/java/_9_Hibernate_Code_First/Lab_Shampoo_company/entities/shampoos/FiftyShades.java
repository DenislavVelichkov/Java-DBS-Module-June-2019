package Hibernate_Code_First_9.Lab_Shampoo_company.entities.shampoos;

import Hibernate_Code_First_9.Lab_Shampoo_company.entities.labels.BasicLabel;

import java.math.BigDecimal;

public class FiftyShades extends BasicShampoo{
    private static final String BRAND = "Fifty Shades ";
    private static final BigDecimal PRICE = new BigDecimal("6.69");
    private static final Size SIZE = Size.SMALL;

    public FiftyShades(BasicLabel label) {
        super(PRICE, BRAND, SIZE, label);
    }
}
