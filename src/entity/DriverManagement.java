package entity;

import Main.MainRun;

import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

public class DriverManagement implements Serializable,Comparable<DriverManagement> {
    private Driver driver;
    private Map<BusRoute, Integer> busRoutes;
    private int totalRound;

    public DriverManagement() {
    }

    public DriverManagement(Driver driver, Map<BusRoute, Integer> busRoutes, int totalRound) {
        this.driver = driver;
        this.busRoutes = busRoutes;
        this.totalRound = totalRound;
    }


    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Map<BusRoute, Integer> getBusRoutes() {
        return busRoutes;
    }

    public void setBusRoutes(Map<BusRoute, Integer> busRoutes) {
        this.busRoutes = busRoutes;
    }

    public int getTotalRound() {
        return totalRound;
    }

    public void setTotalRound(int totalRound) {
        this.totalRound = totalRound;
    }

    @Override
    public String toString() {
        return "DriverManagement{" +
                "driver=" + driver +
                ", busRoutes=" + busRoutes +
                '}';
    }


    @Override
    public int compareTo(DriverManagement o) {
        int a = 0;
        int b = 0;
        for(int i = 0; i < this.getBusRoutes().size();i++){
            Collection<Integer> arr = this.getBusRoutes().values();
            a += (int) arr.toArray()[i];
        }
        for(int j = 0; j < o.getBusRoutes().size();j++){
            Collection<Integer> arrB = o.getBusRoutes().values();
            b += (int) arrB.toArray()[j];
        }
        System.out.println(a + "A");
        System.out.println(b + "B");
        return (a - b);
    }

}
