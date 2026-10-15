import java.util.List;
import java.util.stream.Collectors;

record Pupil(String name, double mark) { }

public class AboveAverage {

    public static void main(String[] args) {
        List<Pupil> students = List.of(
            new Pupil("Tariro", 78), new Pupil("Farai", 54), new Pupil("Rudo", 91),
            new Pupil("Kuda", 66),   new Pupil("Chipo", 49), new Pupil("Simba", 83),
            new Pupil("Anesu", 72),  new Pupil("Nyasha", 60));

        double average = students.stream()
                .mapToDouble(Pupil::mark)
                .average()
                .orElse(0);
        System.out.printf("Class average: %.2f%n", average);

        List<String> aboveAverage = students.stream()
                .filter(s -> s.mark() > average)        
                .map(Pupil::name)                       
                .sorted()                               
                .collect(Collectors.toList());

        System.out.println("Above average (A-Z): " + aboveAverage);
    }
}
