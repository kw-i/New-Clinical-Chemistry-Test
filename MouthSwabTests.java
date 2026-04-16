public class MouthSwabTests {

    public static class OralBacteria extends Test {
        public OralBacteria() { super("Oral Bacteria", 220, new ReferenceRange(0, 1, 0, 1)); }

        public String info() {
            return "LOW: Minimal bacteria.\nNORMAL: Balanced oral flora.\nHIGH: Possible bacterial infection.";
        }
    }

    public static class CandidaTest extends Test {
        public CandidaTest() { super("Candida", 250, new ReferenceRange(0, 1, 0, 1)); }

        public String info() {
            return "NORMAL: No fungal growth.\nHIGH: Possible oral thrush.";
        }
    }

    public static class StrepTest extends Test {
        public StrepTest() { super("Strep Test", 280, new ReferenceRange(0, 1, 0, 1)); }

        public String info() {
            return "NORMAL: No streptococcus detected.\nHIGH: Possible strep infection.";
        }
    }

    public static class ViralLoad extends Test {
        public ViralLoad() { super("Oral Viral Load", 300, new ReferenceRange(0, 1, 0, 1)); }

        public String info() {
            return "LOW: No viral presence.\nNORMAL: Healthy.\nHIGH: Possible viral infection.";
        }
    }

    public static class PHLevel extends Test {
        public PHLevel() { super("Oral pH", 200, new ReferenceRange(6.5, 7.5, 6.5, 7.5)); }

        public String info() {
            return "LOW: Acidic environment.\nNORMAL: Healthy oral balance.\nHIGH: Alkaline imbalance.";
        }
    }
}