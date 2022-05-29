package logic_handle;

import Main.MainRun;
import entity.Driver;
import util.FileUtil;

import java.util.InputMismatchException;
import java.util.Scanner;

public class DriverService {
    public static final String DRIVER_FILE_NAME = "driver.dat";

    public static boolean isEmptyDriver() {
        return MainRun.DRIVERS.isEmpty();
    }

    public static void showDriver() {
        MainRun.DRIVERS.forEach(System.out::println);
    }

    public static void createNewDriver() {
        System.out.print("Bạn muốn nhập thêm mấy lái xe mới: ");
        int newDriverNumber = 0;
        do {
            try {
                newDriverNumber = new Scanner(System.in).nextInt();
            } catch (InputMismatchException ex) {
                System.out.print("Số lượng tài xế phải là số nguyên, yêu cầu nhập lại: ");
                continue;
            }
            if (newDriverNumber > 0) {
                break;
            }
            System.out.print("Số lượng tài xế KHÔNG được là số âm, yêu cầu nhập lại: ");
        } while (true);
        for (int i = 0; i < newDriverNumber; i++) {
            Driver driver = new Driver();
            driver.inputNewData();
            MainRun.DRIVERS.add(driver);
        }

        // lưu dữ liệu vào file
        FileUtil.writeDataToFile(MainRun.DRIVERS, DRIVER_FILE_NAME);
    }

    public static Driver findById(int id) {
        return MainRun.DRIVERS
                .stream()
                .filter(driver -> driver.getId() == id)
                .findFirst()
                .orElse(null);
    }

}
