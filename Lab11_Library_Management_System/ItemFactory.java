
public final class ItemFactory {

    private ItemFactory() {
    }

    public static LibraryItem create(String type, String id, String title, String extra) {
        if (type == null)
            throw new IllegalArgumentException("Item type is missing");
        return switch (type.trim().toUpperCase()) {
            case "BOOK" -> new Book(id, title, extra);
            case "DVD" -> new Dvd(id, title, parseMinutes(extra));
            default -> throw new IllegalArgumentException("Unknown item type: " + type);
        };
    }

    private static int parseMinutes(String text) {
        try {
            return Integer.parseInt(text.trim());
        } catch (NumberFormatException | NullPointerException e) {
            throw new IllegalArgumentException("Duration must be a whole number of minutes: " + text);
        }
    }
}
