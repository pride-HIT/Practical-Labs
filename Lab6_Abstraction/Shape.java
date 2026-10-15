public abstract class Shape {
    public abstract double area();
    public abstract double perimeter();

    // concrete method shared by all shapes
    public void describe() {
        System.out.printf("%-10s area=%.2f perimeter=%.2f%n", getClass().getSimpleName(), area(), perimeter());
    }
}
