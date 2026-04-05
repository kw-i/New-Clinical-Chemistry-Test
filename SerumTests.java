public class SerumTests {

    public static class FBS extends Test {
        public FBS() { super("FBS", 250, new ReferenceRange(74,100,74,100)); }
        public String info() {
            return "LOW: Hypoglycemia.\nNORMAL: Healthy glucose.\nHIGH: Possible diabetes.";
        }
    }

    public static class RBS extends Test {
        public RBS() { super("RBS", 250, new ReferenceRange(74,140,74,140)); }
        public String info() {
            return "LOW: Hypoglycemia.\nNORMAL: Acceptable glucose.\nHIGH: Possible diabetes.";
        }
    }

    public static class TotalCholesterol extends Test {
        public TotalCholesterol() { super("Total Cholesterol", 300, new ReferenceRange(150,200,150,200)); }
        public String info() {
            return "LOW: Malnutrition.\nNORMAL: Healthy.\nHIGH: Heart disease risk.";
        }
    }

    public static class HDL extends Test {
        public HDL() { super("HDL", 300, new ReferenceRange(35,88,42,88)); }
        public String info() {
            return "LOW: Increased heart risk.\nNORMAL: Protective.\nHIGH: Generally good.";
        }
    }

    public static class LDL extends Test {
        public LDL() { super("LDL", 230, new ReferenceRange(50,130,50,130)); }
        public String info() {
            return "LOW: Usually safe.\nNORMAL: Optimal.\nHIGH: High cardiovascular risk.";
        }
    }

    public static class Triglycerides extends Test {
        public Triglycerides() { super("Triglycerides", 300, new ReferenceRange(60,165,40,140)); }
        public String info() {
            return "LOW: Possible malnutrition.\nNORMAL: Healthy.\nHIGH: Metabolic syndrome risk.";
        }
    }

    public static class Creatinine extends Test {
        public Creatinine() { super("Creatinine", 300, new ReferenceRange(0.9,1.3,0.6,1.2)); }
        public String info() {
            return "LOW: Low muscle mass.\nNORMAL: Healthy kidneys.\nHIGH: Kidney dysfunction.";
        }
    }

    public static class UricAcid extends Test {
        public UricAcid() { super("Uric Acid", 290, new ReferenceRange(3.5,7.2,2.6,6.0)); }
        public String info() {
            return "LOW: Rare condition.\nNORMAL: Healthy.\nHIGH: Risk of gout.";
        }
    }

    public static class BUN extends Test {
        public BUN() { super("BUN", 250, new ReferenceRange(6.0,20.0,6.0,20.0)); }
        public String info() {
            return "LOW: Liver issues.\nNORMAL: Healthy.\nHIGH: Kidney issues or dehydration.";
        }
    }

    public static class AST extends Test {
        public AST() { super("AST", 300, new ReferenceRange(0,46,0,46)); }
        public String info() {
            return "NORMAL: Healthy liver.\nHIGH: Liver or muscle damage.";
        }
    }

    public static class ALT extends Test {
        public ALT() { super("ALT", 310, new ReferenceRange(0,49,0,49)); }
        public String info() {
            return "NORMAL: Healthy liver.\nHIGH: Liver inflammation.";
        }
    }

    public static class Albumin extends Test {
        public Albumin() { super("Albumin", 300, new ReferenceRange(3.5,5.0,3.5,5.0)); }
        public String info() {
            return "LOW: Liver disease or malnutrition.\nNORMAL: Healthy.\nHIGH: Dehydration.";
        }
    }

    public static class TotalProtein extends Test {
        public TotalProtein() { super("Total Protein", 260, new ReferenceRange(6.0,8.3,6.0,8.3)); }
        public String info() {
            return "LOW: Malnutrition.\nNORMAL: Healthy.\nHIGH: Chronic inflammation.";
        }
    }

    public static class ALP extends Test {
        public ALP() { super("ALP", 330, new ReferenceRange(44,147,44,147)); }
        public String info() {
            return "LOW: Malnutrition.\nNORMAL: Healthy.\nHIGH: Liver or bone disease.";
        }
    }

    public static class TotalCalcium extends Test {
        public TotalCalcium() { super("Total Calcium", 300, new ReferenceRange(8.6,10.28,8.6,10.28)); }
        public String info() {
            return "LOW: Hypocalcemia.\nNORMAL: Healthy.\nHIGH: Hypercalcemia.";
        }
    }
}