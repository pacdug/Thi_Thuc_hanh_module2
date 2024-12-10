package main;

import manager.MobilePhoneManager;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        MobilePhoneManager manager = new MobilePhoneManager();
        Scanner scanner = new Scanner(System.in);
        String choice;

        while (true) {
            System.out.println("----- MENU CHÍNH -----");
            System.out.println("1. Thêm mới điện thoại");
            System.out.println("2. Xóa điện thoại");
            System.out.println("3. Xem danh sách điện thoại");
            System.out.println("4. Tìm kiếm điện thoại");
            System.out.println("5. Thoát");
            System.out.print("Lựa chọn của bạn: ");
            choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    manager.addPhone(scanner);
                    break;
                case "2":
                    manager.deletePhone(scanner);
                    break;
                case "3":
                    manager.viewPhones();
                    break;
                case "4":
                    manager.searchPhones(scanner);
                    break;
                case "5":
                    System.out.println("Thoát ứng dụng. Tạm biệt!");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn lại.");
            }

            System.out.println(); // Dòng trống để dễ nhìn
        }
    }
}
