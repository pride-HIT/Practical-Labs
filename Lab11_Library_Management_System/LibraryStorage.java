import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public final class LibraryStorage {

    static final String ITEMS = "items.csv";
    static final String MEMBERS = "members.csv";
    static final String LOANS = "loans.csv";

    private LibraryStorage() {
    }

    public static void save(Library library, Path dir) throws IOException {
        Files.createDirectories(dir);

        List<String> itemLines = new ArrayList<>();
        for (LibraryItem i : library.getItems()) {
            itemLines.add(CsvUtil.join(i.typeLabel(), i.getId(), i.getTitle(), i.csvExtra()));
        }
        Files.write(dir.resolve(ITEMS), itemLines);

        List<String> memberLines = new ArrayList<>();
        for (Member m : library.getMembers()) {
            memberLines.add(CsvUtil.join(m.getId(), m.getName()));
        }
        Files.write(dir.resolve(MEMBERS), memberLines);

        List<String> loanLines = new ArrayList<>();
        for (Loan l : library.getLoans()) {
            loanLines.add(CsvUtil.join(
                    l.getMember().getId(), l.getItem().getId(),
                    l.getBorrowDate().toString(), l.getDueDate().toString(),
                    l.getReturnDate() == null ? "" : l.getReturnDate().toString()));
        }
        Files.write(dir.resolve(LOANS), loanLines);
    }

    public static Library load(Path dir) throws IOException {
        Library library = new Library();

        List<String> lines = readLines(dir.resolve(ITEMS));
        for (int n = 0; n < lines.size(); n++) {
            if (lines.get(n).isBlank())
                continue;
            try {
                List<String> f = fields(lines.get(n), 4);
                library.addItem(ItemFactory.create(f.get(0), f.get(1), f.get(2), f.get(3)));
            } catch (RuntimeException e) {
                throw new IOException(ITEMS + " line " + (n + 1) + ": " + e.getMessage(), e);
            }
        }

        lines = readLines(dir.resolve(MEMBERS));
        for (int n = 0; n < lines.size(); n++) {
            if (lines.get(n).isBlank())
                continue;
            try {
                List<String> f = fields(lines.get(n), 2);
                library.registerMember(new Member(f.get(0), f.get(1)));
            } catch (RuntimeException e) {
                throw new IOException(MEMBERS + " line " + (n + 1) + ": " + e.getMessage(), e);
            }
        }

        lines = readLines(dir.resolve(LOANS));
        for (int n = 0; n < lines.size(); n++) {
            if (lines.get(n).isBlank())
                continue;
            try {
                List<String> f = fields(lines.get(n), 5);
                Member member = library.getMember(f.get(0));
                LibraryItem item = library.getItem(f.get(1));
                LocalDate returned = f.get(4).isBlank() ? null : LocalDate.parse(f.get(4));
                library.restoreLoan(new Loan(member, item,
                        LocalDate.parse(f.get(2)), LocalDate.parse(f.get(3)), returned));
            } catch (RuntimeException e) { // includes DateTimeParseException
                throw new IOException(LOANS + " line " + (n + 1) + ": " + e.getMessage(), e);
            }
        }
        return library;
    }

    private static List<String> readLines(Path file) throws IOException {
        return Files.exists(file) ? Files.readAllLines(file) : List.of();
    }

    private static List<String> fields(String line, int expected) {
        List<String> f = CsvUtil.split(line);
        if (f.size() != expected) {
            throw new IllegalArgumentException("expected " + expected + " fields but found " + f.size());
        }
        return f;
    }
}
