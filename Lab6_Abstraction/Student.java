import java.util.Comparator;

public class Student implements Comparable<Student> {
    private final String name;
    private final int marks;

    public Student(String name, int marks) { this.name = name; this.marks = marks; }

    public String getName() { return name; }
    public int getMarks()   { return marks; }

    // Natural ordering: alphabetical by name
    @Override
    public int compareTo(Student other) { return name.compareTo(other.name); }

    // Alternative ordering: highest marks first 
    public static final Comparator<Student> BY_MARKS_DESC =
            (a, b) -> Integer.compare(b.marks, a.marks);

    @Override
    public String toString() { return name + "(" + marks + ")"; }
}
