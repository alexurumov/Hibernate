package softuni.xmlparsingexercisedemo.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.xmlparsingexercisedemo.domain.dtos.PartsDto;
import softuni.xmlparsingexercisedemo.domain.entities.Part;
import softuni.xmlparsingexercisedemo.domain.entities.Supplier;
import softuni.xmlparsingexercisedemo.repositories.PartRepository;
import softuni.xmlparsingexercisedemo.repositories.SupplierRepository;
import softuni.xmlparsingexercisedemo.services.PartService;
import softuni.xmlparsingexercisedemo.util.FileUtil;
import softuni.xmlparsingexercisedemo.util.ValidationUtil;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class PartServiceImpl implements PartService {

    private static final String PARTS_FILE_PATH =
            "/Users/macbookair/Downloads/Hibernate/Lab9/xmlparsingexercisedemo/src/main/resources/files/parts.xml";

    private final PartRepository partRepository;
    private final SupplierRepository supplierRepository;
    private final FileUtil fileUtil;
    private final ModelMapper mapper;
    private final ValidationUtil validationUtil;

    public PartServiceImpl(FileUtil fileUtil, SupplierRepository supplierRepository, PartRepository partRepository, ModelMapper mapper, ValidationUtil validationUtil) {
        this.fileUtil = fileUtil;
        this.supplierRepository = supplierRepository;
        this.partRepository = partRepository;
        this.mapper = mapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public void importParts() throws JAXBException, IOException {

        JAXBContext jaxbContext = JAXBContext.newInstance(PartsDto.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        String s = fileUtil.fileContent(PARTS_FILE_PATH);
        StringReader reader = new StringReader(s);
        PartsDto partsDto = (PartsDto) unmarshaller.unmarshal(reader);

        List<Part> parts = partsDto.getPartDtoList()
                .stream()
                .map(dto -> mapper.map(dto, Part.class))
                .collect(Collectors.toList());

        for (Part part : parts) {
            if (!validationUtil.isValid(part)) {
                System.out.println(validationUtil.violations(part));
                continue;
            }

            part.setSupplier(getRandomSupplier());

            this.partRepository.saveAndFlush(part);
        }

    }

    private Supplier getRandomSupplier() {
        Random random = new Random();
        int randomId = random.nextInt((int) this.supplierRepository.count()) + 1;

        return this.supplierRepository.findById(randomId).orElse(null);
    }
}
