import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SaveLines {

    public static void main(String[] args) {
        Path file = Path.of("user_lines.txt");
        List<String> lines = new ArrayList<>();

        // ---- read 5 lines from the keyboard ----
        Scanner in = new Scanner(System.in);
        for (int i = 1; i <= 5; i++) {
            System.out.print("Enter line " + i + ": ");
            if (!in.hasNextLine()) {
                System.out.println("\nInput ended early.");
                break;
            }
            lines.add(in.nextLine());
        }

        // ---- write ----
        try {
            Files.write(file, lines);                       // creates or overwrites
            System.out.println("\nSaved " + lines.size() + " lines to " + file.toAbsolutePath());
        } catch (IOException e) {
            System.out.println("Could not write the file: " + e.getMessage());
            return;
        }

        // ---- read back ----
        try {
            List<String> loaded = Files.readAllLines(file);
            System.out.println("Reading the file back:");
            for (int i = 0; i < loaded.size(); i++) {
                System.out.println((i + 1) + ": " + loaded.get(i));
            }
        } catch (IOException e) {
            System.out.println("Could not read the file: " + e.getMessage());
        }
    }
}
