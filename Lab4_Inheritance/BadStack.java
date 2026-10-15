import java.util.ArrayList;


public class BadStack extends ArrayList<Integer> {
    public void push(int x) { add(x); }
    public int pop()        { return remove(size() - 1); }
    public int peek()       { return get(size() - 1); }
}
