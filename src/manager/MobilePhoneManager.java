package manager;

import model.ImportedPhone;
import model.MobilePhone;
import model.OriginalPhone;
import exception.NotFoundProductException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MobilePhoneManager {
    private static final String FILE_PATH = "data/mobiles.csv";

    // Đọc danh sách điện thoại từ file CSV
    public List<MobilePhone> readPhones() {
        List<MobilePhone> phones = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            // Nếu file không tồn tại, tạo thư mục và file
            file.getParentFile().mkdirs();
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("Error creating data file.");
            }
            return phones;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                // Đảm bảo rằng dòng có ít nhất 7 trường (điện thoại chính hãng) hoặc 8 trường (xách tay)
                if (data.length < 7) continue;
                int id = Integer.parseInt(data[0]);
                String type = data[1];
                String name = data[2];
                double price = Double.parseDouble(data[3]);
                int quantity = Integer.parseInt(data[4]);
                String manufacturer = data[5];
                if (type.equalsIgnoreCase("Original")) {
                    if (data.length < 8) continue; // Đảm bảo đủ trường
                    int warrantyTime = Integer.parseInt(data[6]);
                    String warrantyScope = data[7];
                    phones.add(new OriginalPhone(id, name, price, quantity, manufacturer, warrantyTime, warrantyScope));
                } else if (type.equalsIgnoreCase("Imported")) {
                    if (data.length < 8) continue; // Đảm bảo đủ trường
                    String importCountry = data[6];
                    String status = data[7];
                    phones.add(new ImportedPhone(id, name, price, quantity, manufacturer, importCountry, status));
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading data file.");
        }
        return phones;
    }

    // Ghi danh sách điện thoại vào file CSV
    public void writePhones(List<MobilePhone> phones) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (MobilePhone phone : phones) {
                bw.write(phone.toCSV());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing to data file.");
        }
    }

    // Lấy ID tiếp theo
    public int getNextId(List<MobilePhone> phones) {
        int maxId = 0;
        for (MobilePhone phone : phones) {
            if (phone.getId() > maxId) {
                maxId = phone.getId();
            }
        }
        return maxId + 1;
    }

    // Thêm mới điện thoại
    public void addPhone(Scanner scanner) {
        List<MobilePhone> phones = readPhones();
        int newId = getNextId(phones);  // Ensure newId is generated properly

        System.out.println("Chọn loại điện thoại:");
        System.out.println("1. Chính hãng");
        System.out.println("2. Xách tay");
        System.out.print("Lựa chọn: ");
        String choice = scanner.nextLine();

        if (!choice.equals("1") && !choice.equals("2")) {
            System.out.println("Lựa chọn không hợp lệ.");
            return;
        }

        System.out.print("Nhập tên điện thoại: ");
        String name = scanner.nextLine();
        if (name.isEmpty()) {
            System.out.println("Tên điện thoại không được để trống.");
            return;
        }

        System.out.print("Nhập giá bán: ");
        String priceStr = scanner.nextLine();
        double price;
        try {
            price = Double.parseDouble(priceStr);
            if (price <= 0) {
                System.out.println("Giá bán phải là số dương.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Giá bán phải là số.");
            return;
        }

        System.out.print("Nhập số lượng: ");
        String quantityStr = scanner.nextLine();
        int quantity;
        try {
            quantity = Integer.parseInt(quantityStr);
            if (quantity <= 0) {
                System.out.println("Số lượng phải là số dương.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Số lượng phải là số.");
            return;
        }

        System.out.print("Nhập nhà sản xuất: ");
        String manufacturer = scanner.nextLine();
        if (manufacturer.isEmpty()) {
            System.out.println("Nhà sản xuất không được để trống.");
            return;
        }

        if (choice.equals("1")) {
            // Điện thoại chính hãng
            System.out.print("Nhập thời gian bảo hành (ngày): ");
            String warrantyTimeStr = scanner.nextLine();
            int warrantyTime;
            try {
                warrantyTime = Integer.parseInt(warrantyTimeStr);
                if (warrantyTime <= 0 || warrantyTime > 730) {
                    System.out.println("Thời gian bảo hành phải là số dương và không quá 730 ngày.");
                    return;
                }
            } catch (NumberFormatException e) {
                System.out.println("Thời gian bảo hành phải là số.");
                return;
            }

            System.out.print("Nhập phạm vi bảo hành (Toan Quoc/Quoc Te): ");
            String warrantyScope = scanner.nextLine();
            if (!(warrantyScope.equalsIgnoreCase("Toan Quoc") || warrantyScope.equalsIgnoreCase("Quoc Te"))) {
                System.out.println("Phạm vi bảo hành chỉ có thể là 'Toan Quoc' hoặc 'Quoc Te'.");
                return;
            }

            OriginalPhone originalPhone = new OriginalPhone(newId, name, price, quantity, manufacturer, warrantyTime, warrantyScope);
            phones.add(originalPhone);
            writePhones(phones);
            System.out.println("Thêm điện thoại chính hãng thành công!");

        } else {
            // Điện thoại xách tay
            System.out.print("Nhập quốc gia xách tay: ");
            String importCountry = scanner.nextLine();
            if (importCountry.equalsIgnoreCase("Viet Nam")) {
                System.out.println("Quốc gia xách tay không được là 'Viet Nam'.");
                return;
            }

            System.out.print("Nhập trạng thái (Da sua chua/Chua sua chua): ");
            String status = scanner.nextLine();
            if (!(status.equalsIgnoreCase("Da sua chua") || status.equalsIgnoreCase("Chua sua chua"))) {
                System.out.println("Trạng thái chỉ có thể là 'Da sua chua' hoặc 'Chua sua chua'.");
                return;
            }

            ImportedPhone importedPhone = new ImportedPhone(newId, name, price, quantity, manufacturer, importCountry, status);
            phones.add(importedPhone);
            writePhones(phones);
            System.out.println("Thêm điện thoại xách tay thành công!");
        }
    }

    // Xóa điện thoại
    public void deletePhone(Scanner scanner) {
        List<MobilePhone> phones = readPhones();
        if (phones.isEmpty()) {
            System.out.println("Danh sách điện thoại đang trống.");
            return;
        }

        System.out.print("Nhập ID điện thoại cần xóa: ");
        String idStr = scanner.nextLine();
        int id;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            System.out.println("ID phải là số.");
            return;
        }

        MobilePhone phoneToDelete = null;
        for (MobilePhone phone : phones) {
            if (phone.getId() == id) {
                phoneToDelete = phone;
                break;
            }
        }

        if (phoneToDelete == null) {
            try {
                throw new NotFoundProductException("ID điện thoại không tồn tại.");
            } catch (NotFoundProductException e) {
                System.out.println(e.getMessage());
                return;
            }
        }

        // Hiển thị thông tin điện thoại cần xóa
        System.out.println("Thông tin điện thoại cần xóa:");
        System.out.println(phoneToDelete.toString());

        System.out.print("Bạn có chắc chắn muốn xóa? (Yes/No): ");
        String confirm = scanner.nextLine();
        if (confirm.equalsIgnoreCase("Yes")) {
            phones.remove(phoneToDelete);
            writePhones(phones);
            System.out.println("Đã xóa điện thoại thành công!");
            viewPhones(); // Hiển thị lại danh sách sau khi xóa
        } else {
            System.out.println("Hủy xóa điện thoại.");
        }
    }

    // Xem danh sách điện thoại
    public void viewPhones() {
        List<MobilePhone> phones = readPhones();
        if (phones.isEmpty()) {
            System.out.println("Danh sách điện thoại đang trống.");
            return;
        }

        System.out.println("----- Danh sách điện thoại -----");
        for (MobilePhone phone : phones) {
            System.out.println(phone.toString());
        }
    }

    // Tìm kiếm điện thoại
    public void searchPhones(Scanner scanner) {
        List<MobilePhone> phones = readPhones();
        if (phones.isEmpty()) {
            System.out.println("Danh sách điện thoại đang trống.");
            return;
        }

        System.out.print("Nhập ID hoặc tên điện thoại cần tìm kiếm: ");
        String query = scanner.nextLine().toLowerCase();

        List<MobilePhone> results = new ArrayList<>();
        for (MobilePhone phone : phones) {
            if (String.valueOf(phone.getId()).equals(query) || phone.getName().toLowerCase().contains(query)) {
                results.add(phone);
            }
        }

        if (results.isEmpty()) {
            System.out.println("Không tìm thấy điện thoại phù hợp.");
        } else {
            System.out.println("----- Kết quả tìm kiếm -----");
            for (MobilePhone phone : results) {
                System.out.println(phone.toString());
            }
        }
    }
        }