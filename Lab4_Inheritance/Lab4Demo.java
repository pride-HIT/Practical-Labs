public class Lab4Demo {

    public static void main(String[] args) {
        basic1and2();
        intermediate3();
        intermediate4();
        challenge5();
    }

    static void basic1and2() {
        System.out.println("=== Basic 1 & 2: Person, Student, Teacher ===");
        Student s = new Student("Tariro", 20, "Software Engineering");
        Teacher t = new Teacher("Mr Moyo", 41, "Java");
        s.display();                              
        System.out.println("  Course:  " + s.getCourse());
        t.display();                              
        System.out.println("  Subject: " + t.getSubject());
    }

    static void intermediate3() {
        System.out.println("\n=== Intermediate 3: constructor call order ===");
        ElectricCar ec = new ElectricCar("Tesla", 4, 75);
        System.out.println(ec);
        System.out.println("Order: Vehicle -> Car -> ElectricCar (superclass constructors run first)");
    }

    static void intermediate4() {
        System.out.println("\n=== Intermediate 4: Dog extends Animal(String) ===");
        Dog d = new Dog("Rex");
        System.out.println(d.name + " says " + d.sound());
        System.out.println("(see the comment in Animal.java for the failing version and why)");
    }

    static void challenge5() {
        System.out.println("\n=== Challenge 5: Stack by inheritance vs composition ===");
        BadStack bad = new BadStack();
        bad.push(1); bad.push(2); bad.push(3);
        bad.add(0, 999);                          // legal, but breaks LIFO thinking
        System.out.println("BadStack after add(0, 999): " + bad + "  <- inserted at the bottom!");
        bad.clear();                              // anyone can wipe it, sort it
        System.out.println("BadStack also inherits get(i), set(i,x), sort(), remove(i)... "
                + "none of which belong on a stack");

        GoodStack good = new GoodStack();
        good.push(1); good.push(2); good.push(3);
        System.out.println("GoodStack: " + good + ", pop -> " + good.pop() + ", peek -> " + good.peek());
    }
}
