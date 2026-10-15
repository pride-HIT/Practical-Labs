public class Car extends Vehicle {
    protected final int doors;

    public Car(String brand, int doors) {
        super(brand);              // Vehicle runs FIRST
        this.doors = doors;
        System.out.println("2. Car constructor: " + doors + " doors");
    }
}
