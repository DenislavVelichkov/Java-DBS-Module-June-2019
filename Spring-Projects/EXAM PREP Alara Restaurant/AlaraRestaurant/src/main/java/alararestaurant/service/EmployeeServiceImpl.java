package alararestaurant.service;

import alararestaurant.models.dto.binding.EmployeeImportDto;
import alararestaurant.models.dto.binding.PositionImportDto;
import alararestaurant.models.entities.Employee;
import alararestaurant.models.entities.Position;
import alararestaurant.repository.EmployeeRepository;
import alararestaurant.repository.PositionRepository;
import alararestaurant.util.FileUtil;
import alararestaurant.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private static final String EMPLOYEE_PATH =
        System.getProperty("user.dir") + "/src/main/resources/files/employees.json";
    private final EmployeeRepository employeeRepository;
    private final PositionRepository positionRepository;
    private final Gson gson;
    private final FileUtil fileUtil;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    
    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository,
                               PositionRepository positionRepository,
                               Gson gson,
                               FileUtil fileUtil,
                               ValidationUtil validationUtil,
                               ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.positionRepository = positionRepository;
        this.gson = gson;
        this.fileUtil = fileUtil;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }
    
    @Override
    public Boolean employeesAreImported() {
        return this.employeeRepository.count() != 0;
    }
    
    @Override
    public String readEmployeesJsonFile() throws IOException {
        return this.fileUtil.readFile(EMPLOYEE_PATH);
    }
    
    @Override
    public String importEmployees(String employees) throws IOException {
        StringBuilder sb = new StringBuilder();
        EmployeeImportDto[] employeeImportDtos =
            this.gson.fromJson(this.readEmployeesJsonFile(), EmployeeImportDto[].class);
        
        for (EmployeeImportDto employeeImportDto : employeeImportDtos) {
            PositionImportDto positionDto = new PositionImportDto();
            positionDto.setName(employeeImportDto.getPosition());
            if (!this.validationUtil.isValid(employeeImportDto) ||
                !this.validationUtil.isValid(positionDto) ||
                    positionDto.getName().equals("Invalid")) {
                System.out.println("Invalid data format.");
                continue;
            }
            
            Position position =
                this.positionRepository
                    .findAllByName(employeeImportDto.getPosition())
                    .orElse(null);
            
            if (position == null) {
                position = this.modelMapper.map(positionDto, Position.class);
                this.positionRepository.saveAndFlush(position);
            }
            
            Employee employee = this.employeeRepository
                                    .findAllByName(employeeImportDto.getName())
                                    .orElse(null);

            if (employee == null) {
                employee = this.modelMapper.map(employeeImportDto, Employee.class);
                employee.setPosition(this.positionRepository
                                         .findAllByName(employeeImportDto.getPosition())
                                         .orElse(null));
                this.employeeRepository.saveAndFlush(employee);
                sb.append(String.format("Record %s successfully imported.",
                    employee.getName()))
                    .append(System.lineSeparator());
            }
        }
        
        return sb.toString().trim();
    }
}
