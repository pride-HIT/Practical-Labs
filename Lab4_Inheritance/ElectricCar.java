public class ElectricCar extends Car {
    private final int batteryKwh;

    public ElectricCar(String brand, int doors, int batteryKwh) {
        super(brand, doors);       
        this.batteryKwh = batteryKwh;
        System.out.println("3. ElectricCar constructor: " + batteryKwh + " kWh battery");
    }

    @Override
    public String toString() {
        return brand + " electric car, " + doors + " doors, " + batteryKwh + " kWh";
    }
}
