package Hibernate_Code_First_9.Lab_Shampoo_company.entities.labels;

import java.io.Serializable;

public interface Label extends Serializable {
    long getId();

    void setId(long id);

    String getTitle();

    void setTitle(String title);

    String getSubtitle();

    void setSubtitle(String subtitle);
}
