public class Child extends Parent {
    String label = "Child field";            // Hides the parent's field, does not override it

    @Override String who() { return "Child method"; }      // true overriding

    static String kind() { return "Child static method"; } // Hides, does not override
}
