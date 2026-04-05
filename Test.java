public abstract class Test {
    protected String name;
    protected double price;
    protected ReferenceRange range;

    public Test(String name, double price, ReferenceRange range) {
        this.name = name;
        this.price = price;
        this.range = range;
    }

    public String evaluate(double value, char sex) {
        if (value < range.getMin(sex)) return "LOW";
        if (value > range.getMax(sex)) return "HIGH";
        return "NORMAL";
    }

    public abstract String info();

    public String getName() {
        return name;
    }

    public ReferenceRange getRange() {
        return range;
    }
}    