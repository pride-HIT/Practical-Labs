public record Learner(String name, int roll, double gpa) {

    public String toCsv() { return name + "," + roll + "," + gpa; }

    public static Learner fromCsv(String line) {
        String[] p = line.split(",");
        return new Learner(p[0].trim(), Integer.parseInt(p[1].trim()), Double.parseDouble(p[2].trim()));
    }
}
