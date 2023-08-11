package service;

import model.TaxTo;

import java.io.IOException;
import java.util.Map;

public interface TaxService {
    Map<String, TaxTo> readFromTaxFile() throws IOException;
}
