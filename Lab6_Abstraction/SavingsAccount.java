public class SavingsAccount extends Account {
    public SavingsAccount(String number, double opening) { super(number, opening); }

    // Fee waived when the balance is 1000 or more
    @Override public double monthlyFee() { return balance >= 1000 ? 0.0 : 5.0; }
}
