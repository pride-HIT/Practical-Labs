public class CryptoPayment implements Payment {
    private final double amount;

    public CryptoPayment(double amount) { this.amount = amount; }

    @Override public double amount() { return amount; }

    @Override public String process() { return "Crypto transfer of " + amount + " confirmed"; }
}
