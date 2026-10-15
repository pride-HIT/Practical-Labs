import java.util.ArrayList;
import java.util.List;


public class GoodStack {
    private final List<Integer> items = new ArrayList<>();   // hidden implementation detail

    public void push(int x)      { items.add(x); }
    public int pop() {
        if (items.isEmpty()) throw new IllegalStateException("Stack is empty");
        return items.remove(items.size() - 1);
    }
    public int peek() {
        if (items.isEmpty()) throw new IllegalStateException("Stack is empty");
        return items.get(items.size() - 1);
    }
    public boolean isEmpty() { return items.isEmpty(); }
    public int size()        { return items.size(); }

    @Override
    public String toString() { return items.toString(); }
}
