package softuni.xmlparsingexercisedemo.services;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public interface CarService {

    void importCars() throws JAXBException, IOException;

    String exportCarsByMake() throws JAXBException;

    String exportCarsWithParts() throws JAXBException;
}
