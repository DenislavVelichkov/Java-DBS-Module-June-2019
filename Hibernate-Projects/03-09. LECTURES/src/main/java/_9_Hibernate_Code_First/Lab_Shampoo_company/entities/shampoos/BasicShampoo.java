package Hibernate_Code_First_9.Lab_Shampoo_company.entities.shampoos;

import Hibernate_Code_First_9.Lab_Shampoo_company.entities.ingredients.BasicIngredient;
import Hibernate_Code_First_9.Lab_Shampoo_company.entities.labels.BasicLabel;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
public class BasicShampoo implements Shampoo {

    @Id
    private long id;

    private BigDecimal price;

    private String brand;

    private Size size;

    @OneToOne
    private BasicLabel label;

    @ManyToMany
    private Set<BasicIngredient> ingredients;


    protected BasicShampoo() {
        this.ingredients = new HashSet<>();
    }

    public BasicShampoo(BigDecimal price, String brand, Size size, BasicLabel classicLabel) {
        this();
        this.price = price;
        this.brand = brand;
        this.size = size;
        this.label = classicLabel;
    }

    @Override
    public long getId() {
        return this.id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public BigDecimal getPrice() {
        return this.price;
    }

    @Override
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public Size getShampooSize() {
        return this.size;
    }

    @Override
    public String getBrand() {
        return this.brand;
    }

    @Override
    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Size getSize() {
        return this.size;
    }

    @Override
    public void setSize(Size size) {
        this.size = size;
    }

    @Override
    public Set<BasicIngredient> getIngredients() {
        return this.ingredients;
    }

    @Override
    public void setIngredients(Set<BasicIngredient> ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public BasicLabel getLabel() {
        return this.label;
    }

    @Override
    public void setLabel(BasicLabel label) {
        this.label = label;
    }
}
