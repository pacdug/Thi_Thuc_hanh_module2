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
    private List<MobilePhone> readPhones() {
        List<MobilePhone> phones = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            createFile(file);
            return phones;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                phones.add(parsePhone(line));
            }
        } catch (IOException e) {
            System.out.println("Error reading data file.");
        }
        return phones;
    }

    // Tạo file nếu không tồn tại
    private void createFile(File file) {
        file.getParentFile().mkdirs();
        try {
            file.createNewFile();
        } catch (IOException e) {
            System.out.println("Error creating data file.");
        }
    }

    // Phân tích dòng dữ liệu CSV thành đối tượng điện thoại
    private MobilePhone parsePhone(String line) {
        String[] data = line.split(",");
        if (data.length < 7) return null;

        int id = Integer.parseInt(data[0]);
        String type = data[1];
        String name = data[2];
        double price = Double.parseDouble(data[3]);
        int quantity = Integer.parseInt(data[4]);
        String manufacturer = data[5];

        if (type.equalsIgnoreCase("Original")) {
            return new OriginalPhone(id, name, price, quantity, manufacturer,
                    Integer.parseInt(data[6]), data[7]);
        } else if (type.equalsIgnoreCase("Imported")) {
            return new ImportedPhone(id, name, price, quantity, manufacturer, data[6], data[7]);
        }
        return null;
    }

    // Ghi danh sách điện thoại vào file CSV
    private void writePhones(List<MobilePhone> phones) {
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
    private int getNextId(List<MobilePhone> phones) {
        return phones.stream()
                .mapToInt(MobilePhone::getId)
                .max()
                .orElse(0) + 1;
    }

    // Thêm mới điện thoại
    public void addPhone(Scanner scanner) {
        List<MobilePhone> phones = readPhones();
        int newId = getNextId(phones);

        String choice = promptPhoneType(scanner);
        if (choice == null) return;

        String name = prompt("Nhập tên điện thoại: ", scanner);
        if (name == null) return;

        double price = promptForDouble("Nhập giá bán: ", scanner);
        if (price <= 0) return;

        int quantity = promptForInt("Nhập số lượng: ", scanner);
        if (quantity <= 0) return;

        String manufacturer = prompt("Nhập nhà sản xuất: ", scanner);
        if (manufacturer == null) return;

        if (choice.equals("1")) {
            addOriginalPhone(scanner, newId, name, price, quantity, manufacturer, phones);
        } else {
            addImportedPhone(scanner, newId, name, price, quantity, manufacturer, phones);
        }
    }

    // Prompt for phone type
    private String promptPhoneType(Scanner scanner) {
        System.out.println("Chọn loại điện thoại:");
        System.out.println("1. Chính hãng");
        System.out.println("2. Xách tay");
        System.out.print("Lựa chọn: ");
        String choice = scanner.nextLine();

        if (!choice.equals("1") && !choice.equals("2")) {
            System.out.println("Lựa chọn không hợp lệ.");
            return null;
        }
        return choice;
    }

    // Prompt input for string
    private String prompt(String message, Scanner scanner) {
        System.out.print(message);
        String input = scanner.nextLine();
        if (input.isEmpty()) {
            System.out.println("Input không được để trống.");
            return null;
        }
        return input;
    }

    // Prompt input for double values
    private double promptForDouble(String message, Scanner scanner) {
        String input = prompt(message, scanner);
        if (input == null) return -1;

        try {
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {
            System.out.println("Giá bán phải là số.");
            return -1;
        }
    }

    // Prompt input for integer values
    private int promptForInt(String message, Scanner scanner) {
        String input = prompt(message, scanner);
        if (input == null) return -1;

        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Số lượng phải là số.");
            return -1;
        }
    }

    // Thêm điện thoại chính hãng
    private void addOriginalPhone(Scanner scanner, int newId, String name, double price, int quantity, String manufacturer, List<MobilePhone> phones) {
        int warrantyTime = promptForInt("Nhập thời gian bảo hành (ngày): ", scanner);
        if (warrantyTime <= 0 || warrantyTime > 730) return;

        String warrantyScope = prompt("Nhập phạm vi bảo hành (Toan Quoc/Quoc Te): ", scanner);
        if (!warrantyScope.equalsIgnoreCase("Toan Quoc") && !warrantyScope.equalsIgnoreCase("Quoc Te")) {
            System.out.println("Phạm vi bảo hành không hợp lệ.");
            return;
        }

        OriginalPhone originalPhone = new OriginalPhone(newId, name, price, quantity, manufacturer, warrantyTime, warrantyScope);
        phones.add(originalPhone);
        writePhones(phones);
        System.out.println("Thêm điện thoại chính hãng thành công!");
    }

    // Thêm điện thoại xách tay
    private void addImportedPhone(Scanner scanner, int newId, String name, double price, int quantity, String manufacturer, List<MobilePhone> phones) {
        String importCountry = prompt("Nhập quốc gia xách tay: ", scanner);
        if (importCountry.equalsIgnoreCase("Viet Nam")) {
            System.out.println("Quốc gia xách tay không được là 'Viet Nam'.");
            return;
        }

        String status = prompt("Nhập trạng thái (Da sua chua/Chua sua chua): ", scanner);
        if (!(status.equalsIgnoreCase("Da sua chua") || status.equalsIgnoreCase("Chua sua chua"))) {
            System.out.println("Trạng thái không hợp lệ.");
            return;
        }

        ImportedPhone importedPhone = new ImportedPhone(newId, name, price, quantity, manufacturer, importCountry, status);
        phones.add(importedPhone);
        writePhones(phones);
        System.out.println("Thêm điện thoại xách tay thành công!");
    }

    // Xóa điện thoại
    public void deletePhone(Scanner scanner) {
        List<MobilePhone> phones = readPhones();
        if (phones.isEmpty()) {
            System.out.println("Danh sách điện thoại đang trống.");
            return;
        }

        int id = promptForInt("Nhập ID điện thoại cần xóa: ", scanner);
        if (id == -1) return;

        MobilePhone phoneToDelete = findPhoneById(phones, id);
        if (phoneToDelete == null) {
            try {
                throw new NotFoundProductException("ID điện thoại không tồn tại.");
            } catch (NotFoundProductException e) {
                System.out.println(e.getMessage());
                return;
            }
        }

        // Hiển thị thông tin điện thoại cần xóa và xác nhận
        System.out.println("Thông tin điện thoại cần xóa:");
        System.out.println(phoneToDelete);
        if (confirmDelete(scanner)) {
            phones.remove(phoneToDelete);
            writePhones(phones);
            System.out.println("Đã xóa điện thoại thành công!");
        } else {
            System.out.println("Hủy xóa điện thoại.");
        }
    }

    // Tìm điện thoại theo ID
    private MobilePhone findPhoneById(List<MobilePhone> phones, int id) {
        for (MobilePhone phone : phones) {
            if (phone.getId() == id) {
                return phone;
            }
        }
        return null;
    }

    // Xác nhận xóa điện thoại
    private boolean confirmDelete(Scanner scanner) {
        System.out.print("Bạn có chắc chắn muốn xóa (y/n)? ");
        String choice = scanner.nextLine();
        return choice.equalsIgnoreCase("y");
    }
}
