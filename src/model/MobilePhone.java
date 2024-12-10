package model;

public abstract class MobilePhone {
    protected int id;
    protected String name;
    protected double price;
    protected int quantity;
    protected String manufacturer;

    // Constructor
    public MobilePhone(int id, String name, double price, int quantity, String manufacturer) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.manufacturer = manufacturer;
    }

    // Getter và Setter cho id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getter và Setter cho name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter và Setter cho price
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    // Getter và Setter cho quantity
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Getter và Setter cho manufacturer
    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    // Phương thức toString
    @Override
    public abstract String toString();

    // Phương thức để chuyển đối tượng thành chuỗi CSV
    public abstract String toCSV();

    // Phương thức để chuyển đối tượng thành mảng chuỗi CSV
    public abstract String[] toCSVArray();
}
