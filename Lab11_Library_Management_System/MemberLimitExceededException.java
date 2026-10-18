public class MemberLimitExceededException extends Exception {

    private final int limit;

    public MemberLimitExceededException(String memberName, int limit) {
        super(memberName + " has reached the limit of " + limit + " loans");
        this.limit = limit;
    }

    public int getLimit() {
        return limit;
    }
}
