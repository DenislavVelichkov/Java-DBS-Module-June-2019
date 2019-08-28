package models;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "visitations")
public class Visitation extends IdDispatchEntity{
    private LocalDateTime visit;
    private String comments;
    private Diagnose diagnose;
    private Patient patient;
    private Set<Medicament> medicaments;

    public Visitation() {
        this.medicaments = new HashSet<>();
    }

    public Visitation(LocalDateTime visit, String comments) {
        this();
        this.visit = visit;
        this.comments = comments;
    }

    @Column(name = "time_of_visit")
    public LocalDateTime getVisit() {
        return visit;
    }

    public void setVisit(LocalDateTime visit) {
        this.visit = visit;
    }

    @Column(name = "comments_about_the_visit")
    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @OneToOne(targetEntity = Diagnose.class, mappedBy = "visitation")
    public Diagnose getDiagnose() {
        return diagnose;
    }

    public void setDiagnose(Diagnose diagnose) {
        this.diagnose = diagnose;
    }

    @OneToOne(targetEntity = Patient.class, mappedBy = "visitation")
    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patients) {
        this.patient = patient;
    }

    @OneToMany(targetEntity = Medicament.class, mappedBy = "visitation")
    public Set<Medicament> getMedicaments() {
        return medicaments;
    }

    public void setMedicaments(Set<Medicament> medicaments) {
        this.medicaments = medicaments;
    }
}
