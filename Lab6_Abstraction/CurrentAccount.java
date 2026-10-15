public class CurrentAccount extends Account {
    public CurrentAccount(String number, double opening) { super(number, opening); }

    @Override public double monthlyFee() { return 10.0; }    // flat fee
}
