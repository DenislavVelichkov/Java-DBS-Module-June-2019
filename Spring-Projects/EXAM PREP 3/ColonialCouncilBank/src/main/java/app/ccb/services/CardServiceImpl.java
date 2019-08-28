package app.ccb.services;

import app.ccb.domain.dtos.xml.CardRootDto;
import app.ccb.domain.dtos.xml.CardSeedDto;
import app.ccb.domain.entities.BankAccount;
import app.ccb.domain.entities.Card;
import app.ccb.repositories.BankAccountRepository;
import app.ccb.repositories.CardRepository;
import app.ccb.util.FileUtil;
import app.ccb.util.ValidationUtil;
import app.ccb.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;

@Service
public class CardServiceImpl implements CardService {
    private static final String CARDS_XML_FILE_PATH =
            System.getProperty("user.dir") +
                    "/src/main/resources/files/xml/cards.xml";
    private final FileUtil fileUtil;
    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final CardRepository cardRepository;
    private final BankAccountRepository bankAccountRepository;

    @Autowired
    public CardServiceImpl(FileUtil fileUtil,
                           XmlParser xmlParser,
                           ValidationUtil validationUtil, ModelMapper modelMapper,
                           CardRepository cardRepository, BankAccountRepository bankAccountRepository) {
        this.fileUtil = fileUtil;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.cardRepository = cardRepository;
        this.bankAccountRepository = bankAccountRepository;
    }

    @Override
    public Boolean cardsAreImported() {
        return this.cardRepository.count() != 0;
    }

    @Override
    public String readCardsXmlFile() throws IOException {
        return this.fileUtil.readFile(CARDS_XML_FILE_PATH);
    }

    @Override
    public String importCards() throws JAXBException, FileNotFoundException {
        StringBuilder sb = new StringBuilder();
        CardRootDto bankAccountRootDtos =
                this.xmlParser.parseXml(
                        CardRootDto.class, CARDS_XML_FILE_PATH);
        for (CardSeedDto cardSeedDto : bankAccountRootDtos.getCards()) {
            if (!this.validationUtil.isValid(cardSeedDto)) {
                System.out.println("Error: Incorrect Data!");

                continue;
            }

            Card card =
                    this.cardRepository
                            .findByCardNumber(cardSeedDto.getCardNumber())
                            .orElse(null);
            if (card == null) {
                card = this.modelMapper.map(cardSeedDto, Card.class);
                BankAccount bankAccount =
                        this.bankAccountRepository
                                .findByAccountNumber(cardSeedDto.getAccountNumber())
                                .orElse(null);

                if (bankAccount == null) {
                    System.out.println("Bank account number missing!");

                    continue;
                }

                card.setBankAccount(bankAccount);
                this.cardRepository.saveAndFlush(card);
                sb.append(String.format("Successfully imported Card - %s.",
                        cardSeedDto.getCardNumber()))
                        .append(System.lineSeparator());
            }
        }
        return sb.toString();
    }
}
