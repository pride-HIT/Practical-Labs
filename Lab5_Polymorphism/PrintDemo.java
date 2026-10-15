import java.util.Arrays;

public class PrintDemo {

    void print(int x)      { System.out.println("int: " + x); }
    void print(double x)   { System.out.println("double: " + x); }
    void print(String x)   { System.out.println("String: " + x); }
    void print(int[] x)    { System.out.println("int[]: " + Arrays.toString(x)); }
}
