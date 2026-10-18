public class Book extends LibraryItem {

    private static final int LOAN_PERIOD_DAYS = 21;
    private final String author;

    public Book(String id, String title, String author) {
        super(id, title);
        if (author == null || author.isBlank())
            throw new IllegalArgumentException("Author cannot be blank");
        this.author = author.trim();
    }

    public String getAuthor() {
        return author;
    }

    @Override
    public int loanPeriodDays() {
        return LOAN_PERIOD_DAYS;
    }

    @Override
    public String typeLabel() {
        return "BOOK";
    }

    @Override
    public String details() {
        return "by " + author;
    }

    @Override
    public String csvExtra() {
        return author;
    }
}
