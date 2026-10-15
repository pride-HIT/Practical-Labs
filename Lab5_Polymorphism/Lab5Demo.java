public class Lab5Demo {

    public static void main(String[] args) {
        basic1();
        basic2();
        intermediate3();
        intermediate4();
        challenge5();
    }

    static void basic1() {
        System.out.println("=== Basic 1: overloaded print ===");
        PrintDemo p = new PrintDemo();
        p.print(42);
        p.print(3.14);
        p.print("hello");
        p.print(new int[] {1, 2, 3});
    }

    static void basic2() {
        System.out.println("\n=== Basic 2: Animal[] ===");
        Animal[] zoo = { new Cat(), new Cow(), new Duck(), new Animal() };
        for (Animal a : zoo) {
            System.out.println(a.name() + " says " + a.sound());
        }
    }

    static void intermediate3() {
        System.out.println("\n=== Intermediate 3: payroll with Intern ===");
        Employee[] staff = {
            new Employee("Kudzai", 2000),
            new Manager("Nyasha", 3500, 800),
            new Developer("Tatenda", 3000, 10),
            new Intern("Rudo", 600)
        };
        double total = 0;
        for (Employee e : staff) {                 
            System.out.printf("%-8s %.2f%n", e.getName(), e.calculatePay());
            total += e.calculatePay();
        }
        System.out.printf("Total payroll: %.2f%n", total);
    }

    static void intermediate4() {
        System.out.println("\n=== Intermediate 4: method vs field vs static ===");
        Parent p = new Child();          // declared type Parent, real object Child
        System.out.println("p.who()   -> " + p.who());     // Child method  
        System.out.println("p.label   -> " + p.label);     // Parent field  
        System.out.println("p.kind()  -> " + p.kind() + "   (static follows declared type)");
        System.out.println("Child's own field via cast -> " + ((Child) p).label);
        
    }

    static void challenge5() {
        System.out.println("\n=== Challenge 5: PaymentProcessor, two designs ===");
        Payment[] payments = {
            new CardPayment("4111111111111234", 250.0),
            new MobileMoneyPayment("0771234567", 40.0)
        };
        for (Payment p : payments) {
            PaymentProcessor.payWithInstanceof(p);
            PaymentProcessor.pay(p);
        }

        System.out.println("-- now add a brand-new payment type --");
        Payment crypto = new CryptoPayment(99.0);
        PaymentProcessor.pay(crypto);                           // works with zero changes
        try {
            PaymentProcessor.payWithInstanceof(crypto);         // breaks until we edit the processor
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
