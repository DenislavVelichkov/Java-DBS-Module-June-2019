package Hibernate_Code_First_9.Lab_Shampoo_company.entities.ingredients;

public interface ChemicalIngredient extends Ingredient {
    void setChemicalFormula(String chemicalFormula);

    String getChemicalFormula();
}
