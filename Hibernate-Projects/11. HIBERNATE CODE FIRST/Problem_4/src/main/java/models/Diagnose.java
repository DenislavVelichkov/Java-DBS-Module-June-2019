package models;

import javax.persistence.*;

@Entity
@Table(name = "diagnoses")
public class Diagnose extends IdDispatchEntity {
    private String name;
    private String comments;
    private Visitation visitation;

    public Diagnose() {
    }

    @Column(name = "diagnose_name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "diagnose_comments")
    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @OneToOne(targetEntity = Visitation.class)
    @JoinColumn(name = "visitation_id", referencedColumnName = "id")
    public Visitation getVisitation() {
        return visitation;
    }

    public void setVisitation(Visitation visitation) {
        this.visitation = visitation;
    }
}
