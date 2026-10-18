public interface Borrowable {

    /** Marks the object as on loan to the given member. */
    void borrow(Member member) throws ItemNotAvailableException;

    /** Marks the object as available again. */
    void giveBack();
}
