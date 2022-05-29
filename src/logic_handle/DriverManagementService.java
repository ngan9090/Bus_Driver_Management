package logic_handle;

import Main.MainRun;
import entity.BusRoute;
import entity.Driver;
import entity.DriverManagement;
import jdk.jfr.internal.tool.Main;
import util.DataUtil;
import util.FileUtil;

import java.util.*;
import java.util.stream.IntStream;

public class DriverManagementService {
    public static final String DRIVER_MANAGEMENT_FILE_NAME = "driver_management.dat";


    public static boolean checkData() {
        return DriverService.isEmptyDriver() || BusRouteService.isEmptyBusRoute();
    }

    public static void createDrivingSchedule() {
        if (checkData()) {
            System.out.println("Bạn cần nhập thông tin cho tài xế và tuyến xe trước khi phân công lái xe");
            return;
        }
        System.out.print("Nhập số lượng tài xế mà bạn muốn phân công trong hôm nay: ");
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
            Driver driver = inputDriver(i);


            System.out.print("Tài xế thứ " + (i + 1) + " muốn lái bao nhiêu tuyến trong ngày hôm nay: ");
            int busRouteNumber = 0;
            do {
                try {
                    busRouteNumber = new Scanner(System.in).nextInt();
                } catch (InputMismatchException ex) {
                    System.out.print("Số lượng tuyến phải là số nguyên, yêu cầu nhập lại: ");
                    continue;
                }
                if (busRouteNumber > 0) {
                    break;
                }
                System.out.print("Số lượng tuyến KHÔNG được là số âm, yêu cầu nhập lại: ");
            } while (true);

            Map<BusRoute, Integer> busRouteMap = new HashMap<>();
            int totalRound = 0;
            //
            int size = MainRun.DRIVER_MANAGEMENTS.size();
            int turnEqual = 0;
            for (int k = 0; k < size; k++) {
                if (size > 0 && MainRun.DRIVER_MANAGEMENTS.get(k).getDriver().getId() == driver.getId()) {
                    turnEqual +=1;
                    busRouteMap = MainRun.DRIVER_MANAGEMENTS.get(k).getBusRoutes();
                    int value = MainRun.DRIVER_MANAGEMENTS.get(k).getBusRoutes().size();
                    for (int h = value - 1; h < busRouteNumber + value - 1; h++) {
                        BusRoute busRoute = inputBusRouteMap(h);
                        int busRound = inputBusRound();
                        if ((totalRound + busRound) > 15) {
                            System.out.println("Lái xe này không được phép lái quá 15 lượt mỗi ngày.");
                            break;
                        }
                        busRouteMap.put(busRoute, busRound);
                        totalRound += busRound;
                    }
                    MainRun.DRIVER_MANAGEMENTS.get(k).setBusRoutes(busRouteMap);
                }
            }

            if (turnEqual == 0) {
                for (int j = 0; j < busRouteNumber; j++) {
                    BusRoute busRoute = inputBusRouteMap(j);
                    int busRound = inputBusRound();
                    if ((totalRound + busRound) > 15) {
                        System.out.println("Lái xe này không được phép lái quá 15 lượt mỗi ngày.");
                        break;
                    }
                    busRouteMap.put(busRoute, busRound);
                    totalRound += busRound;
                }
                DriverManagement driverManagement = new DriverManagement(driver, busRouteMap, totalRound);
                MainRun.DRIVER_MANAGEMENTS.add(driverManagement);
            }
            //

        }
        // ghi dữ liệu vào file
        FileUtil.writeDataToFile(MainRun.DRIVER_MANAGEMENTS, DRIVER_MANAGEMENT_FILE_NAME);
    }

    private static int inputBusRound() {
        System.out.print("Nhập số lượt chạy cho tuyến vừa chọn: ");
        int busRound = 0;
        do {
            try {
                busRound = new Scanner(System.in).nextInt();
            } catch (InputMismatchException ex) {
                System.out.print("Số lượt chạy của tuyến xe phải là số nguyên, yêu cầu nhập lại: ");
                continue;
            }
            if (busRound > 0) {
                break;
            }
            System.out.print("Số lượt chạy của tuyến xe KHÔNG được là số âm, yêu cầu nhập lại: ");
        } while (true);
        return busRound;
    }

    private static BusRoute inputBusRouteMap(int j) {

        System.out.print("Nhập tuyến thứ " + (j + 1) + " mà tài xế này muốn lái:");
        BusRoute busRoute = null;
        do {
            int busRouteId = 0;
            do {
                try {
                    busRouteId = new Scanner(System.in).nextInt();
                } catch (InputMismatchException ex) {
                    System.out.print("Mã tuyến xe phải là số nguyên, yêu cầu nhập lại: ");
                    continue;
                }
                if (busRouteId > 0) {
                    break;
                }
                System.out.print("Mã tuyến xe KHÔNG được là số âm, yêu cầu nhập lại: ");
            } while (true);

            // tìm kiếm xem có bus route trong danh sách hay không, nếu không có yêu cầu nhập lại
            busRoute = BusRouteService.findById(busRouteId);
            if (!DataUtil.isNull(busRoute)) {
                break;
            }
            System.out.print("Không tìm thấy tuyến có id vừa nhập, vui lòng nhập lại: ");
        } while (true);
        return busRoute;
    }

    //Tìm id tài xế có tồn tại trong managerment không, nếu không thì tạo mới, nếu có thì thêm route vào id đó
    private static Driver inputDriver(int i) {
        System.out.print("Nhập id tài xế thứ " + (i + 1) + " cần phân công: ");
        int driverId = 0;
        Driver driver = null;
        do {
            do {
                try {
                    driverId = new Scanner(System.in).nextInt();
                } catch (InputMismatchException ex) {
                    System.out.print("Mã tài xế phải là số nguyên, yêu cầu nhập lại: ");
                    continue;
                }
                if (driverId > 0) {
                    break;
                }
                System.out.print("Mã tài xế KHÔNG được là số âm, yêu cầu nhập lại: ");
            } while (true);

            // tìm kiếm xem có tài xế trong danh sách hay không, nếu không có yêu cầu nhập lại
            driver = DriverService.findById(driverId);

            if (!DataUtil.isNull(driver)) {
                break;
            }
            System.out.print("Không tìm thấy tài xế có id vừa nhập, vui lòng nhập lại: ");
        } while (true);
        // kiểm tra xem lái xe này đã tồn tại trong bảng phân công trước đó hay chưa
        return driver;
    }

    public static void showData() {
        MainRun.DRIVER_MANAGEMENTS.forEach(System.out::println);
    }

    public static void sortByDriverName() {
        MainRun.DRIVER_MANAGEMENTS.sort(Comparator.comparing(o -> o.getDriver().getName()));
        showData();
    }

    public static void sortRouteManagermentByTotal() {
        //MainRun.DRIVER_MANAGEMENTS.sort(Comparator.comparing(o -> Arrays.stream(o.getBusRoutes().values().toArray()).count()));
        for (int i = 0; i < MainRun.DRIVER_MANAGEMENTS.size() - 1; i++) {
            if (MainRun.DRIVER_MANAGEMENTS.get(i).getDriver() == null) {
                continue;
            }
            for (int j = 0 + i; j < MainRun.DRIVER_MANAGEMENTS.size(); j++) {
                if (MainRun.DRIVER_MANAGEMENTS.get(j).getDriver() != null) {
                    System.out.println(MainRun.DRIVER_MANAGEMENTS.get(i).compareTo(MainRun.DRIVER_MANAGEMENTS.get(j)));
                    if (MainRun.DRIVER_MANAGEMENTS.get(i).compareTo(MainRun.DRIVER_MANAGEMENTS.get(j)) < 0) {
                        DriverManagement temp1 = MainRun.DRIVER_MANAGEMENTS.get(i);
                        DriverManagement temp2 = MainRun.DRIVER_MANAGEMENTS.get(j);
                        MainRun.DRIVER_MANAGEMENTS.set(j, temp1);
                        MainRun.DRIVER_MANAGEMENTS.set(i, temp2);
                    }
                }
            }
        }
        showData();
    }

    //Sắp sếp
    public static void sortRouteManagerment() {
        System.out.println("Sắp sếp danh sách phân công lái xe");
        System.out.println("1.Theo tên lái xe");
        System.out.println("2.Tổng số tuyến giảm dần");
        System.out.print("Nhập sự lựa chọn của bạn: ");
        int turn = 0;
        do {
            try {
                turn = new Scanner(System.in).nextInt();
            } catch (InputMismatchException e) {
                System.out.print("Lựa chọn sắp sếp danh sách phải là số nguyên, yêu cầu nhập lại: ");
                continue;
            }
            if (turn > 0 && turn < 3) break;
            System.out.println("Lựa chọn cần nhập từ 1 đến 2. Xin vui lòng nhập lại: ");
        } while (true);

        switch (turn) {
            case 1:
                sortByDriverName();
                break;
            case 2:
                sortRouteManagermentByTotal();
                break;
        }
    }

    public static void calculateTotalRound() {
        MainRun.DRIVER_MANAGEMENTS.forEach(driverManagement -> {
            Set<Map.Entry<BusRoute, Integer>> entries = driverManagement.getBusRoutes().entrySet();
            System.out.println(driverManagement.getDriver());
            int a = 0;
            String b = "";
            for (Map.Entry<BusRoute, Integer> element : entries) {
                b += element.getKey().getId() + " ";
                a += (int) (element.getValue() * element.getKey().getDistance());
            }
            System.out.println(" =>Tổng khoảng cách chạy xe trong ngày của tài xế trên là: " + a + " (Tuyến: " + b + " )");
        });
    }
}
