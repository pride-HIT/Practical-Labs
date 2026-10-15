import java.util.List;


public final class ImmutablePerson {
    private final String name;
    private final int age;
    private final List<String> hobbies;

    public ImmutablePerson(String name, int age, List<String> hobbies) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Name required");
        if (age < 0 || age > 150)           throw new IllegalArgumentException("Invalid age: " + age);
        this.name = name.trim();
        this.age = age;
        this.hobbies = List.copyOf(hobbies);      // defensive copy
    }

    public String getName()          { return name; }
    public int getAge()              { return age; }
    public List<String> getHobbies() { return hobbies; }   // already unmodifiable

    // "Wither": returns a NEW object instead of modifying this one
    public ImmutablePerson withAge(int newAge) {
        return new ImmutablePerson(name, newAge, hobbies);
    }

    @Override
    public String toString() { return name + ", " + age + ", hobbies=" + hobbies; }
}
