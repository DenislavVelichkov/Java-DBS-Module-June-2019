package models;

import javax.persistence.*;

@Entity(name = "billing_details")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "billing_type", discriminatorType = DiscriminatorType.STRING)
public abstract class BillingDetail extends BaseEntity{
    private String billingDetail;
    private User owner;

    public BillingDetail() {
    }

    public BillingDetail(String billingDetail, User owner) {
        this.billingDetail = billingDetail;
        this.owner = owner;
    }

    @Column(name = "billing_detail")
    public String getBillingDetail() {
        return billingDetail;
    }

    public void setBillingDetail(String billingDetail) {
        this.billingDetail = billingDetail;
    }

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(referencedColumnName = "id")
    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
