import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;


public class TextStats {

    public static void main(String[] args) {
        String fileName = args.length > 0 ? args[0] : "sample.txt";
        Path path = Path.of(fileName);

        try {
            List<String> lines = Files.readAllLines(path);

            int lineCount = lines.size();
            int wordCount = 0;
            int charCount = 0;
            for (String line : lines) {
                charCount += line.length();                    // characters, excluding line breaks
                if (!line.isBlank()) {
                    wordCount += line.trim().split("\\s+").length;
                }
            }

            System.out.println("File:       " + path);
            System.out.println("Lines:      " + lineCount);
            System.out.println("Words:      " + wordCount);
            System.out.println("Characters: " + charCount + " (not counting line breaks)");

        } catch (NoSuchFileException e) {
            System.out.println("File not found: " + e.getMessage()
                    + "  (run this from the folder that contains the file)");
        } catch (IOException e) {
            System.out.println("I/O error: " + e.getMessage());
        }
    }
}
