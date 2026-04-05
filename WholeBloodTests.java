public class WholeBloodTests {

    public static class IonizedCalcium extends Test {
        public IonizedCalcium() {
            super("Ionized Calcium", 800, new ReferenceRange(4.4,5.2,4.4,5.2));
        }

        public String info() {
            return "LOW: Hypocalcemia.\nNORMAL: Healthy.\nHIGH: Hypercalcemia.";
        }
    }
}