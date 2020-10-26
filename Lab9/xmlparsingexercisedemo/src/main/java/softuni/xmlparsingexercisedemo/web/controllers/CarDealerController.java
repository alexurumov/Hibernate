package softuni.xmlparsingexercisedemo.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;
import softuni.xmlparsingexercisedemo.services.*;

import javax.xml.bind.JAXBException;
import java.util.Scanner;

@Controller
public class CarDealerController implements CommandLineRunner {

    private final SupplierService supplierService;
    private final PartService partService;
    private final CarService carService;
    private final CustomerService customerService;
    private final SaleService saleService;

    @Autowired
    public CarDealerController(SupplierService supplierService, PartService partService, CarService carService, CustomerService customerService, SaleService saleService) {
        this.supplierService = supplierService;
        this.partService = partService;
        this.carService = carService;
        this.customerService = customerService;
        this.saleService = saleService;
    }


    @Override
    public void run(String... args) throws Exception {

//        this.supplierService.importSuppliers();
//        this.partService.importParts();
//        this.carService.importCars();
//        this.customerService.importCustomers();
//        this.saleService.importSales();

//        this.exportOrderedCustomers();
//        this.exportCarsByMake();
//        this.exportLocalSuppliers();
//        this.exportCarsWithParts();
//        this.exportCustomersBySales();
//        this.exportSalesWithDiscount();
    }

    private void exportSalesWithDiscount() throws JAXBException {
        System.out.println(this.saleService.exportSalesWithDiscount());
    }

    private void exportCustomersBySales() throws JAXBException {
        System.out.println(this.customerService.exportCustomersBySales());
    }

    private void exportCarsWithParts() throws JAXBException {
        System.out.println(this.carService.exportCarsWithParts());
    }

    private void exportLocalSuppliers() throws JAXBException {
        System.out.println(this.supplierService.exportLocalSuppliers());
    }

    private void exportCarsByMake() throws JAXBException {
        System.out.println(this.carService.exportCarsByMake());
    }

    private void exportOrderedCustomers() throws JAXBException {
        System.out.println(this.customerService.exportOrderedCustomers());
    }
}
