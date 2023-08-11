package model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class OrderTo {
    private int orderNumber;
    private String customerName;
    private String state;
    private BigDecimal taxRate;
    private String productType;
    private BigDecimal area;
    private BigDecimal costPerSquareFoot;
    private BigDecimal laborCostPerSquareFoot;
    private BigDecimal materialCost;
    private BigDecimal laborCost;
    private BigDecimal tax;
    private BigDecimal total;

    private BigDecimal bigDecimal100 = new BigDecimal(100);     // Used in calculating tax field by diving the tax rate by 100.

    public OrderTo(int orderNumber, String customerName, String state, BigDecimal taxRate, String productType,
                   BigDecimal area, BigDecimal costPerSquareFoot, BigDecimal laborCostPerSquareFoot,
                   BigDecimal materialCost, BigDecimal laborCost, BigDecimal tax, BigDecimal total) {
        // Constructor for reading in the data from the files.
        this.orderNumber = orderNumber;
        this.customerName = customerName;
        this.state = state;
        this.taxRate = taxRate;
        this.productType = productType;
        this.area = area;
        this.costPerSquareFoot = costPerSquareFoot;
        this.laborCostPerSquareFoot = laborCostPerSquareFoot;
        this.materialCost = materialCost;
        this.laborCost = laborCost;
        this.tax = tax;
        this.total = total;
    }
    public OrderTo(int orderNumber, String customerName, TaxTo taxTo, ProductTo productTo, BigDecimal area){
        // Constructor to make order objects via the program.
        this.orderNumber = orderNumber;
        this.customerName = customerName;
        this.state = taxTo.getStateAbbreviation();
        this.taxRate = taxTo.getTaxRate();
        this.productType = productTo.getProductType();
        this.area = area;
        this.costPerSquareFoot = productTo.getCostPerSquareFoot();
        this.laborCostPerSquareFoot = productTo.getLaborCostPerSquareFoot();
        this.materialCost = area.multiply(productTo.getCostPerSquareFoot());
        this.laborCost = area.multiply(productTo.getLaborCostPerSquareFoot());
        this.tax = (this.materialCost.add(this.laborCost)).multiply(taxTo.getTaxRate().divide(bigDecimal100, 2, RoundingMode.HALF_UP));
        this.total = (this.materialCost.add(this.laborCost)).add(this.tax);

    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public BigDecimal getArea() {
        return area;
    }

    public void setArea(BigDecimal area) {
        this.area = area;
    }

    public String getProductType() {
        return productType;
    }

    public BigDecimal getMaterialCost() {
        return materialCost;
    }

    /* public void setMaterialCost(BigDecimal materialCost) {
        this.materialCost = materialCost;
    }*/

    public BigDecimal getLaborCost() {
        return laborCost;
    }

    /* public void setLaborCost(BigDecimal laborCost) {
        this.laborCost = laborCost;
    } */

    @Override
    public String toString() {
        return "OrderNumber=" + orderNumber +
                ", customerName='" + customerName + '\'' +
                ", state='" + state + '\'' +
                ", taxRate=" + taxRate +
                ", productType='" + productType + '\'' +
                ", area=" + area +
                ", costPerSquareFoot=" + costPerSquareFoot +
                ", laborCostPerSquareFoot=" + laborCostPerSquareFoot +
                ", materialCost=" + materialCost +
                ", laborCost=" + laborCost +
                ", tax=" + tax +
                ", total=" + total ;
    }

    public String orderBreakdownDisplay() {
        return "OrderNumber=" + orderNumber +
                "\n customerName='" + customerName + '\'' +
                "\n state='" + state + '\'' +
                "\n taxRate=" + taxRate +
                "\n productType='" + productType + '\'' +
                "\n area=" + area +
                "\n costPerSquareFoot=" + costPerSquareFoot +
                "\n laborCostPerSquareFoot=" + laborCostPerSquareFoot +
                "\n materialCost=" + materialCost +
                "\n laborCost=" + laborCost +
                "\n tax=" + tax +
                "\n total=" + total ;
    }
}
