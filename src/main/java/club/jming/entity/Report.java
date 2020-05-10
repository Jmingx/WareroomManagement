package club.jming.entity;

/**
 * report类，记录一下bug，当时把这个类定义为一个default类，结果发现不能够访问...
 * 所以以后要记住，entity类必须为public，否则el表达式不能访问
 */
public class Report{
    private String componentName;
    private int amount;
    private int componentId;
    //key---可选供应商,value---供应商联系方式
    private String  supplierName;
    private String supplierContact;
    private double price;
    private double total;

    public Report() {
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getComponentName() {
        return componentName;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getComponentId() {
        return componentId;
    }

    public void setComponentId(int componentId) {
        this.componentId = componentId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierContact() {
        return supplierContact;
    }

    public void setSupplierContact(String supplierContact) {
        this.supplierContact = supplierContact;
    }

    @Override
    public String toString() {
        return "Report{" +
                "componentName='" + componentName + '\'' +
                ", amount=" + amount +
                ", componentId=" + componentId +
                ", supplierName='" + supplierName + '\'' +
                ", supplierContact='" + supplierContact + '\'' +
                ", price=" + price +
                ", total=" + total +
                '}';
    }
}