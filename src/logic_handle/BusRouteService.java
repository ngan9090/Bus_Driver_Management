package logic_handle;

import Main.MainRun;
import entity.BusRoute;
import util.FileUtil;

import java.util.InputMismatchException;
import java.util.Scanner;

public class BusRouteService {
    public static final String BUS_ROUTE_FILE_NAME = "bus_route.dat";

    public static boolean isEmptyBusRoute() {
        return MainRun.BUS_ROUTES.isEmpty();
    }

    public static void createNewBusRoute() {
        System.out.print("Bạn muốn nhập thêm bao nhiêu tuyến xe mới: ");
        int newBusRouteNumber = 0;
        do {
            try {
                newBusRouteNumber = new Scanner(System.in).nextInt();
            } catch (InputMismatchException ex) {
                System.out.print("Số lượng tuyến mới phải là số nguyên, yêu cầu nhập lại: ");
                continue;
            }
            if (newBusRouteNumber > 0) {
                break;
            }
            System.out.print("Số lượng tuyến mới KHÔNG được là số âm, yêu cầu nhập lại: ");
        } while (true);
        for (int i = 0; i < newBusRouteNumber; i++) {
            BusRoute busRoute = new BusRoute();
            busRoute.inputNewData();
            MainRun.BUS_ROUTES.add(busRoute);
        }

        // lưu dữ liệu vào file
        FileUtil.writeDataToFile(MainRun.BUS_ROUTES, BUS_ROUTE_FILE_NAME);
    }

    public static void showBusRoute() {
        MainRun.BUS_ROUTES.forEach(System.out::println);
    }

    public static BusRoute findById(int busRouteId) {
        return MainRun.BUS_ROUTES
                .stream()
                .filter(driver -> driver.getId() == busRouteId)
                .findFirst()
                .orElse(null);
    }
}
