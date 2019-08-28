package Hibernate_Code_First_9.Lab_Shampoo_company.entities.shampoos;

import Hibernate_Code_First_9.Lab_Shampoo_company.entities.ingredients.BasicIngredient;
import Hibernate_Code_First_9.Lab_Shampoo_company.entities.labels.BasicLabel;


import java.math.BigDecimal;
import java.util.Set;

public interface Shampoo {
    long getId();

    void setId(long id);

    String getBrand();

    void setBrand(String brand);

    BigDecimal getPrice();

    void setPrice(BigDecimal price);

    Size getShampooSize();

    void setSize(Size size);

    BasicLabel getLabel();

    void setLabel(BasicLabel label);

    Set<BasicIngredient> getIngredients();

    void setIngredients(Set<BasicIngredient> ingredients);
}
