import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Library {

    private final Map<String, LibraryItem> items = new LinkedHashMap<>();
    private final Map<String, Member> members = new LinkedHashMap<>();
    private final List<Loan> loans = new ArrayList<>();

    // ---------- adding things ----------
    public void addItem(LibraryItem item) {
        if (items.containsKey(item.getId())) {
            throw new IllegalArgumentException("An item with id " + item.getId() + " already exists");
        }
        items.put(item.getId(), item);
    }

    public void registerMember(Member member) {
        if (members.containsKey(member.getId())) {
            throw new IllegalArgumentException("A member with id " + member.getId() + " already exists");
        }
        members.put(member.getId(), member);
    }

    // ---------- lookups ----------
    public LibraryItem getItem(String id) {
        LibraryItem item = items.get(id == null ? "" : id.trim());
        if (item == null)
            throw new IllegalArgumentException("No item with id " + id);
        return item;
    }

    public Member getMember(String id) {
        Member member = members.get(id == null ? "" : id.trim());
        if (member == null)
            throw new IllegalArgumentException("No member with id " + id);
        return member;
    }

    public Collection<LibraryItem> getItems() {
        return List.copyOf(items.values());
    }

    public Collection<Member> getMembers() {
        return List.copyOf(members.values());
    }

    public List<Loan> getLoans() {
        return List.copyOf(loans);
    }

    // ---------- borrowing and returning ----------
    public Loan borrow(String memberId, String itemId)
            throws ItemNotAvailableException, MemberLimitExceededException {
        return borrow(memberId, itemId, LocalDate.now());
    }

    public Loan borrow(String memberId, String itemId, LocalDate date)
            throws ItemNotAvailableException, MemberLimitExceededException {
        // 1. look up member and item (IllegalArgumentException if missing)
        Member member = getMember(memberId);
        LibraryItem item = getItem(itemId);

        // 2. check every rule BEFORE changing anything, so a failure leaves no
        // half-done state
        if (!item.isAvailable()) {
            throw new ItemNotAvailableException(item.getId(), item.getTitle());
        }
        if (member.hasReachedLimit()) {
            throw new MemberLimitExceededException(member.getName(), Member.MAX_LOANS);
        }

        // 3. create the loan, mark the item unavailable, record it on the member
        Loan loan = new Loan(member, item, date);
        item.borrow(member);
        member.addLoan(loan);
        loans.add(loan);
        return loan;
    }

    public Loan giveBack(String itemId) {
        return giveBack(itemId, LocalDate.now());
    }

    public Loan giveBack(String itemId, LocalDate date) {
        LibraryItem item = getItem(itemId);
        Loan loan = loans.stream()
                .filter(l -> l.isActive() && l.getItem() == item)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Item " + item.getId() + " is not on loan"));

        loan.markReturned(date);
        item.giveBack();
        loan.getMember().removeLoan(loan);
        return loan;
    }

    // ---------- queries (streams) ----------
    public List<Loan> overdueLoans(LocalDate today) {
        return loans.stream()
                .filter(l -> l.isOverdue(today))
                .sorted(Comparator.comparing(Loan::getDueDate))
                .toList();
    }

    public List<LibraryItem> searchByTitle(String text) {
        String needle = text == null ? "" : text.trim().toLowerCase();
        return items.values().stream()
                .filter(i -> i.getTitle().toLowerCase().contains(needle))
                .sorted(Comparator.comparing(LibraryItem::getTitle, String.CASE_INSENSITIVE_ORDER))
                .toList();
    }

    // ---------- used only by LibraryStorage when reloading saved data ----------
    void restoreLoan(Loan loan) {
        loans.add(loan);
        if (loan.isActive()) {
            try {
                loan.getItem().borrow(loan.getMember());
                loan.getMember().addLoan(loan);
            } catch (ItemNotAvailableException | MemberLimitExceededException e) {
                throw new IllegalStateException("Saved data is inconsistent: " + e.getMessage(), e);
            }
        }
    }
}
