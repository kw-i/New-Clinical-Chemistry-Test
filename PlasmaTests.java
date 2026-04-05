public class PlasmaTests {

    public static class Sodium extends Test {
        public Sodium() {
            super("Sodium", 300, new ReferenceRange(135,145,135,145));
        }

        public String info() {
            return "LOW: Hyponatremia.\nNORMAL: Healthy.\nHIGH: Dehydration.";
        }
    }

    public static class Potassium extends Test {
        public Potassium() {
            super("Potassium", 300, new ReferenceRange(3.5,5.0,3.5,5.0));
        }

        public String info() {
            return "LOW: Weakness.\nNORMAL: Healthy.\nHIGH: Cardiac risk.";
        }
    }

    public static class Chloride extends Test {
        public Chloride() {
            super("Chloride", 300, new ReferenceRange(96,110,96,110));
        }

        public String info() {
            return "LOW: Alkalosis.\nNORMAL: Healthy.\nHIGH: Dehydration.";
        }
    }
}