package softuni.xmlparsingexercisedemo.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.xmlparsingexercisedemo.domain.dtos.CarInSaleDto;
import softuni.xmlparsingexercisedemo.domain.dtos.SaleDto;
import softuni.xmlparsingexercisedemo.domain.dtos.SaleMainDto;
import softuni.xmlparsingexercisedemo.domain.dtos.SuppliersExportDto;
import softuni.xmlparsingexercisedemo.domain.entities.Car;
import softuni.xmlparsingexercisedemo.domain.entities.Customer;
import softuni.xmlparsingexercisedemo.domain.entities.Part;
import softuni.xmlparsingexercisedemo.domain.entities.Sale;
import softuni.xmlparsingexercisedemo.repositories.CarRepository;
import softuni.xmlparsingexercisedemo.repositories.CustomerRepository;
import softuni.xmlparsingexercisedemo.repositories.SaleRepository;
import softuni.xmlparsingexercisedemo.services.SaleService;

import javax.transaction.Transactional;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
public class SaleServiceImpl implements SaleService {

    private final CarRepository carRepository;
    private final CustomerRepository customerRepository;
    private final SaleRepository saleRepository;
    private final ModelMapper mapper;

    @Autowired
    public SaleServiceImpl(CarRepository carRepository, CustomerRepository customerRepository, SaleRepository saleRepository, ModelMapper mapper) {
        this.carRepository = carRepository;
        this.customerRepository = customerRepository;
        this.saleRepository = saleRepository;
        this.mapper = mapper;
    }

    @Override
    public void importSales() {

        for (int i = 0; i < 20; i++) {
            Sale sale = new Sale();
            sale.setCar(this.getRandomCar());
            sale.setCustomer(this.getRandomCustomer());
            sale.setDiscount(this.getRandomDiscount());

            this.saleRepository.saveAndFlush(sale);
        }

    }

    @Override
    public String exportSalesWithDiscount() throws JAXBException {

        List<SaleDto> dtos = this.saleRepository.findAll()
                .stream()
                .map(sale -> {
                    SaleDto dto = mapper.map(sale, SaleDto.class);
                    dto.setPrice(this.getPrice(sale));
                    dto.setPriceWithDiscount(dto.getPrice().multiply(BigDecimal.valueOf(1d - dto.getDiscount())));
                    dto.setCustomerName(sale.getCustomer().getName());
                    dto.setCarExportDto(mapper.map(sale.getCar(), CarInSaleDto.class));
                    return dto;
                })
                .collect(Collectors.toList());

        SaleMainDto mainDto = new SaleMainDto();
        mainDto.setSaleDtos(dtos);

        JAXBContext context = JAXBContext.newInstance(SaleMainDto.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        StringWriter writer = new StringWriter();
        marshaller.marshal(mainDto, writer);

        return writer.toString();
    }

    private BigDecimal getPrice(Sale sale) {
        BigDecimal sum = BigDecimal.ZERO;
        List<Part> parts = sale.getCar().getParts();

        for (Part part : parts) {
            sum = sum.add(part.getPrice());
        }

        return sum;
    }


    private Car getRandomCar() {
        Random random = new Random();
        int randomId = random.nextInt((int) this.carRepository.count()) + 1;

        return this.carRepository.findById(randomId).orElse(null);
    }

    private Customer getRandomCustomer() {
        Random random = new Random();
        int randomId = random.nextInt((int) this.customerRepository.count()) + 1;

        return this.customerRepository.findById(randomId).orElse(null);
    }

    private Double getRandomDiscount() {
        List<Double> discounts = Stream.of(0.0, 0.05, 0.1, 0.15, 0.2, 0.3, 0.4, 0.5).collect(Collectors.toList());
        Random random = new Random();
        int randomId = random.nextInt(8);

        return discounts.get(randomId);
    }
}
