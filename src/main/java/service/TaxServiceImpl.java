package service;

import dao.TaxDao;
import model.TaxTo;

import java.io.IOException;
import java.util.Map;

public class TaxServiceImpl implements TaxService {

    @Override
    public Map<String, TaxTo> readFromTaxFile() throws IOException {
        // Method calls the static method readFromTaxFile() of the TaxDao class and returns the result.
        return TaxDao.readFromTaxFile();
    }

    @Override
    public TaxTo fetchTaxTo(String key) {
        // Method calls the static method fetchTaxTo() of TaxDao class and returns the taxTo found (if one exists) else null.
        return TaxDao.fetchTaxTo(key);
    }

}
