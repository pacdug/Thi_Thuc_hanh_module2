package model;

public class OriginalPhone extends MobilePhone {
    private int warrantyTime; // Số ngày bảo hành
    private String warrantyScope; // "Toan Quoc" hoặc "Quoc Te"

    public OriginalPhone(int id, String name, double price, int quantity, String manufacturer, int warrantyTime, String warrantyScope) {
        super(id, name, price, quantity, manufacturer);
        this.warrantyTime = warrantyTime;
        this.warrantyScope = warrantyScope;
    }

    // Getters and Setters
    public int getWarrantyTime() {
        return warrantyTime;
    }

    public void setWarrantyTime(int warrantyTime) {
        this.warrantyTime = warrantyTime;
    }

    public String getWarrantyScope() {
        return warrantyScope;
    }

    public void setWarrantyScope(String warrantyScope) {
        this.warrantyScope = warrantyScope;
    }

    @Override
    public String toString() {
        return "OriginalPhone{" +
                "ID=" + id +
                ", Tên='" + name + '\'' +
                ", Giá bán=" + price +
                ", Số lượng=" + quantity +
                ", Nhà sản xuất='" + manufacturer + '\'' +
                ", Thời gian bảo hành=" + warrantyTime +
                ", Phạm vi bảo hành='" + warrantyScope + '\'' +
                '}';
    }

    @Override
    public String toCSV() {
        return id + ",Original," + name + "," + price + "," + quantity + "," + manufacturer + "," + warrantyTime + "," + warrantyScope;
    }
}

