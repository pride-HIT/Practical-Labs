public class MobileMoneyPayment implements Payment {
    private final String phone;
    private final double amount;

    public MobileMoneyPayment(String phone, double amount) {
        this.phone = phone;
        this.amount = amount;
    }

    public String phone() { return phone; }

    @Override public double amount() { return amount; }

    @Override public String process() {
        return "Mobile money sent from " + phone + " for " + amount;
    }
}
