package app.ccb.domain.dtos.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement(name = "cards")
@XmlAccessorType(XmlAccessType.FIELD)
public class CardRootDto implements Serializable {

    @XmlElement(name = "card")
    private CardSeedDto[] cards;

    public CardRootDto() {
    }

    public CardSeedDto[] getCards() {
        return cards;
    }

    public void setCards(CardSeedDto[] cards) {
        this.cards = cards;
    }
}
