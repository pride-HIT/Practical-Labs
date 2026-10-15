public class Intern extends Employee {
    private final double stipend;

    public Intern(String name, double stipend) {
        super(name, 0);
        this.stipend = stipend;
    }

    @Override public double calculatePay() { return stipend; }
}
