package softuni.xmlparsingexercisedemo.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.xmlparsingexercisedemo.domain.dtos.SupplierExportDto;
import softuni.xmlparsingexercisedemo.domain.dtos.SuppliersDto;
import softuni.xmlparsingexercisedemo.domain.dtos.SuppliersExportDto;
import softuni.xmlparsingexercisedemo.domain.entities.Supplier;
import softuni.xmlparsingexercisedemo.repositories.SupplierRepository;
import softuni.xmlparsingexercisedemo.services.SupplierService;
import softuni.xmlparsingexercisedemo.util.FileUtil;
import softuni.xmlparsingexercisedemo.util.ValidationUtil;

import javax.transaction.Transactional;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class SupplierServiceImpl implements SupplierService {

    private static final String SUPPLIERS_FILE_PATH =
            "/Users/macbookair/Downloads/Hibernate/Lab9/xmlparsingexercisedemo/src/main/resources/files/suppliers.xml";

    private final FileUtil fileUtil;

    private final SupplierRepository supplierRepository;
    private final ModelMapper mapper;
    private final ValidationUtil validationUtil;

    @Autowired
    public SupplierServiceImpl(FileUtil fileUtil, SupplierRepository supplierRepository, ModelMapper mapper, ValidationUtil validationUtil) {
        this.fileUtil = fileUtil;
        this.supplierRepository = supplierRepository;
        this.mapper = mapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public void importSuppliers() throws JAXBException, IOException {

        JAXBContext jaxbContext = JAXBContext.newInstance(SuppliersDto.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        String s = fileUtil.fileContent(SUPPLIERS_FILE_PATH);
        StringReader reader = new StringReader(s);
        SuppliersDto suppliersDto = (SuppliersDto) unmarshaller.unmarshal(reader);

        List<Supplier> suppliers = suppliersDto.getSupplierDtoList()
                .stream()
                .map(dto -> mapper.map(dto, Supplier.class))
                .collect(Collectors.toList());

        for (Supplier supplier : suppliers) {
            if (!validationUtil.isValid(supplier)) {
                System.out.println(validationUtil.violations(supplier));
                continue;
            }

            this.supplierRepository.saveAndFlush(supplier);
        }
    }

    @Override
    public String exportLocalSuppliers() throws JAXBException {

        List<SupplierExportDto> dtos = this.supplierRepository.findAll()
                .stream()
                .filter(s -> !s.isImporter())
                .map(s -> {
                    SupplierExportDto dto = mapper.map(s, SupplierExportDto.class);
                    dto.setPartsCount(s.getParts().size());
                    return dto;
                })
                .collect(Collectors.toList());

        SuppliersExportDto mainDto = new SuppliersExportDto();
        mainDto.setSupplierExportDtos(dtos);

        JAXBContext context = JAXBContext.newInstance(SuppliersExportDto.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        StringWriter writer = new StringWriter();
        marshaller.marshal(mainDto, writer);

        return writer.toString();
    }
}
