import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

public class LibraryTests {

    private static int passed = 0;
    private static int failed = 0;

    interface Action {
        void run() throws Exception;
    }

    private static void check(String name, boolean condition) {
        if (condition) {
            passed++;
            System.out.println("  PASS  " + name);
        } else {
            failed++;
            System.out.println("  FAIL  " + name);
        }
    }

    private static void expectThrows(String name, Class<? extends Throwable> type, Action action) {
        try {
            action.run();
            failed++;
            System.out.println("  FAIL  " + name + " (no exception thrown)");
        } catch (Throwable t) {
            check(name + " -> " + type.getSimpleName(), type.isInstance(t));
        }
    }

    /** Fresh library: 3 books, 1 DVD, 2 members. */
    private static Library sample() {
        Library lib = new Library();
        lib.addItem(new Book("B1", "Clean Code", "Robert C. Martin"));
        lib.addItem(new Book("B2", "Effective Java", "Joshua Bloch"));
        lib.addItem(new Book("B3", "Head First Java", "Kathy Sierra"));
        lib.addItem(new Book("B4", "Algorithms", "Cormen"));
        lib.addItem(new Dvd("D1", "The Social Network", 120));
        lib.registerMember(new Member("M1", "Tariro"));
        lib.registerMember(new Member("M2", "Farai"));
        return lib;
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Running Library tests...\n");

        System.out.println("Borrowing and polymorphism");
        borrowingTests();
        System.out.println("\nRules and exceptions");
        ruleTests();
        System.out.println("\nReturning");
        returnTests();
        System.out.println("\nStreams: overdue and search");
        queryTests();
        System.out.println("\nValidation");
        validationTests();
        System.out.println("\nCSV and persistence");
        persistenceTests();

        System.out.printf("%nResult: %d passed, %d failed%n", passed, failed);
        if (failed > 0)
            System.exit(1);
    }

    static void borrowingTests() throws Exception {
        Library lib = sample();
        LocalDate day = LocalDate.of(2026, 10, 1);

        Loan book = lib.borrow("M1", "B1", day);
        check("borrowed book becomes unavailable", !lib.getItem("B1").isAvailable());
        check("member's active loan count is 1", lib.getMember("M1").getActiveLoanCount() == 1);
        check("book due date = borrow date + 21 days", book.getDueDate().equals(day.plusDays(21)));

        Loan dvd = lib.borrow("M2", "D1", day);
        check("DVD due date = borrow date + 7 days (polymorphism)", dvd.getDueDate().equals(day.plusDays(7)));
        check("item knows who borrowed it", lib.getItem("D1").getBorrowedBy().getId().equals("M2"));
    }

    static void ruleTests() throws Exception {
        Library lib = sample();
        lib.borrow("M1", "B1");

        expectThrows("borrowing an item already on loan", ItemNotAvailableException.class,
                () -> lib.borrow("M2", "B1"));

        lib.borrow("M1", "B2");
        lib.borrow("M1", "B3");
        expectThrows("4th loan for one member", MemberLimitExceededException.class,
                () -> lib.borrow("M1", "B4"));
        check("failed borrow leaves the item available (no half-done state)", lib.getItem("B4").isAvailable());
        check("member still has exactly 3 loans", lib.getMember("M1").getActiveLoanCount() == 3);

        expectThrows("unknown member", IllegalArgumentException.class, () -> lib.borrow("NOPE", "B4"));
        expectThrows("unknown item", IllegalArgumentException.class, () -> lib.borrow("M2", "NOPE"));
    }

    static void returnTests() throws Exception {
        Library lib = sample();
        lib.borrow("M1", "B1");
        Loan loan = lib.giveBack("B1");
        check("returned item is available again", lib.getItem("B1").isAvailable());
        check("member's loan count drops to 0", lib.getMember("M1").getActiveLoanCount() == 0);
        check("loan is no longer active", !loan.isActive());
        expectThrows("returning an item that is not on loan", IllegalArgumentException.class,
                () -> lib.giveBack("B1"));

        lib.borrow("M2", "B1"); // can be borrowed again by someone else
        check("item can be borrowed again after return", !lib.getItem("B1").isAvailable());
    }

    static void queryTests() throws Exception {
        Library lib = sample();
        LocalDate today = LocalDate.of(2026, 10, 18);
        lib.borrow("M1", "B1", today.minusDays(40)); // due 19 days ago
        lib.borrow("M2", "B2", today.minusDays(2)); // not yet due

        List<Loan> overdue = lib.overdueLoans(today);
        check("exactly one overdue loan", overdue.size() == 1);
        check("it is the old one", overdue.get(0).getItem().getId().equals("B1"));
        check("days overdue = 19", overdue.get(0).daysOverdue(today) == 19);

        lib.giveBack("B1", today);
        check("returned loan is no longer overdue", lib.overdueLoans(today).isEmpty());

        List<LibraryItem> found = lib.searchByTitle("JAVA");
        check("search is case-insensitive and finds 2 titles", found.size() == 2);
        check("results are sorted by title",
                found.get(0).getTitle().equals("Effective Java") && found.get(1).getTitle().equals("Head First Java"));
        check("search with no match returns empty list", lib.searchByTitle("zzz").isEmpty());
    }

    static void validationTests() {
        expectThrows("book with blank title", IllegalArgumentException.class, () -> new Book("X", "  ", "A"));
        expectThrows("book with no author", IllegalArgumentException.class, () -> new Book("X", "T", ""));
        expectThrows("DVD with 0 minutes", IllegalArgumentException.class, () -> new Dvd("X", "T", 0));
        expectThrows("member with blank name", IllegalArgumentException.class, () -> new Member("M9", ""));
        expectThrows("duplicate item id", IllegalArgumentException.class,
                () -> {
                    Library l = sample();
                    l.addItem(new Book("B1", "Dup", "A"));
                });
        expectThrows("duplicate member id", IllegalArgumentException.class,
                () -> {
                    Library l = sample();
                    l.registerMember(new Member("M1", "Dup"));
                });
        expectThrows("factory with unknown type", IllegalArgumentException.class,
                () -> ItemFactory.create("magazine", "X", "T", "A"));
        expectThrows("factory with bad DVD duration", IllegalArgumentException.class,
                () -> ItemFactory.create("dvd", "X", "T", "abc"));
        check("factory builds a Book", ItemFactory.create("book", "X", "T", "A") instanceof Book);
        check("factory builds a Dvd", ItemFactory.create("DVD", "X", "T", "90") instanceof Dvd);
    }

    static void persistenceTests() throws IOException, Exception {
        // CSV helper
        String line = CsvUtil.join("BOOK", "B9", "Intro to \"Algorithms\", 4th Ed", "Cormen");
        List<String> back = CsvUtil.split(line);
        check("CSV round-trip keeps commas and quotes", back.get(2).equals("Intro to \"Algorithms\", 4th Ed")
                && back.size() == 4);
        check("CSV keeps an empty trailing field", CsvUtil.split("a,b,").size() == 3);

        // Save -> load round trip using a temporary folder
        Library lib = sample();
        lib.addItem(new Book("B5", "Title, with comma", "Some Author"));
        LocalDate day = LocalDate.of(2026, 9, 1);
        lib.borrow("M1", "B1", day);
        lib.borrow("M1", "D1", day);
        lib.borrow("M2", "B2", day);
        lib.giveBack("B2", day.plusDays(5)); // a finished loan stays in the history

        Path dir = Files.createTempDirectory("librarytest");
        LibraryStorage.save(lib, dir);
        Library copy = LibraryStorage.load(dir);

        check("items survive a save/load", copy.getItems().size() == 6);
        check("members survive a save/load", copy.getMembers().size() == 2);
        check("loan history survives", copy.getLoans().size() == 3);
        check("title with a comma survives", copy.getItem("B5").getTitle().equals("Title, with comma"));
        check("item on loan is still unavailable after reload", !copy.getItem("B1").isAvailable());
        check("returned item is available after reload", copy.getItem("B2").isAvailable());
        check("member loan count rebuilt from file", copy.getMember("M1").getActiveLoanCount() == 2);
        check("due date preserved", copy.getLoans().get(0).getDueDate().equals(day.plusDays(21)));
        check("DVD subclass restored with its duration",
                copy.getItem("D1") instanceof Dvd d && d.getDurationMinutes() == 120);

        // missing files = empty library; corrupt file = clear error
        Path empty = Files.createTempDirectory("librarytest-empty");
        check("loading from an empty folder gives an empty library", LibraryStorage.load(empty).getItems().isEmpty());

        Path bad = Files.createTempDirectory("librarytest-bad");
        Files.write(bad.resolve("items.csv"), List.of("BOOK,B1,Only three fields"));
        expectThrows("corrupt CSV line is reported", IOException.class, () -> LibraryStorage.load(bad));
    }
}
