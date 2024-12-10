package model;

public class ImportedPhone extends MobilePhone {
    private String importCountry; // Quốc gia xách tay
    private String status; // "Da sua chua" hoặc "Chua sua chua"

    public ImportedPhone(int id, String name, double price, int quantity, String manufacturer, String importCountry, String status) {
        super(id, name, price, quantity, manufacturer);
        this.importCountry = importCountry;
        this.status = status;
    }

    // Getters and Setters
    public String getImportCountry() {
        return importCountry;
    }

    public void setImportCountry(String importCountry) {
        this.importCountry = importCountry;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ImportedPhone{" +
                "ID=" + id +
                ", Tên='" + name + '\'' +
                ", Giá bán=" + price +
                ", Số lượng=" + quantity +
                ", Nhà sản xuất='" + manufacturer + '\'' +
                ", Quốc gia xách tay='" + importCountry + '\'' +
                ", Trạng thái='" + status + '\'' +
                '}';
    }

    @Override
    public String toCSV() {
        return id + ",Imported," + name + "," + price + "," + quantity + "," + manufacturer + "," + importCountry + "," + status;
    }
}

