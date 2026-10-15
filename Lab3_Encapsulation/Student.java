/** Basic 1 and 2: private fields, validated setter, derived grade. */
public class Student {
    private String name;
    private int marks;

    public Student(String name, int marks) {
        setName(name);
        setMarks(marks);          // reuse validation so the constructor cannot create a bad object
    }

    public String getName()  { return name; }
    public int getMarks()    { return marks; }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name required");
        }
        this.name = name.trim();
    }

    // Reject invalid marks (must be 0 to 100)
    public void setMarks(int marks) {
        if (marks < 0 || marks > 100) {
            throw new IllegalArgumentException("Marks must be between 0 and 100 but was " + marks);
        }
        this.marks = marks;
    }

   
    public String getGrade() {
        if (marks >= 80) return "A";
        if (marks >= 65) return "B";
        if (marks >= 50) return "C";
        return "F";
    }

    @Override
    public String toString() { return name + " - " + marks + " (" + getGrade() + ")"; }
}
