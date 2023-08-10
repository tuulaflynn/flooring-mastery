package dao;

import model.TaxTo;

import java.io.IOException;
import java.util.Map;

public interface TaxDao {
    Map<String, TaxTo> readFromTaxFile() throws IOException; // Each object of tax is initialised from a line in the file

}
