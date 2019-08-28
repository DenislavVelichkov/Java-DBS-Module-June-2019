package app.ccb.services;

import app.ccb.domain.dtos.xml.BankAccountRootDto;
import app.ccb.domain.dtos.xml.BankAccountSeedDto;
import app.ccb.domain.entities.BankAccount;
import app.ccb.domain.entities.Client;
import app.ccb.repositories.BankAccountRepository;
import app.ccb.repositories.ClientRepository;
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
public class BankAccountServiceImpl implements BankAccountService {
    private static final String BANK_ACCOUNT_XML_FILE_PATH =
            System.getProperty("user.dir") +
                    "/src/main/resources/files/xml/bank-accounts.xml";
    private final BankAccountRepository bankAccountRepository;
    private final FileUtil fileUtil;
    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final ClientRepository clientRepository;


    @Autowired
    public BankAccountServiceImpl(BankAccountRepository bankAccountRepository,
                                  FileUtil fileUtil, XmlParser xmlParser,
                                  ValidationUtil validationUtil,
                                  ModelMapper modelMapper,
                                  ClientRepository clientRepository) {
        this.bankAccountRepository = bankAccountRepository;
        this.fileUtil = fileUtil;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.clientRepository = clientRepository;
    }

    @Override
    public Boolean bankAccountsAreImported() {
        return this.bankAccountRepository.count() != 0;
    }

    @Override
    public String readBankAccountsXmlFile() throws JAXBException, IOException {
        return this.fileUtil.readFile(BANK_ACCOUNT_XML_FILE_PATH);
    }

    @Override
    public String importBankAccounts() throws JAXBException, FileNotFoundException {
        StringBuilder sb = new StringBuilder();
        BankAccountRootDto bankAccountRootDtos =
                this.xmlParser.parseXml(
                        BankAccountRootDto.class, BANK_ACCOUNT_XML_FILE_PATH);
        for (BankAccountSeedDto bankAccountSeedDto : bankAccountRootDtos.getCard()) {
            if (!this.validationUtil.isValid(bankAccountSeedDto)) {
                System.out.println("Error: Incorrect Data!");

                continue;
            }

            BankAccount bankAccount =
                    this.bankAccountRepository
                            .findByAccountNumber(bankAccountSeedDto.getAccountNumber())
                            .orElse(null);

            if (bankAccount == null) {
                Client client =
                        this.clientRepository
                                .findByFullName(bankAccountSeedDto.getClientName())
                                .orElse(null);
                if (client == null) {
                    System.out.println("Client not found!");

                    continue;
                }

                bankAccount = this.modelMapper.map(bankAccountSeedDto, BankAccount.class);
                bankAccount.setClient(client);
                this.bankAccountRepository.saveAndFlush(bankAccount);
                sb.append(String.format("Successfully imported Bank account - %s.",
                        bankAccountSeedDto.getAccountNumber()))
                        .append(System.lineSeparator());
            }
        }

        return sb.toString();
    }
}
