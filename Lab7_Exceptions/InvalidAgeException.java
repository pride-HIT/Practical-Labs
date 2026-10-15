public class InvalidAgeException extends Exception {

    private final int age;

    public InvalidAgeException(int age) {
        super("Invalid age " + age + ": must be between 0 and 120");
        this.age = age;
    }

    public int getAge() { return age; }
}
