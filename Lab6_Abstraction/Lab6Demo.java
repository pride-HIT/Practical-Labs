import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Lab6Demo {

    public static void main(String[] args) {
        basic1();
        basic2();
        intermediate3();
        intermediate4();
        challenge5();
    }

    static void basic1() {
        System.out.println("=== Basic 1: abstract Shape ===");

        Shape[] shapes = { new Circle(5), new Rectangle(4, 6), new Square(3) };
        for (Shape s : shapes) s.describe();
    }

    static void basic2() {
        System.out.println("\n=== Basic 2: Flyable (unrelated classes) ===");
        Flyable[] things = { new Bird(), new Aeroplane() };
        for (Flyable f : things) f.fly();
    }

    static void intermediate3() {
        System.out.println("\n=== Intermediate 3: abstract Account ===");
        Account[] accounts = {
            new SavingsAccount("SAV-1", 500),
            new SavingsAccount("SAV-2", 2000),
            new CurrentAccount("CUR-1", 800)
        };
        for (Account a : accounts) {
            a.deposit(100);
            System.out.println("Before fee: " + a);
            a.applyMonthlyFee();
            System.out.printf("After fee:  %s -> balance %.2f%n", a.getAccountNumber(), a.getBalance());
        }
    }

    static void intermediate4() {
        System.out.println("\n=== Intermediate 4: Comparable and Comparator ===");
        List<Student> list = new ArrayList<>(List.of(
            new Student("Kuda", 65), new Student("Ada", 91), new Student("Lynn", 78)));
        Collections.sort(list);                           // natural order: by name
        System.out.println("By name:       " + list);
        list.sort(Student.BY_MARKS_DESC);                 // Comparator: by marks, highest first
        System.out.println("By marks desc: " + list);
    }

    static void challenge5() {
        System.out.println("\n=== Challenge 5: default method conflict ===");
        new DefaultConflict().hello();
    }
}
