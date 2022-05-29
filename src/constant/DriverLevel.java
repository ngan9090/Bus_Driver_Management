package constant;

public enum DriverLevel {
    LEVEL_A(1),
    LEVEL_B(2),
    LEVEL_C(3),
    LEVEL_D(4),
    LEVEL_E(5),
    LEVEL_F(6);

    public int value;

    DriverLevel(int value) {
        this.value = value;
    }
}
