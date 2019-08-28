package Hibernate_Code_First_9.Lab_Shampoo_company.entities.ingredients;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
@DiscriminatorValue(value = "NT")
public class Nettle extends BasicIngredient {
    private static final String NAME = "Nettle";
    private static final BigDecimal PRICE = new BigDecimal("6.12");

    public Nettle() {
        super(Nettle.NAME, Nettle.PRICE);
    }
}
