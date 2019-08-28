package app.ccb.services;

import app.ccb.domain.dtos.json.EmployeeSeedDto;
import app.ccb.domain.entities.Branch;
import app.ccb.domain.entities.Employee;
import app.ccb.repositories.BranchRepository;
import app.ccb.repositories.EmployeeRepository;
import app.ccb.util.FileUtil;
import app.ccb.util.GsonParser;
import app.ccb.util.ValidationUtil;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final String EMPLOYEES_JSON_FILE_PATH =
            System.getProperty("user.dir") +
                    "/src/main/resources/files/json/employees.json";

    private final EmployeeRepository employeeRepository;
    private final BranchRepository branchRepository;
    private final FileUtil fileUtil;
    private final GsonParser gsonParser;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository,
                               BranchRepository branchRepository,
                               FileUtil fileUtil,
                               GsonParser gsonParser,
                               ValidationUtil validationUtil,
                               ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.branchRepository = branchRepository;
        this.fileUtil = fileUtil;
        this.gsonParser = gsonParser;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public Boolean employeesAreImported() {
        return this.employeeRepository.count() != 0;
    }

    @Override
    public String readEmployeesJsonFile() throws IOException {
        return this.fileUtil.readFile(EMPLOYEES_JSON_FILE_PATH);
    }

    @Override
    public String importEmployees(String employees) {
        StringBuilder sb = new StringBuilder();
        EmployeeSeedDto[] employeeSeedDtos =
                this.gsonParser.parseDataFromJson(employees, EmployeeSeedDto[].class);

        Converter<String, String> convertFirstName = src -> {
            String[] params = src.getSource().split("\\s+");
            String firstName = params[0];
            return (src.getSource() == null) ? null : firstName;
        };

        Converter<String, String> convertSecondName = src -> {
            String[] params = src.getSource().split("\\s+");
            String lastName = params[1];
            return (src.getSource() == null) ? null : lastName;
        };


        Converter<String, Branch> nameToBranch = src -> src.getSource() == null ?
                null
                :
                this.branchRepository.findByName(src.getSource()).orElse(null);

        TypeMap<EmployeeSeedDto, Employee> employeeMap =
                this.modelMapper.createTypeMap(EmployeeSeedDto.class, Employee.class);

        employeeMap.addMappings(m -> m.using(convertFirstName)
                .map(EmployeeSeedDto::getFullName, Employee::setFirstName));
        employeeMap.addMappings(m -> m.using(convertSecondName)
                .map(EmployeeSeedDto::getFullName, Employee::setLastName));
        employeeMap.addMappings(m -> m.using(nameToBranch)
                .map(EmployeeSeedDto::getBranchName, Employee::setBranch));
        employeeMap.addMappings(m -> m.map(EmployeeSeedDto::getSalary, Employee::setSalary));
        employeeMap.addMappings(m -> m.map(EmployeeSeedDto::getStartedOn, Employee::setStartedOn));

        for (EmployeeSeedDto employeeSeedDto : employeeSeedDtos) {
            if (!this.validationUtil.isValid(employeeSeedDto)) {
                System.out.println("Error: Incorrect Data!");

                continue;
            }

            Employee employee =
                    this.employeeRepository
                            .findByFirstNameAndLastName(
                                    employeeSeedDto.getFullName().split("\\s+")[0],
                                    employeeSeedDto.getFullName().split("\\s+")[1]
                            ).orElse(null);

            if (employee == null) {
                employee = employeeMap.map(employeeSeedDto);
                this.employeeRepository.saveAndFlush(employee);
                sb.append(String.format("Successfully imported Employee - %s.",
                        employeeSeedDto.getFullName()))
                        .append(System.lineSeparator());
            }
        }

        return sb.toString();
    }

    @Override
    public String exportTopEmployees() {
        StringBuilder sb = new StringBuilder();
        this.employeeRepository.findAllByClientsNotNull()
                .forEach(employee -> {
                    sb.append(String.format("Full Name: %s %s",
                            employee.getFirstName(), employee.getLastName()))
                            .append(System.lineSeparator())
                            .append(String.format("Salary: %s", employee.getSalary()))
                            .append(System.lineSeparator())
                            .append(String.format("Started On: %s", employee.getStartedOn()))
                            .append(System.lineSeparator())
                            .append("Clients:")
                    .append(System.lineSeparator());
                    employee.getClients().forEach(client -> {
                        sb.append(String.format("\t%s", client.getFullName()))
                                .append(System.lineSeparator());
                    });

                });

        return sb.toString();
    }
}
