import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Loan {

    private final Member member;
    private final LibraryItem item;
    private final LocalDate borrowDate;
    private final LocalDate dueDate;
    private LocalDate returnDate; // null = still on loan

    public Loan(Member member, LibraryItem item, LocalDate borrowDate) {
        this(member, item, borrowDate, borrowDate.plusDays(item.loanPeriodDays()), null);
    }

    // Used when reloading a saved loan
    public Loan(Member member, LibraryItem item, LocalDate borrowDate, LocalDate dueDate, LocalDate returnDate) {
        if (member == null || item == null || borrowDate == null || dueDate == null) {
            throw new IllegalArgumentException("Loan needs a member, an item and dates");
        }
        if (dueDate.isBefore(borrowDate)) {
            throw new IllegalArgumentException("Due date cannot be before the borrow date");
        }
        this.member = member;
        this.item = item;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
    }

    public Member getMember() {
        return member;
    }

    public LibraryItem getItem() {
        return item;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public boolean isActive() {
        return returnDate == null;
    }

    void markReturned(LocalDate date) {
        this.returnDate = date;
    }

    /** Overdue = still on loan and the due date has passed. */
    public boolean isOverdue(LocalDate today) {
        return isActive() && today.isAfter(dueDate);
    }

    /** Days past the due date as of the given day (0 if not late). */
    public long daysOverdue(LocalDate asOf) {
        return Math.max(0, ChronoUnit.DAYS.between(dueDate, asOf));
    }

    @Override
    public String toString() {
        return String.format("Loan[%s -> %s, borrowed %s, due %s, %s]",
                item.getId(), member.getId(), borrowDate, dueDate,
                isActive() ? "active" : "returned " + returnDate);
    }
}
