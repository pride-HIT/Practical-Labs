public interface Payment {
    double amount();

    // Used by the polymorphic design 
    String process();
}
