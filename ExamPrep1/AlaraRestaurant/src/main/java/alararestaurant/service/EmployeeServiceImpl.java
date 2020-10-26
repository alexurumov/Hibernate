package alararestaurant.service;

import alararestaurant.domain.dtos.EmployeeJsonImportDto;
import alararestaurant.domain.entities.Employee;
import alararestaurant.domain.entities.Position;
import alararestaurant.repository.EmployeeRepository;
import alararestaurant.repository.PositionRepository;
import alararestaurant.util.FileUtil;
import alararestaurant.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private static final String EMPLOYEES_JSON_FILE_PATH =
            "/Users/macbookair/Downloads/Hibernate/ExamPrep1/AlaraRestaurant/src/main/resources/files/employees.json";

    private final EmployeeRepository employeeRepository;
    private final PositionRepository positionRepository;
    private final FileUtil fileUtil;
    private final Gson gson;
    private final ModelMapper mapper;
    private final ValidationUtil validationUtil;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, PositionRepository positionRepository, FileUtil fileUtil, Gson gson, ModelMapper mapper, ValidationUtil validationUtil) {
        this.employeeRepository = employeeRepository;
        this.positionRepository = positionRepository;
        this.fileUtil = fileUtil;
        this.gson = gson;
        this.mapper = mapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public Boolean employeesAreImported() {
        return this.employeeRepository.count() > 0;
    }

    @Override
    public String readEmployeesJsonFile() throws IOException {
        return fileUtil.readFile(EMPLOYEES_JSON_FILE_PATH);
    }

    @Override
    public String importEmployees(String employees) {

        StringBuilder sb = new StringBuilder();

        EmployeeJsonImportDto[] employeeJsonImportDtos = gson.fromJson(employees, EmployeeJsonImportDto[].class);

        for (EmployeeJsonImportDto dto : employeeJsonImportDtos) {
            Employee employee = mapper.map(dto, Employee.class);

            if (!validationUtil.isValid(employee)) {
                sb.append("Invalid data format.").append(System.lineSeparator());
                continue;
            }

            Position position = positionRepository.findByName(dto.getPosition()).orElse(null);
            if (position == null) {
                position = new Position();
                position.setName(dto.getPosition());
            }

            if (!this.validationUtil.isValid(position)) {
                sb.append("Invalid data format.").append(System.lineSeparator());

                continue;
            }

            this.positionRepository.saveAndFlush(position);

            employee.setPosition(position);
            this.employeeRepository.saveAndFlush(employee);
            sb.append("Record ").append(employee.getName()).append(" successfully imported.").append(System.lineSeparator());
        }

        return sb.toString();
    }
}
