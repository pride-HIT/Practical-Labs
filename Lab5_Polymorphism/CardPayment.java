public class CardPayment implements Payment {
    private final String cardNumber;
    private final double amount;

    public CardPayment(String cardNumber, double amount) {
        this.cardNumber = cardNumber;
        this.amount = amount;
    }

    public String maskedCard() { return "****" + cardNumber.substring(cardNumber.length() - 4); }

    @Override public double amount() { return amount; }

    @Override public String process() {
        return "Charged card " + maskedCard() + " for " + amount;
    }
}
