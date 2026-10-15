public class Lab7Demo {

    public static void main(String[] args) {
        basic2();
        intermediate3();
        intermediate4();
        challenge5();
        System.out.println("\n(Basic 1 is interactive: run  java DivideInput)");
    }

    static void basic2() {
        System.out.println("=== Basic 2: ArrayIndexOutOfBoundsException ===");
        int[] data = {10, 20, 30};
        System.out.println("data[1] = " + ArrayAccess.safeGet(data, 1));
        System.out.println("data[5] = " + ArrayAccess.safeGet(data, 5));
    }

    static void intermediate3() {
        System.out.println("\n=== Intermediate 3: InvalidAgeException ===");
        int[] ages = {25, -3, 121, 0, 120};
        for (int age : ages) {
            try {
                AgeValidator.validateAge(age);
            } catch (InvalidAgeException e) {
                System.out.println("Rejected: " + e.getMessage());
            }
        }
    }

    static void intermediate4() {
        System.out.println("\n=== Intermediate 4: withdraw throws InsufficientFundsException ===");
        BankAccount acc = new BankAccount("ACC-001", 500);
        try {
            acc.withdraw(200);
            System.out.println("Balance after withdrawing 200: " + acc.getBalance());
            acc.withdraw(1000);                       // fails
            System.out.println("never printed");
        } catch (InsufficientFundsException e) {
            System.out.println(e.getMessage());
            System.out.println("Short by: " + e.getShortfall());
        }
        System.out.println("Balance unchanged after failure: " + acc.getBalance());
    }

    static void challenge5() {
        System.out.println("\n=== Challenge 5: return in try, print in finally ===");
        FinallyDemo.run();
    }
}
