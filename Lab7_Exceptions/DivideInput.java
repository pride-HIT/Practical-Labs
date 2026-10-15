import java.util.InputMismatchException;
import java.util.Scanner;


public class DivideInput {

    public static void main(String[] args) {

        try (Scanner in = new Scanner(System.in)) {
            System.out.print("Enter the first integer: ");
            int a = in.nextInt();
            System.out.print("Enter the second integer: ");
            int b = in.nextInt();

            int result = a / b;                          
            System.out.println(a + " / " + b + " = " + result);

        } catch (InputMismatchException e) {
            System.out.println("\nInvalid input: please enter whole numbers only.");
        } catch (ArithmeticException e) {
            System.out.println("Math error: cannot divide by zero.");
        } finally {
            System.out.println("Program finished.");
        }
    }
}
