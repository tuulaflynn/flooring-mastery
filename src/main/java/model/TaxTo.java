package model;

import java.math.BigDecimal;

public class TaxTo {
    private String stateAbbreviation;
    private String stateName;
    private BigDecimal taxRate;

    public TaxTo(String stateAbbreviation, String stateName, BigDecimal taxRate) {
        this.stateAbbreviation = stateAbbreviation;
        this.stateName = stateName;
        this.taxRate = taxRate;
    }

    // Only need getters as ProductTo objects will be initialised by reading on the data file and not edited during the program.

    public String getStateAbbreviation() {
        return stateAbbreviation;
    }

    public String getStateName() {
        return stateName;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }
}
