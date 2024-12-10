package model;

public class OriginalPhone extends MobilePhone {
    private int warrantyTime; // Số ngày bảo hành
    private String warrantyScope; // "Toan Quoc" hoặc "Quoc Te"

    // Constructor
    public OriginalPhone(int id, String name, double price, int quantity, String manufacturer, int warrantyTime, String warrantyScope) {
        super(id, name, price, quantity, manufacturer);
        this.warrantyTime = warrantyTime;
        this.warrantyScope = warrantyScope;
    }

    // Getter và Setter cho warrantyTime
    public int getWarrantyTime() {
        return warrantyTime;
    }

    public void setWarrantyTime(int warrantyTime) {
        this.warrantyTime = warrantyTime;
    }

    // Getter và Setter cho warrantyScope
    public String getWarrantyScope() {
        return warrantyScope;
    }

    public void setWarrantyScope(String warrantyScope) {
        this.warrantyScope = warrantyScope;
    }

    // Phương thức toString() để hiển thị thông tin chi tiết của điện thoại
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

    // Phương thức để chuyển đối tượng thành chuỗi CSV
    @Override
    public String toCSV() {
        return id + ",Original," + name + "," + price + "," + quantity + "," + manufacturer + "," + warrantyTime + "," + warrantyScope;
    }

    // Phương thức để chuyển đối tượng thành mảng chuỗi CSV
    @Override
    public String[] toCSVArray() {
        return new String[] {
                String.valueOf(id),
                "Original",
                name,
                String.valueOf(price),
                String.valueOf(quantity),
                manufacturer,
                String.valueOf(warrantyTime),
                warrantyScope
        };
    }
}
