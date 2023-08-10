package view;

import service.TaxService;
import service.TaxServiceImpl;

import java.io.IOException;

public class TaxView {
    TaxService taxService;

    public TaxView() {
        taxService = new TaxServiceImpl();
    }

    public void readFromTaxFile() {

        try {
            System.out.println(taxService.readFromTaxFile());
        } catch (IOException e) {
            System.out.println("There has been an error trying to read from 'Taxes.txt' file.");
        }
    }
}
