import java.util.List;

public class Lab3Demo {

    public static void main(String[] args) {
        basic1and2();
        intermediate3();
        intermediate4();
        challenge5();
    }

    static void basic1and2() {
        System.out.println("=== Basic 1 & 2: Student with validated marks and grade ===");
        Student s = new Student("Tariro", 82);
        System.out.println(s);
        s.setMarks(48);
        System.out.println(s);
        for (int bad : new int[] {-5, 101}) {
            try {
                s.setMarks(bad);
            } catch (IllegalArgumentException e) {
                System.out.println("Rejected: " + e.getMessage());
            }
        }
        System.out.println("Marks unchanged after rejection: " + s.getMarks());
    }

    static void intermediate3() {
        System.out.println("\n=== Intermediate 3: BankAccount with transaction history ===");
        BankAccount acc = new BankAccount("ACC-001", "Tendai", 500);
        acc.deposit(250);
        acc.withdraw(100);
        acc.withdraw(5000);              // fails, but is still recorded
        List<String> h = acc.getTransactionHistory();
        h.forEach(line -> System.out.println("  " + line));
        try {
            h.add("Fake transaction");   // blocked
        } catch (UnsupportedOperationException e) {
            System.out.println("History copy is read-only: cannot be tampered with");
        }
        acc.deposit(10);
        System.out.println("Old copy still has " + h.size() + " entries, account now has "
                + acc.getTransactionHistory().size());
    }

    static void intermediate4() {
        System.out.println("\n=== Intermediate 4: ImmutablePerson ===");
        List<String> hobbies = new java.util.ArrayList<>(List.of("chess", "coding"));
        ImmutablePerson p = new ImmutablePerson("Rudo", 20, hobbies);
        hobbies.add("football");         // changing the ORIGINAL list does not affect p
        System.out.println(p);
        ImmutablePerson older = p.withAge(21);
        System.out.println(older + "  (new object)");
        System.out.println(p + "  (original unchanged)");
        try {
            p.getHobbies().add("hacking");
        } catch (UnsupportedOperationException e) {
            System.out.println("Cannot modify hobbies through the getter");
        }
    }

    static void challenge5() {
        System.out.println("\n=== Challenge 5: Temperature stored in kelvin ===");
        Temperature t = Temperature.fromCelsius(25);
        System.out.println(t);
        System.out.println(Temperature.fromFahrenheit(212));
        System.out.println(Temperature.fromCelsius(-273.15));   // absolute zero is allowed
        try {
            Temperature.fromCelsius(-300);
        } catch (IllegalArgumentException e) {
            System.out.println("Rejected: " + e.getMessage());
        }
    }
}
