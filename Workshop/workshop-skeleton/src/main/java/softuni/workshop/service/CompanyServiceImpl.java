package softuni.workshop.service;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.workshop.domain.dtos.importDtos.CompanyDto;
import softuni.workshop.domain.dtos.importDtos.CompanyRootDto;
import softuni.workshop.domain.entities.Company;
import softuni.workshop.repository.CompanyRepository;
import softuni.workshop.repository.ProjectRepository;
import softuni.workshop.util.FileUtil;
import softuni.workshop.util.ValidatorUtil;
import softuni.workshop.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;

@Service
public class CompanyServiceImpl implements CompanyService {

    private static final String COMPANIES_XML_FILE_PATH =
            "/Users/macbookair/Downloads/Hibernate/Workshop/workshop-skeleton/src/main/resources/files/xmls/companies.xml";

    private final CompanyRepository companyRepository;
    private final ModelMapper mapper;
    private final XmlParser xmlParser;
    private final ValidatorUtil validatorUtil;
    private final FileUtil fileUtil;

    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository, ModelMapper mapper, XmlParser xmlParser, ValidatorUtil validatorUtil, FileUtil fileUtil) {
        this.companyRepository = companyRepository;
        this.mapper = mapper;
        this.xmlParser = xmlParser;
        this.validatorUtil = validatorUtil;
        this.fileUtil = fileUtil;
    }

    @Override
    public void importCompanies() throws JAXBException {

        CompanyRootDto companyRootDto = this.xmlParser.importXml(CompanyRootDto.class, COMPANIES_XML_FILE_PATH);

        for (CompanyDto companyDto : companyRootDto.getCompanyDtos()) {
            Company company = mapper.map(companyDto, Company.class);
            if (!this.validatorUtil.isValid(company)) {
                validatorUtil.violations(company).forEach(v -> System.out.println(v.getMessage()));

                continue;
            }

            this.companyRepository.saveAndFlush(company);
        }

    }

    @Override
    public boolean areImported() {
        return this.companyRepository.count() > 0;
    }

    @Override
    public String readCompaniesXmlFile() throws IOException {

        return fileUtil.readFile(COMPANIES_XML_FILE_PATH);
    }
}
