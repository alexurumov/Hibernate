package softuni.xmlparsingexercisedemo.services;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public interface PartService {
    void importParts() throws JAXBException, IOException;
}
