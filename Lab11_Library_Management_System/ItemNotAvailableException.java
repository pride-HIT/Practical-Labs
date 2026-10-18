public class ItemNotAvailableException extends Exception {

    private final String itemId;

    public ItemNotAvailableException(String itemId, String title) {
        super("Item " + itemId + " (" + title + ") is not available: it is already on loan");
        this.itemId = itemId;
    }

    public String getItemId() {
        return itemId;
    }
}
