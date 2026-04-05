public class ReferenceRange {
    private double minMale;
    private double maxMale;
    private double minFemale;
    private double maxFemale;

    public ReferenceRange(double minMale, double maxMale, double minFemale, double maxFemale) {
        this.minMale = minMale;
        this.maxMale = maxMale;
        this.minFemale = minFemale;
        this.maxFemale = maxFemale;
    }

    public double getMin(char sex) {
        return sex == 'M' ? minMale : minFemale;
    }

    public double getMax(char sex) {
        return sex == 'M' ? maxMale : maxFemale;
    }

    public String getRangeString(char sex) {
        return getMin(sex) + " - " + getMax(sex);
    }
}