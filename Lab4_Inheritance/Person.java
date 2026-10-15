public class Person {
    private final String name;
    private final int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() { return name; }
    public int getAge()     { return age; }

    // Inherited unchanged by Student and Teacher
    public void display() {
        System.out.println("Name: " + name + ", Age: " + age);
    }
}
