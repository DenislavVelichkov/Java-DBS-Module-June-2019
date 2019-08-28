package Hibernate_Code_First_9.Lab_Shampoo_company.entities.ingredients;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
@DiscriminatorValue(value = "AM")
public class AmmoniumChloride extends BasicChemicalIngredient {
    private static final String NAME = "Ammonium Chloride";
    private static final BigDecimal PRICE = new BigDecimal("0.59");
    private static final String CHEMICAL_FORMULA = "NH4C1";

    public AmmoniumChloride() {
        super(AmmoniumChloride.NAME, AmmoniumChloride.PRICE, AmmoniumChloride.CHEMICAL_FORMULA);
    }

    public String getName() {
        return super.getName();
    }

    public BigDecimal getPrice() {
        return super.getPrice();
    }

    @Override
    public void setChemicalFormula(String chemicalFormula) {
        super.setChemicalFormula(chemicalFormula);
    }

    @Override
    public String getChemicalFormula() {
        return super.getChemicalFormula();
    }
}
