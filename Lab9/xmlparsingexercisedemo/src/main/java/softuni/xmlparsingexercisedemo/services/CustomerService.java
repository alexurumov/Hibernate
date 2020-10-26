package softuni.xmlparsingexercisedemo.services;

import softuni.xmlparsingexercisedemo.domain.dtos.CustomersDto;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public interface CustomerService {
    void importCustomers() throws JAXBException, IOException;

    String exportOrderedCustomers() throws JAXBException;

    String exportCustomersBySales() throws JAXBException;
}
