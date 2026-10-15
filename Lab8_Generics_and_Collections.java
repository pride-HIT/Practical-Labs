import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


class Stack<T> {
    private final List<T> items = new ArrayList<>();    // composition: the list is hidden

    void push(T item) { items.add(item); }

    T pop() {
        if (items.isEmpty()) throw new IllegalStateException("Stack is empty");
        return items.remove(items.size() - 1);
    }

    T peek() {
        if (items.isEmpty()) throw new IllegalStateException("Stack is empty");
        return items.get(items.size() - 1);
    }

    boolean isEmpty() { return items.isEmpty(); }

    int size() { return items.size(); }

    @Override
    public String toString() { return items.toString(); }
}

public class Lab8_Generics_and_Collections {

    public static void main(String[] args) {
        basic1();
        basic2();
        intermediate3();
        intermediate4();
        challenge5();
    }

    // ---------- Basic 1: sum, maximum and reverse of 10 integers ----------
    static void basic1() {
        System.out.println("=== Basic 1: ArrayList of 10 integers ===");
        List<Integer> nums = new ArrayList<>(List.of(14, 7, 23, 5, 42, 19, 8, 31, 12, 27));

        int sum = 0;
        int max = nums.get(0);
        for (int n : nums) {              
            sum += n;
            if (n > max) max = n;
        }
        System.out.println("List:    " + nums);
        System.out.println("Sum:     " + sum);
        System.out.println("Maximum: " + max);

        List<Integer> reversed = new ArrayList<>(nums);   // copy so the original is untouched
        Collections.reverse(reversed);
        System.out.println("Reverse: " + reversed);
    }

    static void basic2() {
        System.out.println("\n=== Basic 2: remove duplicates using a Set ===");
        List<String> names = List.of("Zodwa", "Alice", "Moyo", "Alice", "Tariro", "Moyo", "Kuda");

        Set<String> unique = new LinkedHashSet<>(names);
        System.out.println("Original: " + names + " (" + names.size() + ")");
        System.out.println("Unique:   " + unique + " (" + unique.size() + ")");
        List<String> backToList = new ArrayList<>(unique);   // if a List is needed again
        System.out.println("As list:  " + backToList);
    }

    // ---------- Intermediate 3: use the generic Stack ----------
    static void intermediate3() {
        System.out.println("\n=== Intermediate 3: generic Stack<T> ===");
        Stack<String> words = new Stack<>();
        words.push("one");
        words.push("two");
        words.push("three");
        System.out.println("Stack: " + words + ", peek = " + words.peek() + ", size = " + words.size());
        System.out.println("pop -> " + words.pop());
        System.out.println("pop -> " + words.pop());
        System.out.println("isEmpty? " + words.isEmpty());

        Stack<Integer> numbers = new Stack<>();          // same class, different type
        numbers.push(10);
        numbers.push(20);
        int top = numbers.pop();                         // no cast needed
        System.out.println("Integer stack popped: " + top);

        words.pop();                                     // now empty
        try {
            words.pop();
        } catch (IllegalStateException e) {
            System.out.println("Popping an empty stack: " + e.getMessage());
        }
    }

    static void intermediate4() {
        System.out.println("\n=== Intermediate 4: word frequency ===");
        String paragraph = "The quick brown fox jumps over the lazy dog. "
                         + "The dog barks, and the fox runs away!";

        Map<String, Integer> freq = new HashMap<>();
        for (String w : paragraph.toLowerCase().split("\\W+")) {   
            if (!w.isEmpty()) {
                freq.merge(w, 1, Integer::sum);                    
            }
        }

       
        List<Map.Entry<String, Integer>> entries = new ArrayList<>(freq.entrySet());
        entries.sort(
            Map.Entry.<String, Integer>comparingByValue(Comparator.reverseOrder())
                     .thenComparing(Map.Entry.comparingByKey()));

        for (Map.Entry<String, Integer> e : entries) {
            System.out.printf("%-6s %d%n", e.getKey(), e.getValue());
        }
    }

    static <T extends Comparable<T>> void bubbleSort(T[] arr) {
        for (int pass = 0; pass < arr.length - 1; pass++) {
            boolean swapped = false;
            for (int i = 0; i < arr.length - 1 - pass; i++) {
                if (arr[i].compareTo(arr[i + 1]) > 0) {        // bounded type gives us compareTo
                    T tmp = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = tmp;
                    swapped = true;
                }
            }
            if (!swapped) break;                               // already sorted: stop early
        }
    }

    static void challenge5() {
        System.out.println("\n=== Challenge 5: generic bubbleSort ===");
        Integer[] numbers = {5, 2, 9, 1, 7};
        String[] words = {"pear", "apple", "zebra", "mango"};

        bubbleSort(numbers);
        bubbleSort(words);
        System.out.println("Integer[]: " + Arrays.toString(numbers));
        System.out.println("String[]:  " + Arrays.toString(words));

       
    }
}
