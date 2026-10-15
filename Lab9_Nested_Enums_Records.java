import java.util.EnumMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;


enum Level { LOW, MEDIUM, HIGH }

record Student(String name, int age) { }

class Book {
    private final String isbn;
    private final String title;

    Book(String isbn, String title) { this.isbn = isbn; this.title = title; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book other)) return false;
        return isbn.equals(other.isbn);
    }

    @Override
    public int hashCode() { return isbn.hashCode(); }

    @Override
    public String toString() { return title + " [" + isbn + "]"; }
}

class BrokenBook {
    private final String isbn;
    private final String title;

    BrokenBook(String isbn, String title) { this.isbn = isbn; this.title = title; }

    @Override
    public boolean equals(Object o) {
        return o instanceof BrokenBook other && isbn.equals(other.isbn);
    }
}


class SimpleLinkedList<T> implements Iterable<T> {


    private static class Node<E> {
        E value;
        Node<E> next;
        Node(E value) { this.value = value; }
    }

    private Node<T> head;
    private Node<T> tail;
    private int size;

    void add(T value) {
        Node<T> node = new Node<>(value);
        if (head == null) {
            head = tail = node;
        } else {
            tail.next = node;
            tail = node;
        }
        size++;
    }

    int size() { return size; }

   
    @Override
    public Iterator<T> iterator() { return new ListIterator(); }

    private class ListIterator implements Iterator<T> {
        private Node<T> current = head;

        @Override public boolean hasNext() { return current != null; }

        @Override public T next() {
            if (current == null) throw new NoSuchElementException();
            T value = current.value;
            current = current.next;
            return value;
        }
    }
}


enum Coin {
    PENNY(1), NICKEL(5), DIME(10), QUARTER(25);

    private final int cents;

    Coin(int cents) { this.cents = cents; }

    int cents() { return cents; }

  
    static Map<Coin, Integer> fewestCoins(int amount) {
        if (amount < 0) throw new IllegalArgumentException("Amount cannot be negative");
        Map<Coin, Integer> result = new EnumMap<>(Coin.class);   // keeps enum order
        Coin[] all = values();
        for (int i = all.length - 1; i >= 0 && amount > 0; i--) {
            int count = amount / all[i].cents;
            if (count > 0) {
                result.put(all[i], count);
                amount -= count * all[i].cents;
            }
        }
        return result;
    }
}

public class Lab9_Nested_Enums_Records {

    public static void main(String[] args) {
        basic1();
        basic2();
        intermediate3();
        intermediate4();
        challenge5();
    }

    static void advice(Level level) {
        switch (level) {                       // the compiler knows every enum constant
            case LOW    -> System.out.println("LOW: everything is fine, carry on.");
            case MEDIUM -> System.out.println("MEDIUM: keep an eye on it.");
            case HIGH   -> System.out.println("HIGH: act now!");
        }
    }

    static void basic1() {
        System.out.println("=== Basic 1: enum Level and switch ===");
        for (Level l : Level.values()) {
            advice(l);
        }
        System.out.println("valueOf(\"HIGH\") = " + Level.valueOf("HIGH") + ", ordinal = " + Level.HIGH.ordinal());
    }

    static void basic2() {
        System.out.println("\n=== Basic 2: record Student ===");
        Student s1 = new Student("Tariro", 20);
        Student s2 = new Student("Tariro", 20);
        Student s3 = new Student("Farai", 22);

        System.out.println(s1);                                   
        System.out.println("s1 == s2      : " + (s1 == s2));     
        System.out.println("s1.equals(s2) : " + s1.equals(s2));   
        System.out.println("s1.equals(s3) : " + s1.equals(s3));
        System.out.println("name component: " + s1.name() + ", age component: " + s1.age());
    }

    static void intermediate3() {
        System.out.println("\n=== Intermediate 3: equals/hashCode and HashSet<Book> ===");
        Set<Book> shelf = new HashSet<>();
        Book b1 = new Book("978-0132350884", "Clean Code");
        Book b2 = new Book("978-0132350884", "Clean Code (2nd copy)");   // same ISBN
        Book b3 = new Book("978-0134685991", "Effective Java");

        System.out.println("add b1: " + shelf.add(b1));
        System.out.println("add b2 (duplicate ISBN): " + shelf.add(b2));   
        System.out.println("add b3: " + shelf.add(b3));
        System.out.println("Set size: " + shelf.size() + "  " + shelf.contains(new Book("978-0132350884", "x")));

    
        Set<BrokenBook> broken = new HashSet<>();
        broken.add(new BrokenBook("111", "A"));
        broken.add(new BrokenBook("111", "A"));
        System.out.println("BrokenBook (equals but no hashCode) set size: " + broken.size() + "  <- duplicate got in");
    }

    static void intermediate4() {
        System.out.println("\n=== Intermediate 4: SimpleLinkedList in a for-each loop ===");
        SimpleLinkedList<String> list = new SimpleLinkedList<>();
        list.add("Harare");
        list.add("Bulawayo");
        list.add("Mutare");
        for (String city : list) {                 
            System.out.println("City: " + city);
        }
        System.out.println("Size: " + list.size());
    }

    static void challenge5() {
        System.out.println("\n=== Challenge 5: enum Coin, fewest coins ===");
        for (int amount : new int[] {41, 68, 99, 5, 0}) {
            Map<Coin, Integer> coins = Coin.fewestCoins(amount);
            int total = coins.values().stream().mapToInt(Integer::intValue).sum();
            System.out.println(amount + " cents -> " + coins + "  (" + total + " coins)");
        }
    }
}
