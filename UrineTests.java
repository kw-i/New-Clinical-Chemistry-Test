public class UrineTests {

    public static class Urinalysis extends Test {
        public Urinalysis() {
            super("Urinalysis", 200, new ReferenceRange(4.5,8.0,4.5,8.0));
        }

        public String info() {
            return "LOW: Acidic urine (possible metabolic issues).\n"
                 + "NORMAL: Healthy urine pH.\n"
                 + "HIGH: Alkaline urine (possible infection).";
        }
    }

    public static class UrineProtein extends Test {
        public UrineProtein() {
            super("Urine Protein", 250, new ReferenceRange(0,8,0,8));
        }

        public String info() {
            return "LOW: Normal.\n"
                 + "NORMAL: No protein detected.\n"
                 + "HIGH: Possible kidney disease.";
        }
    }

    public static class UrineGlucose extends Test {
        public UrineGlucose() {
            super("Urine Glucose", 250, new ReferenceRange(0,15,0,15));
        }

        public String info() {
            return "LOW: Normal.\n"
                 + "NORMAL: No glucose detected.\n"
                 + "HIGH: Possible diabetes.";
        }
    }

    public static class UrineKetones extends Test {
        public UrineKetones() {
            super("Urine Ketones", 260, new ReferenceRange(0,5,0,5));
        }

        public String info() {
            return "LOW: Normal.\n"
                 + "NORMAL: No ketones detected.\n"
                 + "HIGH: Possible ketoacidosis or fasting.";
        }
    }

    public static class UrineSpecificGravity extends Test {
        public UrineSpecificGravity() {
            super("Urine Specific Gravity", 220, new ReferenceRange(1.005,1.030,1.005,1.030));
        }

        public String info() {
            return "LOW: Dilute urine (overhydration).\n"
                 + "NORMAL: Proper kidney function.\n"
                 + "HIGH: Concentrated urine (dehydration).";
        }
    }

    public static class UrinepH extends Test {
        public UrinepH() {
            super("Urine pH", 200, new ReferenceRange(4.5,8.0,4.5,8.0));
        }

        public String info() {
            return "LOW: Acidic urine.\n"
                 + "NORMAL: Balanced.\n"
                 + "HIGH: Alkaline urine (possible infection).";
        }
    }
}