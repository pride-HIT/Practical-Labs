import java.time.Year;

// ---------- Basic 1 and 2: Car ----------
class Car {
    String brand;
    int year;
    double mileage;

    void display() {
        System.out.println(brand + " (" + year + "), mileage: " + mileage + " km");
    }

    // Basic 2: antique when MORE than 25 years old
    boolean isAntique() {
        int age = Year.now().getValue() - year;
        return age > 25;
    }
}

// ---------- Intermediate 3 and 4: Rectangle ----------
class Rectangle {
    double width;
    double height;

    double area()      { return width * height; }
    double perimeter() { return 2 * (width + height); }
}

// ---------- Challenge 5: Address and Person ----------
class Address {
    String street;
    String city;
}

class Person {
    String name;
    Address address;   
}

public class Lab1_Classes_and_Objects {

    static Car makeCar(String brand, int year, double mileage) {
        Car c = new Car();
        c.brand = brand;
        c.year = year;
        c.mileage = mileage;
        return c;
    }

    static Rectangle makeRect(double w, double h) {
        Rectangle r = new Rectangle();
        r.width = w;
        r.height = h;
        return r;
    }

    public static void main(String[] args) {
        basic1and2();
        intermediate3();
        intermediate4();
        challenge5();
    }

    static void basic1and2() {
        System.out.println("=== Basic 1 & 2: Car ===");
        Car[] cars = {
            makeCar("Toyota", 2018, 85000),
            makeCar("Mazda", 1995, 210000),
            makeCar("Honda", 2000, 150000)
        };
        for (Car c : cars) {
            c.display();
            System.out.println("  Antique? " + c.isAntique());
        }
    }

    static void intermediate3() {
        System.out.println("\n=== Intermediate 3: Rectangle with the largest area ===");
        Rectangle[] rects = {
            makeRect(2, 3), makeRect(4, 5), makeRect(1, 10), makeRect(6, 6), makeRect(3, 7)
        };
        Rectangle largest = rects[0];
        for (Rectangle r : rects) {
            System.out.println(r.width + " x " + r.height + "  area=" + r.area()
                    + "  perimeter=" + r.perimeter());
            if (r.area() > largest.area()) {
                largest = r;
            }
        }
        System.out.println("Largest: " + largest.width + " x " + largest.height
                + " with area " + largest.area());
    }

    static void intermediate4() {
        System.out.println("\n=== Intermediate 4: Aliasing ===");
        Rectangle r1 = makeRect(5);
        r2.width = 9;
        System.out.println("r1.width = " + r1.width);   

    }

    static void challenge5() {
        System.out.println("\n=== Challenge 5: Person has an Address ===");
        Address a = new Address();
        a.street = "12 Samora Machel Ave";
        a.city = "Harare";

        Person p1 = new Person();
        p1.name = "Tariro";
        p1.address = a;

        // chained dot: person -> address -> city
        System.out.println(p1.name + " lives in " + p1.address.city);

        Person p2 = new Person();
        p2.name = "Farai";          

        if (p2.address == null) {
            System.out.println(p2.name + " has no address yet (address is null)");
        }

        try {
            System.out.println(p2.address.city);
        } catch (NullPointerException e) {
            System.out.println("NullPointerException: cannot read 'city' because p2.address is null");
        }
    }
}
