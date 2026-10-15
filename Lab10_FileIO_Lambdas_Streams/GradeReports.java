import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;


public class GradeReports {

    static String band(Learner l) {
        if (l.gpa() >= 3.5) return "A";
        if (l.gpa() >= 3.0) return "B";
        return "C";
    }

    public static void main(String[] args) {
        Path input = Path.of("learners.csv");
        Path outDir = Path.of("reports");

        try {
            // ---- load ----
            List<Learner> learners = new ArrayList<>();
            for (String line : Files.readAllLines(input)) {
                if (!line.isBlank()) learners.add(Learner.fromCsv(line));
            }
            System.out.println("Loaded " + learners.size() + " learners from " + input);

            // ---- group by band  ----
            Map<String, List<Learner>> byBand = learners.stream()
                    .collect(Collectors.groupingBy(GradeReports::band, TreeMap::new, Collectors.toList()));

            // ---- write one report per band ----
            Files.createDirectories(outDir);
            for (Map.Entry<String, List<Learner>> entry : byBand.entrySet()) {
                String bandName = entry.getKey();
                List<Learner> members = entry.getValue();

                double avg = members.stream().mapToDouble(Learner::gpa).average().orElse(0);

                List<String> report = new ArrayList<>();
                report.add("GRADE BAND " + bandName + " REPORT");
                report.add("Learners: " + members.size() + ", average GPA: " + String.format("%.2f", avg));
                report.add("--------------------------------");
                members.stream()
                       .sorted((a, b) -> Double.compare(b.gpa(), a.gpa()))      // best first
                       .forEach(l -> report.add(String.format("%-10s roll %-3d gpa %.1f",
                                                              l.name(), l.roll(), l.gpa())));

                Path out = outDir.resolve("report_" + bandName + ".txt");
                Files.write(out, report);
                System.out.println("Wrote " + out + " (" + members.size() + " learners)");
            }

        } catch (IOException e) {
            System.out.println("File problem: " + e.getMessage()
                    + "  (run this from the folder that contains learners.csv)");
        }
    }
}
