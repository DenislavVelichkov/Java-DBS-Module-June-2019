package app.ccb.domain.dtos.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement(name = "bank-accounts")
@XmlAccessorType(XmlAccessType.FIELD)
public class BankAccountRootDto implements Serializable {

    @XmlElement(name = "bank-account")
    private BankAccountSeedDto[] accounts;

    public BankAccountRootDto() {
    }

    public BankAccountSeedDto[] getCard() {
        return accounts;
    }

    public void setCard(BankAccountSeedDto[] cards) {
        this.accounts = cards;
    }
}
