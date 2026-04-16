public class StoolTests {

    public static class StoolPH extends Test {
        public StoolPH() { super("Stool pH", 200, new ReferenceRange(6.5, 7.5, 6.5, 7.5)); }

        public String info() {
            return "LOW: Possible carbohydrate malabsorption.\nNORMAL: Healthy digestion.\nHIGH: Possible bacterial imbalance.";
        }
    }

    public static class OccultBlood extends Test {
        public OccultBlood() { super("Occult Blood", 250, new ReferenceRange(0, 0, 0, 0)); }

        public String info() {
            return "NORMAL: No blood detected.\nHIGH: Possible gastrointestinal bleeding.";
        }
    }

    public static class StoolFat extends Test {
        public StoolFat() { super("Stool Fat", 270, new ReferenceRange(2, 7, 2, 7)); }

        public String info() {
            return "LOW: Normal fat absorption.\nNORMAL: Healthy.\nHIGH: Possible malabsorption.";
        }
    }

    public static class StoolWBC extends Test {
        public StoolWBC() { super("Stool WBC", 260, new ReferenceRange(0, 5, 0, 5)); }

        public String info() {
            return "LOW: No inflammation.\nNORMAL: Healthy.\nHIGH: Infection or inflammation.";
        }
    }

    public static class ParasiteTest extends Test {
        public ParasiteTest() { super("Parasite Test", 300, new ReferenceRange(0, 0, 0, 0)); }

        public String info() {
            return "NORMAL: No parasites.\nHIGH: Parasitic infection present.";
        }
    }
}