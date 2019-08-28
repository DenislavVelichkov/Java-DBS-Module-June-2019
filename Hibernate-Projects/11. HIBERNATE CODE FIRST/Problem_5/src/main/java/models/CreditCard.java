package models;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name = "credit_cards")
@DiscriminatorValue(value = "CC")
public class CreditCard extends BillingDetail {
    private String cardType;
    private String expirationMonth;
    private String expirationYear;

    public CreditCard() {
    }

    public CreditCard(String billingDetail, User owner, String cardType, String expirationMonth, String expirationYear) {
        super(billingDetail, owner);
        this.cardType = cardType;
        this.expirationMonth = expirationMonth;
        this.expirationYear = expirationYear;
    }

    @Column(name = "card_type")
    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    @Column(name = "expiration_month")
    public String getExpirationMonth() {
        return expirationMonth;
    }

    public void setExpirationMonth(String expirationMonth) {
        this.expirationMonth = expirationMonth;
    }

    @Column(name = "expiration_year")
    public String getGetExpirationYear() {
        return expirationYear;
    }

    public void setGetExpirationYear(String getExpirationYear) {
        this.expirationYear = getExpirationYear;
    }
}
