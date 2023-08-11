package service;

import dao.TaxDao;
import dao.TaxDaoImpl;
import model.TaxTo;

import java.io.IOException;
import java.util.Map;

public class TaxServiceImpl implements TaxService {

    TaxDao taxDao;

    public TaxServiceImpl() {
        taxDao = new TaxDaoImpl();
    }

    @Override
    public Map<String, TaxTo> readFromTaxFile() throws IOException {
        return taxDao.readFromTaxFile();
    }

    @Override
    public TaxTo fetchTaxTo(String key) {
        return taxDao.fetchTaxTo(key);
    }

}
