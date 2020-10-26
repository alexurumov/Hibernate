package softuni.xmlparsingexercisedemo.services;

import javax.xml.bind.JAXBException;

public interface SaleService {
    void importSales();

    String exportSalesWithDiscount() throws JAXBException;
}
