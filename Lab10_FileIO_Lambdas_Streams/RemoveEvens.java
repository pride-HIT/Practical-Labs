import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class RemoveEvens {

    public static void main(String[] args) {
        List<Integer> numbers = new ArrayList<>(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12));
        System.out.println("Before: " + numbers);

        Predicate<Integer> isEven = n -> n % 2 == 0;

        numbers.removeIf(isEven);      // removes every element for which the predicate is true
        System.out.println("After:  " + numbers);

        
    }
}
