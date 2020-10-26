package softuni.workshop.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.workshop.domain.dtos.importDtos.ProjectDto;
import softuni.workshop.domain.dtos.importDtos.ProjectRootDto;
import softuni.workshop.domain.entities.Company;
import softuni.workshop.domain.entities.Project;
import softuni.workshop.repository.CompanyRepository;
import softuni.workshop.repository.ProjectRepository;
import softuni.workshop.util.FileUtil;
import softuni.workshop.util.ValidatorUtil;
import softuni.workshop.util.XmlParser;

import javax.transaction.Transactional;
import javax.xml.bind.JAXBException;
import java.io.IOException;


@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {

    private static final String PROJECTS_XML_FILE_PATH =
            "/Users/macbookair/Downloads/Hibernate/Workshop/workshop-skeleton/src/main/resources/files/xmls/projects.xml";

    private final ProjectRepository projectRepository;
    private final CompanyRepository companyRepository;
    private final ModelMapper mapper;
    private final ValidatorUtil validatorUtil;
    private final XmlParser xmlParser;
    private final FileUtil fileUtil;

    public ProjectServiceImpl(ProjectRepository projectRepository, CompanyRepository companyRepository, ModelMapper mapper, ValidatorUtil validatorUtil, XmlParser xmlParser, FileUtil fileUtil) {
        this.projectRepository = projectRepository;
        this.companyRepository = companyRepository;
        this.mapper = mapper;
        this.validatorUtil = validatorUtil;
        this.xmlParser = xmlParser;
        this.fileUtil = fileUtil;
    }

    @Override
    public void importProjects() throws JAXBException {

        ProjectRootDto projectRootDto = xmlParser.importXml(ProjectRootDto.class, PROJECTS_XML_FILE_PATH);

        for (ProjectDto projectDto : projectRootDto.getProjectDtos()) {
            Project project = mapper.map(projectDto, Project.class);
            if (!validatorUtil.isValid(project)) {
                validatorUtil.violations(project).forEach(v -> System.out.println(v.getMessage()));

                continue;
            }

            Company company = this.companyRepository.findCompanyByName(projectDto.getCompany().getName());
            project.setCompany(company);

            this.projectRepository.saveAndFlush(project);
        }

    }

    @Override
    public boolean areImported() {
       return this.projectRepository.count() > 0;
    }

    @Override
    public String readProjectsXmlFile() throws IOException {
      return fileUtil.readFile(PROJECTS_XML_FILE_PATH);
    }

    @Override
    public String exportFinishedProjects(){
        StringBuilder sb = new StringBuilder();

        this.projectRepository.findAllByFinishedIsTrue()
                .forEach(p -> {
                    sb.append("Project Name: ").append(p.getName()).append(System.lineSeparator())
                            .append("    Description: ").append(p.getDescription()).append(System.lineSeparator())
                            .append("    ").append(p.getPayment()).append(System.lineSeparator());
                });

        return sb.toString().trim();
    }
}
