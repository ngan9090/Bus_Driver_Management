package Main;

import entity.BusRoute;
import entity.Driver;
import entity.DriverManagement;
import logic_handle.BusRouteService;
import logic_handle.DriverManagementService;
import logic_handle.DriverService;
import util.DataUtil;
import util.FileUtil;

import java.util.*;

public class MainRun {
    public static List<Driver> DRIVERS;
    public static List<BusRoute> BUS_ROUTES;
    public static List<DriverManagement> DRIVER_MANAGEMENTS;

    public static void main(String[] args) {
        // Lấy dữ liệu sẵn có từ file đã lưu
        initData();
        menu();
    }

    private static void initData() {
        DRIVERS = (List<Driver>) FileUtil.readDataFromFile(DriverService.DRIVER_FILE_NAME);
        if (DataUtil.isEmptyCollection(DRIVERS)) {
            DRIVERS = new ArrayList<>();
        }

        BUS_ROUTES = (List<BusRoute>) FileUtil.readDataFromFile(BusRouteService.BUS_ROUTE_FILE_NAME);
        if (DataUtil.isEmptyCollection(BUS_ROUTES)) {
            BUS_ROUTES = new ArrayList<>();
        }

        DRIVER_MANAGEMENTS = (List<DriverManagement>) FileUtil.readDataFromFile(DriverManagementService.DRIVER_MANAGEMENT_FILE_NAME);
        if (DataUtil.isEmptyCollection(DRIVER_MANAGEMENTS)) {
            DRIVER_MANAGEMENTS = new ArrayList<>();
        }
    }

    private static void menu() {
        do {
            int functionChoice = showMenu();
            switch (functionChoice) {
                case 1:
                    DriverService.createNewDriver();
                    break;
                case 2:
                    DriverService.showDriver();
                    break;
                case 3:
                    BusRouteService.createNewBusRoute();
                    break;
                case 4:
                    BusRouteService.showBusRoute();
                    break;
                case 5:
                    DriverManagementService.createDrivingSchedule();
                    break;
                case 6:
                    DriverManagementService.showData();
                    break;
                case 7:
                    DriverManagementService.sortRouteManagerment();
                    break;
                case 8:
                    DriverManagementService.calculateTotalRound();
                    break;
                case 9:
                    return;
            }
        } while (true);
    }

    private static int showMenu() {
        printMenu();
        int functionChoice = -1;
        do {
            try {
                functionChoice = new Scanner(System.in).nextInt();
            } catch (InputMismatchException ex) {
                System.out.print("Chức năng cần chọn là 1 số nguyên, yêu cầu nhập lại: ");
                continue;
            }
            if (functionChoice >= 1 && functionChoice <= 9) {
                break;
            }
            System.out.print("Chức năng vừa chọn không hợp lệ, vui lòng nhập lại: ");
        } while (true);
        return functionChoice;
    }

    private static void printMenu() {
        System.out.println("--------PHẦN MỀM QUẢN LÝ PHÂN CÔNG LÁT XE BUÝT------");
        System.out.println("1. Nhập danh sách lái xe mới.");
        System.out.println("2. In ra danh sách lái xe mới.");
        System.out.println("3. Nhập danh sách tuyến xe mới.");
        System.out.println("4. In ra danh sách tuyến xe mới.");
        System.out.println("5. Phân công lái xe cho các tài xế.");
        System.out.println("6. In ra danh sách đã phân công.");
        System.out.println("7. Sắp xếp danh sách phân công lái xe.");
        System.out.println("8. Lập bảng thống kê tổng khoảng cách chạy xe trong ngày của từng lái xe.");
        System.out.println("9. Thoát");
        System.out.print(" Xin mời chọn chức năng: ");
    }

}
