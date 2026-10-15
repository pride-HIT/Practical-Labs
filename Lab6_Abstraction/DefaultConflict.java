
interface Alpha {
    default void hello() { System.out.println("Hello from Alpha"); }
}

interface Beta {
    default void hello() { System.out.println("Hello from Beta"); }
}

public class DefaultConflict implements Alpha, Beta {

    @Override
    public void hello() {           
        Alpha.super.hello();         
        Beta.super.hello();          
        System.out.println("Hello from DefaultConflict (resolved the conflict)");
    }
}
