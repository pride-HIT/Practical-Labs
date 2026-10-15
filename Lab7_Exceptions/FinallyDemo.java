
public class FinallyDemo {

    static int demo() {
        try {
            System.out.println("in try");
            return 1;
        } finally {
            System.out.println("in finally");
        }
    }

    static int tricky() {
        int x = 1;
        try {
            return x;            // the value 1 is copied and saved here
        } finally {
            x = 2;               
            System.out.println("finally set x = " + x);
        }
    }

    @SuppressWarnings("finally")
    static int badPractice() {
        try {
            return 1;
        } finally {
            return 99;          
        }
    }

    public static void run() {
        System.out.println("returned value: " + demo());
        System.out.println("tricky() returned: " + tricky() + "  (still 1, not 2)");
        System.out.println("badPractice() returned: " + badPractice() + "  (finally's return wins)");
    }
}
