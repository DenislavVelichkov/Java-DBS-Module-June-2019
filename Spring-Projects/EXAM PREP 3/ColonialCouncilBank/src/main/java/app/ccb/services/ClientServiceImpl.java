package app.ccb.services;

import app.ccb.domain.dtos.json.ClientSeedDto;
import app.ccb.domain.entities.Client;
import app.ccb.domain.entities.Employee;
import app.ccb.repositories.ClientRepository;
import app.ccb.repositories.EmployeeRepository;
import app.ccb.util.FileUtil;
import app.ccb.util.GsonParser;
import app.ccb.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
public class ClientServiceImpl implements ClientService {
    private static final String CLIENTS_JSON_FILE_PATH =
            System.getProperty("user.dir") +
                    "/src/main/resources/files/json/clients.json";
    private final ClientRepository clientRepository;
    private final FileUtil fileUtil;
    private final GsonParser gsonParser;
    private final ValidationUtil validationUtil;
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository,
                             FileUtil fileUtil,
                             GsonParser gsonParser,
                             ValidationUtil validationUtil,
                             EmployeeRepository employeeRepository,
                             ModelMapper modelMapper) {
        this.clientRepository = clientRepository;
        this.fileUtil = fileUtil;
        this.gsonParser = gsonParser;
        this.validationUtil = validationUtil;
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Boolean clientsAreImported() {
        return this.clientRepository.count() != 0;
    }

    @Override
    public String readClientsJsonFile() throws IOException {
        return this.fileUtil.readFile(CLIENTS_JSON_FILE_PATH);
    }

    @Override
    @Transactional
    public String importClients(String clients) {
        StringBuilder sb = new StringBuilder();
        ClientSeedDto[] clientSeedDtos =
                this.gsonParser.parseDataFromJson(clients, ClientSeedDto[].class);

        TypeMap<ClientSeedDto, Client> clientMap =
                this.modelMapper.createTypeMap(ClientSeedDto.class, Client.class);

        clientMap.addMappings(m -> m.skip(Client::setFullName));

        clientMap.addMappings(m -> m.map(ClientSeedDto::getAge, Client::setAge));

        clientMap.addMappings(m -> m.skip(Client::setEmployees));

        clientMap.addMappings(m -> m.skip(Client::setBankAccount));

        for (ClientSeedDto clientSeedDto : clientSeedDtos) {
            if (!this.validationUtil.isValid(clientSeedDto)) {
                System.out.println("Error: Incorrect Data!");

                continue;
            }

            Client client =
                    this.clientRepository
                            .findByFullName(String.join(" ",
                                    clientSeedDto.getFirstName(),
                                    clientSeedDto.getLastName())
                            )
                            .orElse(null);

            if (client == null) {
                String[] employeeNameSplit =
                        clientSeedDto.getAppointedEmployee().split("\\s+");
                Employee employee = this.employeeRepository
                        .findByFirstNameAndLastName(
                                employeeNameSplit[0], employeeNameSplit[1])
                        .get();
                client = clientMap.map(clientSeedDto);
                client.setFullName(String.join(" ",
                        clientSeedDto.getFirstName(),
                        clientSeedDto.getLastName()));
                employee.getClients().add(client);
                this.clientRepository.saveAndFlush(client);
                sb.append(String.format("Successfully imported Client - %s.",
                        client.getFullName()))
                        .append(System.lineSeparator());
            }
        }

        return sb.toString();
    }

    @Override
    public String exportFamilyGuy() {
        StringBuilder sb = new StringBuilder();

        Client client = this.clientRepository.findByBiggestCountOfCards().get(0);

        sb.append(String.format("Full Name: %s",
                            client.getFullName()))
                            .append(System.lineSeparator())
                            .append(String.format("Salary: %d", client.getAge()))
                            .append(System.lineSeparator())
                            .append(String.format("Bank Account: %s",
                                    client.getBankAccount().getAccountNumber()))
                            .append(System.lineSeparator());
                    client.getBankAccount().getCards().forEach(card -> {
                        sb.append(String.format("\tCard Number: %s", card.getCardNumber()))
                                .append(System.lineSeparator())
                        .append(String.format("\tCard Status: %s", card.getCardStatus()))
                        .append(System.lineSeparator());
                    });


        return sb.toString();
    }
}
