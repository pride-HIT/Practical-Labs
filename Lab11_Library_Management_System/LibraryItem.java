
public abstract class LibraryItem implements Borrowable {

    private final String id;
    private final String title;
    private boolean available = true;
    private Member borrowedBy;

    protected LibraryItem(String id, String title) {
        if (id == null || id.isBlank())
            throw new IllegalArgumentException("Item id cannot be blank");
        if (title == null || title.isBlank())
            throw new IllegalArgumentException("Item title cannot be blank");
        this.id = id.trim();
        this.title = title.trim();
    }

    public abstract int loanPeriodDays();

    public abstract String typeLabel();

    public abstract String details();

    public abstract String csvExtra();

    // ----- Borrowable -----
    @Override
    public void borrow(Member member) throws ItemNotAvailableException {
        if (!available) {
            throw new ItemNotAvailableException(id, title);
        }
        available = false;
        borrowedBy = member;
    }

    @Override
    public void giveBack() {
        available = true;
        borrowedBy = null;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public boolean isAvailable() {
        return available;
    }

    public Member getBorrowedBy() {
        return borrowedBy;
    }

    @Override
    public String toString() {
        String status = available ? "available" : "on loan to " + borrowedBy.getName();
        return String.format("[%s] %s - %s (%s) - %s", typeLabel(), id, title, details(), status);
    }
}
