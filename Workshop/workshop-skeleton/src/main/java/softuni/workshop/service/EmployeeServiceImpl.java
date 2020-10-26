package softuni.workshop.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.workshop.domain.dtos.exportDtos.EmployeeExportDto;
import softuni.workshop.domain.dtos.exportDtos.EmployeeExportRootDto;
import softuni.workshop.domain.dtos.importDtos.EmployeeDto;
import softuni.workshop.domain.dtos.importDtos.EmployeeRootDto;
import softuni.workshop.domain.entities.Employee;
import softuni.workshop.domain.entities.Project;
import softuni.workshop.repository.EmployeeRepository;
import softuni.workshop.repository.ProjectRepository;
import softuni.workshop.util.FileUtil;
import softuni.workshop.util.ValidatorUtil;
import softuni.workshop.util.XmlParser;

import javax.transaction.Transactional;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private static final String EMPLOYEES_XML_FILE_PATH =
            "/Users/macbookair/Downloads/Hibernate/Workshop/workshop-skeleton/src/main/resources/files/xmls/employees.xml";
    private static final String EXPORT_XML_EMPLOYEES_FILE_PATH =
            "/Users/macbookair/Downloads/Hibernate/Workshop/workshop-skeleton/src/main/resources/files/xmls/exported-xml-employees";

    private final EmployeeRepository employeeRepository;
    private final ProjectRepository projectRepository;
    private final XmlParser xmlParser;
    private final ModelMapper mapper;
    private final ValidatorUtil validatorUtil;
    private final FileUtil fileUtil;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, ProjectRepository projectRepository, XmlParser xmlParser, ModelMapper mapper, ValidatorUtil validatorUtil, FileUtil fileUtil) {
        this.employeeRepository = employeeRepository;
        this.projectRepository = projectRepository;
        this.xmlParser = xmlParser;
        this.mapper = mapper;
        this.validatorUtil = validatorUtil;
        this.fileUtil = fileUtil;
    }

    @Override
    public void importEmployees() throws JAXBException {

        EmployeeRootDto employeeRootDto = xmlParser.importXml(EmployeeRootDto.class, EMPLOYEES_XML_FILE_PATH);

        for (EmployeeDto employeeDto : employeeRootDto.getEmployeeDtos()) {
            Employee employee = mapper.map(employeeDto, Employee.class);
            if (!validatorUtil.isValid(employee)) {
                validatorUtil.violations(employee).forEach(v -> System.out.println(v.getMessage()));

                continue;
            }


            Project project = this.projectRepository.findByName(employeeDto.getProjectDto().getName());
            employee.setProject(project);

            this.employeeRepository.saveAndFlush(employee);
        }

    }


    @Override
    public boolean areImported() {
       return this.employeeRepository.count() > 0;
    }

    @Override
    public String readEmployeesXmlFile() throws IOException {
        return fileUtil.readFile(EMPLOYEES_XML_FILE_PATH);
    }

    @Override
    public String exportEmployeesWithAgeAbove() {
        StringBuilder sb = new StringBuilder();

        this.employeeRepository.findAllByAgeAfter(25)
                .forEach(e -> {
                    sb.append("Name: ").append(e.getFirstName()).append(" ").append(e.getLastName()).append(System.lineSeparator())
                            .append("    Age: ").append(e.getAge()).append(System.lineSeparator())
                            .append("    Project Name: ").append(e.getProject().getName()).append(System.lineSeparator());
                });

        return sb.toString().trim();
    }

    @Override
    public void exportEmployees() throws JAXBException {

        EmployeeExportRootDto rootDto = new EmployeeExportRootDto();
        rootDto.setEmployeeExportDtos(this.employeeRepository.findAll()
                .stream()
                .map(e -> mapper.map(e, EmployeeExportDto.class))
                .collect(Collectors.toList()));

        this.xmlParser.exportXml(rootDto, EXPORT_XML_EMPLOYEES_FILE_PATH);
    }
}
