public abstract class Account {
    private final String accountNumber;
    protected double balance;

    protected Account(String accountNumber, double openingBalance) {
        this.accountNumber = accountNumber;
        this.balance = openingBalance;
    }

    // concrete: identical for every account type
    public void deposit(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Deposit must be positive");
        balance += amount;
    }

    // abstract: every account type charges differently
    public abstract double monthlyFee();

    // template: uses the abstract method, so each subclass gets its own fee automatically
    public void applyMonthlyFee() {
        balance -= monthlyFee();
    }

    public String getAccountNumber() { return accountNumber; }
    public double getBalance()       { return balance; }

    @Override
    public String toString() {
        return String.format("%s %s balance=%.2f fee=%.2f",
                getClass().getSimpleName(), accountNumber, balance, monthlyFee());
    }
}
