package softuni.xmlparsingexercisedemo.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.xmlparsingexercisedemo.domain.dtos.*;
import softuni.xmlparsingexercisedemo.domain.entities.Customer;
import softuni.xmlparsingexercisedemo.domain.entities.Part;
import softuni.xmlparsingexercisedemo.domain.entities.Sale;
import softuni.xmlparsingexercisedemo.repositories.CustomerRepository;
import softuni.xmlparsingexercisedemo.services.CustomerService;
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
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private static final String CUSTOMERS_FILE_PATH =
            "/Users/macbookair/Downloads/Hibernate/Lab9/xmlparsingexercisedemo/src/main/resources/files/customers.xml";

    private final CustomerRepository customerRepository;
    private final FileUtil fileUtil;
    private final ModelMapper mapper;
    private final ValidationUtil validationUtil;

    public CustomerServiceImpl(CustomerRepository customerRepository, FileUtil fileUtil, ModelMapper mapper, ValidationUtil validationUtil) {
        this.customerRepository = customerRepository;
        this.fileUtil = fileUtil;
        this.mapper = mapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public void importCustomers() throws JAXBException, IOException {

        JAXBContext jaxbContext = JAXBContext.newInstance(CustomersDto.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        String s = fileUtil.fileContent(CUSTOMERS_FILE_PATH);
        StringReader reader = new StringReader(s);
        CustomersDto customersDto = (CustomersDto) unmarshaller.unmarshal(reader);

        List<Customer> customers = customersDto.getCustomerDtoList()
                .stream()
                .map(dto -> {
                    Customer customer = mapper.map(dto, Customer.class);
                    String birthdate = dto.getBirthDate();
                    customer.setBirthDate(LocalDateTime.parse(birthdate));
                    return customer;
                })
                .collect(Collectors.toList());

        for (Customer customer : customers) {
            if (!validationUtil.isValid(customer)) {
                System.out.println(validationUtil.violations(customer));

                continue;
            }

            this.customerRepository.saveAndFlush(customer);
        }

    }

    @Override
    public String exportOrderedCustomers() throws JAXBException {

        List<CustomerExportDto> customerExportDtos = this.customerRepository.findAllByOrderByBirthDateAsc()
                .stream()
                .sorted((d1, d2) -> {
                    if (d1.getBirthDate().isEqual(d2.getBirthDate())) {
                        if (!d1.isYoungDriver() && d2.isYoungDriver()) {
                            return -1;
                        }
                        if (d1.isYoungDriver() && !d2.isYoungDriver()) {
                            return 1;
                        }
                    }
                    return 0;
                })
                .map(c -> mapper.map(c, CustomerExportDto.class))
                .collect(Collectors.toList());

        CustomersExportDto customersExportDto = new CustomersExportDto();
        customersExportDto.setCustomerExportDtos(customerExportDtos);

        JAXBContext jaxbContext = JAXBContext.newInstance(CustomersExportDto.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        StringWriter writer = new StringWriter();

        marshaller.marshal(customersExportDto, writer);

        return writer.toString();
    }

    @Override
    public String exportCustomersBySales() throws JAXBException {

        List<CustomerBySalesDto> dtos = this.customerRepository.findAll()
                .stream()
                .filter(c -> c.getSales().size() != 0)
                .map(customer -> {
                    CustomerBySalesDto dto = mapper.map(customer, CustomerBySalesDto.class);
                    dto.setBoughtCars(customer.getSales().size());
                    dto.setMoneySpent(this.getMoneySpent(customer));
                    return dto;
                })
                .sorted((d1, d2) -> {
                    if (d1.getMoneySpent().equals(d2.getMoneySpent())) {
                        return Integer.compare(d2.getBoughtCars(), d1.getBoughtCars());
                    }
                    return d2.getMoneySpent().compareTo(d1.getMoneySpent());
                })
                .collect(Collectors.toList());

        CustomersBySaleMainDto mainDto = new CustomersBySaleMainDto();
        mainDto.setCustomerBySalesDtos(dtos);

        JAXBContext jaxbContext = JAXBContext.newInstance(CustomersBySaleMainDto.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        StringWriter writer = new StringWriter();

        marshaller.marshal(mainDto, writer);

        return writer.toString();
    }

    private BigDecimal getMoneySpent(Customer customer) {

        List<Sale> sales = customer.getSales();
        BigDecimal totalSum = BigDecimal.ZERO;
        for (Sale sale : sales) {

            List<Part> parts = sale.getCar().getParts();
            BigDecimal partsSum = BigDecimal.ZERO;
            for (Part part : parts) {
                partsSum = partsSum.add(part.getPrice());
            }

            totalSum = totalSum.add(partsSum.multiply(BigDecimal.valueOf(1d - sale.getDiscount())));
        }

        return totalSum;
    }
}
