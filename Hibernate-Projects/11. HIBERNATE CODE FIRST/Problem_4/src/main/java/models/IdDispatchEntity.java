package models;

import javax.persistence.*;

@MappedSuperclass
public abstract class IdDispatchEntity {
    private Integer id;

    public IdDispatchEntity() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
