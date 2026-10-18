public class Dvd extends LibraryItem {

    private static final int LOAN_PERIOD_DAYS = 7; // DVDs are lent for a shorter time
    private final int durationMinutes;

    public Dvd(String id, String title, int durationMinutes) {
        super(id, title);
        if (durationMinutes <= 0)
            throw new IllegalArgumentException("Duration must be positive");
        this.durationMinutes = durationMinutes;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    @Override
    public int loanPeriodDays() {
        return LOAN_PERIOD_DAYS;
    }

    @Override
    public String typeLabel() {
        return "DVD";
    }

    @Override
    public String details() {
        return durationMinutes + " min";
    }

    @Override
    public String csvExtra() {
        return String.valueOf(durationMinutes);
    }
}
