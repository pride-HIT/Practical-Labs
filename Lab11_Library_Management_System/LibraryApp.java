import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class LibraryApp {

    private static final Path DATA_DIR = Path.of("data");
    private static final Scanner IN = new Scanner(System.in);
    private static boolean inputEnded = false;

    public static void main(String[] args) {
        Library library;
        try {
            library = LibraryStorage.load(DATA_DIR);
        } catch (IOException e) {
            System.out.println("Could not load saved data: " + e.getMessage());
            System.out.println("Fix or delete the files in the data folder and try again. Nothing was changed.");
            return;
        }
        System.out.printf("Library loaded: %d items, %d members, %d loans.%n",
                library.getItems().size(), library.getMembers().size(), library.getLoans().size());

        while (true) {
            printMenu();
            String choice = prompt("Choose an option: ");
            if (inputEnded || choice.equals("0"))
                break;

            try {
                switch (choice) {
                    case "1" -> addItem(library);
                    case "2" -> registerMember(library);
                    case "3" -> borrow(library);
                    case "4" -> giveBack(library);
                    case "5" -> listOverdue(library);
                    case "6" -> search(library);
                    case "7" -> library.getItems().forEach(System.out::println);
                    case "8" -> library.getMembers().forEach(System.out::println);
                    default -> System.out.println("Please choose a number from the menu.");
                }
            } catch (ItemNotAvailableException | MemberLimitExceededException e) {
                System.out.println("Cannot borrow: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        try {
            LibraryStorage.save(library, DATA_DIR);
            System.out.println("Data saved. Goodbye!");
        } catch (IOException e) {
            System.out.println("WARNING: could not save data: " + e.getMessage());
        }
    }

    // ---------- menu actions ----------
    private static void addItem(Library library) {
        String type = prompt("Type (book/dvd): ");
        if (!type.equalsIgnoreCase("book") && !type.equalsIgnoreCase("dvd")) {
            throw new IllegalArgumentException("Type must be book or dvd");
        }
        String id = prompt("Item id: ");
        String title = prompt("Title: ");
        String extra = type.equalsIgnoreCase("dvd") ? prompt("Duration in minutes: ") : prompt("Author: ");

        LibraryItem item = ItemFactory.create(type, id, title, extra); // Factory decides Book or Dvd
        library.addItem(item);
        System.out.println("Added: " + item);
    }

    private static void registerMember(Library library) {
        String id = prompt("Member id: ");
        String name = prompt("Full name: ");
        Member member = new Member(id, name);
        library.registerMember(member);
        System.out.println("Registered: " + member);
    }

    private static void borrow(Library library) throws ItemNotAvailableException, MemberLimitExceededException {
        String memberId = prompt("Member id: ");
        String itemId = prompt("Item id: ");
        Loan loan = library.borrow(memberId, itemId);
        System.out.printf("%s borrowed \"%s\". Due back on %s (%d days).%n",
                loan.getMember().getName(), loan.getItem().getTitle(),
                loan.getDueDate(), loan.getItem().loanPeriodDays());
    }

    private static void giveBack(Library library) {
        String itemId = prompt("Item id to return: ");
        Loan loan = library.giveBack(itemId);
        System.out.printf("\"%s\" returned by %s.%n", loan.getItem().getTitle(), loan.getMember().getName());
        long late = loan.daysOverdue(loan.getReturnDate());
        if (late > 0) {
            System.out.println("It was " + late + " day(s) late.");
        }
    }

    private static void listOverdue(Library library) {
        LocalDate today = LocalDate.now();
        List<Loan> overdue = library.overdueLoans(today);
        if (overdue.isEmpty()) {
            System.out.println("No overdue items.");
            return;
        }
        System.out.println("Overdue items as of " + today + ":");
        for (Loan l : overdue) {
            System.out.printf("  [%s] %s - %s | due %s | %d day(s) overdue | borrowed by %s%n",
                    l.getItem().typeLabel(), l.getItem().getId(), l.getItem().getTitle(),
                    l.getDueDate(), l.daysOverdue(today), l.getMember().getName());
        }
    }

    private static void search(Library library) {
        String text = prompt("Search title for: ");
        List<LibraryItem> results = library.searchByTitle(text);
        if (results.isEmpty()) {
            System.out.println("No items match \"" + text + "\".");
        } else {
            results.forEach(System.out::println);
        }
    }

    // ---------- helpers ----------
    private static void printMenu() {
        System.out.println("""

                ===== Library Management System =====
                1. Add item          5. List overdue items
                2. Register member   6. Search by title
                3. Borrow item       7. List all items
                4. Return item       8. List members
                0. Save and exit""");
    }

    private static String prompt(String message) {
        System.out.print(message);
        if (!IN.hasNextLine()) {
            inputEnded = true;
            return "";
        }
        return IN.nextLine().trim();
    }
}
