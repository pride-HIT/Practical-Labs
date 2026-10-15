public class ArrayAccess {

    // Returns the element, or -1 when the index is invalid
    public static int safeGet(int[] arr, int index) {
        try {
            return arr[index];
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Invalid index " + index + ": " + e.getMessage());
            return -1;
        }
    }
}
