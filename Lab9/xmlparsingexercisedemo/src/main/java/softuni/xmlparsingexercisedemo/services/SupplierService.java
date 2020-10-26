package softuni.xmlparsingexercisedemo.services;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public interface SupplierService {
    void importSuppliers() throws JAXBException, IOException;

    String exportLocalSuppliers() throws JAXBException;
}
