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

    @Override
    public String toString() {
        return  "{stateAbbreviation='" + stateAbbreviation + '\'' +
                ", stateName='" + stateName + '\'' +
                ", taxRate=" + taxRate +"}\n";
    }

    public TaxTo copyTaxTo() {
        return new TaxTo(this.stateAbbreviation, this.stateName, this.taxRate);
    }
}
