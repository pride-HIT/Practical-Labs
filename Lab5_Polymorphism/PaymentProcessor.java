
public class PaymentProcessor {

    // VERSION 1
    public static void payWithInstanceof(Payment p) {
        if (p instanceof CardPayment c) {                       // test AND cast in one step
            System.out.println("[v1] Card " + c.maskedCard() + " charged " + c.amount());
        } else if (p instanceof MobileMoneyPayment m) {
            System.out.println("[v1] Mobile money from " + m.phone() + " for " + m.amount());
        } else {
            throw new IllegalArgumentException("[v1] Unsupported payment type: "
                    + p.getClass().getSimpleName());
        }
    }

    // VERSION 2
    public static void pay(Payment p) {
        System.out.println("[v2] " + p.process());              // dynamic dispatch does the work
    }
}
