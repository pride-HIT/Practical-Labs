
public class Temperature {
    private static final double ABSOLUTE_ZERO_K = 0.0;

    private final double kelvin;     // internal representation, hidden from callers

    public Temperature(double kelvin) {
        if (Double.isNaN(kelvin) || kelvin < ABSOLUTE_ZERO_K) {
            throw new IllegalArgumentException(String.format("Below absolute zero: %.2f K", kelvin));
        }
        this.kelvin = kelvin;
    }

    public static Temperature fromCelsius(double c)    { return new Temperature(c + 273.15); }
    public static Temperature fromFahrenheit(double f) { return new Temperature((f - 32) * 5 / 9 + 273.15); }

    public double getKelvin()     { return kelvin; }
    public double getCelsius()    { return kelvin - 273.15; }
    public double getFahrenheit() { return getCelsius() * 9 / 5 + 32; }

    @Override
    public String toString() {
        return String.format("%.2f K = %.2f C = %.2f F", kelvin, getCelsius(), getFahrenheit());
    }
}
