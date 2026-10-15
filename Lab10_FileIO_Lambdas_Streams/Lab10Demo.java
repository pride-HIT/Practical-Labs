
public class Lab10Demo {

    public static void main(String[] args) {
        System.out.println("=== Basic 2: Predicate and removeIf ===");
        RemoveEvens.main(args);

        System.out.println("\n=== Intermediate 3: lines, words, characters ===");
        TextStats.main(args);

        System.out.println("\n=== Intermediate 4: above-average students (streams) ===");
        AboveAverage.main(args);

        System.out.println("\n=== Challenge 5: grade band reports (groupingBy) ===");
        GradeReports.main(args);

        System.out.println("\n(Basic 1 is interactive: run  java SaveLines)");
    }
}
