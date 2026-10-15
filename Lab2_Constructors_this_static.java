
// ---------- Basic 1 and 2: Circle ----------
class Circle {
    private static int count = 0;      // Basic 2: one shared counter for all circles

    private final double radius;

    Circle(double radius) {
        this.radius = radius;
        count++;
    }

    Circle() {
        this(1.0);                     // no-arg constructor defaults the radius to 1.0
    }

    static int getCount() { return count; }

    double area() { return Math.PI * radius * radius; }

    @Override
    public String toString() { return String.format("Circle(r=%.1f, area=%.2f)", radius, area()); }
}

// ---------- Intermediate 3: Employee ----------
class Employee {
    private static int nextId = 1000;          // static counter -> auto-incrementing id

    private final int employeeId;
    private final String name;
    private final double salary;
    private final String department;

    // Constructor 1: full
    Employee(String name, double salary, String department) {
        this.employeeId = nextId++;
        this.name = name;
        this.salary = salary;
        this.department = department;
    }

    // Constructor 2: chains to constructor 1
    Employee(String name, double salary) {
        this(name, salary, "Unassigned");
    }

    // Constructor 3: chains to constructor 2
    Employee(String name) {
        this(name, 0.0);
    }

    // Copy constructor: same details, but a NEW employee gets a NEW id
    Employee(Employee other) {
        this(other.name, other.salary, other.department);
    }

    void display() {
        System.out.printf("#%d %s, %s, $%.2f%n", employeeId, name, department, salary);
    }
}

// ---------- Intermediate 4: two static blocks and two instance blocks ----------
class TwoBlocks {
    static { System.out.println("static block 1"); }
    static { System.out.println("static block 2"); }

    { System.out.println("instance block 1"); }
    { System.out.println("instance block 2"); }

    TwoBlocks() { System.out.println("constructor"); }

  
}

// ---------- Challenge 5: Temperature with static factory methods ----------
class Temperature {
    private final double celsius;

    private Temperature(double celsius) {     // private: callers must use the factories
        this.celsius = celsius;
    }

    static Temperature fromCelsius(double c)    { return new Temperature(c); }
    static Temperature fromFahrenheit(double f) { return new Temperature((f - 32) * 5 / 9); }

    double toCelsius()    { return celsius; }
    double toFahrenheit() { return celsius * 9 / 5 + 32; }

    @Override
    public String toString() { return String.format("%.1f C / %.1f F", toCelsius(), toFahrenheit()); }

   

public class Lab2_Constructors_this_static {

    public static void main(String[] args) {
        basic1and2();
        intermediate3();
        intermediate4();
        challenge5();
    }

    static void basic1and2() {
        System.out.println("=== Basic 1 & 2: Circle ===");
        Circle c1 = new Circle(5);
        Circle c2 = new Circle();          // default radius 1.0
        System.out.println(c1);
        System.out.println(c2);
        System.out.println("Circles created: " + Circle.getCount());
    }

    static void intermediate3() {
        System.out.println("\n=== Intermediate 3: Employee ===");
        Employee e1 = new Employee("Tendai", 2500, "IT");
        Employee e2 = new Employee("Chipo", 1800);
        Employee e3 = new Employee("Simba");
        Employee e4 = new Employee(e1);    // copy constructor
        e1.display();
        e2.display();
        e3.display();
        e4.display();
    }

    static void intermediate4() {
        System.out.println("\n=== Intermediate 4: static and instance blocks ===");
        System.out.println("--- first object ---");
        new TwoBlocks();
        System.out.println("--- second object ---");
        new TwoBlocks();
    }

    static void challenge5() {
        System.out.println("\n=== Challenge 5: Temperature factories ===");
        Temperature boiling = Temperature.fromCelsius(100);
        Temperature body = Temperature.fromFahrenheit(98.6);
        System.out.println("Boiling: " + boiling);
        System.out.println("Body:    " + body);

    }
}
}
