public class Developer extends Employee {
    private final int overtimeHours;
    private static final double OVERTIME_RATE = 25.0;

    public Developer(String name, double baseSalary, int overtimeHours) {
        super(name, baseSalary);
        this.overtimeHours = overtimeHours;
    }

    @Override public double calculatePay() { return baseSalary + overtimeHours * OVERTIME_RATE; }
}
