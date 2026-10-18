import java.util.ArrayList;
import java.util.List;

/**
 * Minimal CSV helper that copes with commas and quotes inside a field (e.g.
 * "Algorithms, 4th Edition").
 */
final class CsvUtil {

    private CsvUtil() {
    }

    static String join(String... fields) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < fields.length; i++) {
            if (i > 0)
                sb.append(',');
            sb.append(escape(fields[i]));
        }
        return sb.toString();
    }

    private static String escape(String field) {
        if (field == null)
            return "";
        if (field.contains(",") || field.contains("\"") || field.contains("\n")) {
            return "\"" + field.replace("\"", "\"\"") + "\"";
        }
        return field;
    }

    static List<String> split(String line) {
        List<String> fields = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (inQuotes) {
                if (c == '"') {
                    if (i + 1 < line.length() && line.charAt(i + 1) == '"') {
                        current.append('"'); // doubled quote = literal quote
                        i++;
                    } else {
                        inQuotes = false;
                    }
                } else {
                    current.append(c);
                }
            } else if (c == '"') {
                inQuotes = true;
            } else if (c == ',') {
                fields.add(current.toString());
                current.setLength(0);
            } else {
                current.append(c);
            }
        }
        fields.add(current.toString());
        return fields;
    }
}
