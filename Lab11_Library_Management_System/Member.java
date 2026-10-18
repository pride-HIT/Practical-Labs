import java.util.ArrayList;
import java.util.List;

public class Member {

    public static final int MAX_LOANS = 3;

    private final String id;
    private final String name;
    private final List<Loan> activeLoans = new ArrayList<>();

    public Member(String id, String name) {
        if (id == null || id.isBlank())
            throw new IllegalArgumentException("Member id cannot be blank");
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Member name cannot be blank");
        this.id = id.trim();
        this.name = name.trim();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean hasReachedLimit() {
        return activeLoans.size() >= MAX_LOANS;
    }

    public int getActiveLoanCount() {
        return activeLoans.size();
    }

    // Callers get a read-only copy, so they cannot bypass the loan limit
    public List<Loan> getActiveLoans() {
        return List.copyOf(activeLoans);
    }

    void addLoan(Loan loan) throws MemberLimitExceededException {
        if (hasReachedLimit()) {
            throw new MemberLimitExceededException(name, MAX_LOANS);
        }
        activeLoans.add(loan);
    }

    void removeLoan(Loan loan) {
        activeLoans.remove(loan);
    }

    @Override
    public String toString() {
        return String.format("%s - %s (%d/%d loans)", id, name, activeLoans.size(), MAX_LOANS);
    }
}
