import javax.swing.*;
import java.awt.*;
import java.util.*;

// main class
public class LabGUI {

    // main frame
    private JFrame frame;

    // selected tests list
    private DefaultListModel<String> selectedTestsModel;
    private JList<String> selectedTestsList;

    // button
    private JButton takeTestButton;

    // current test object
    private Test currentTest;

    // patient info
    private String patientName;
    private char patientSex;
    private int patientAge;

    // colors
    private final Color BG_COLOR = new Color(255, 248, 180);
    private final Color BUTTON_COLOR = new Color(255, 204, 0);
    private final Color TEXT_COLOR = new Color(102, 75, 0);
    private final Color FIELD_COLOR = new Color(255, 255, 210);

    // test arrays
    private final String[] bloodTests = {
            "FBS","RBS","Total Cholesterol","HDL","LDL","Triglycerides",
            "Creatinine","Uric Acid","BUN","AST","ALT","Albumin",
            "Total Protein","ALP","Total Calcium",
            "Sodium","Potassium","Chloride","Ionized Calcium"
    };

    private final String[] urineTests = {
            "Urinalysis","Urine Protein","Urine Glucose",
            "Urine Ketones","Urine Specific Gravity","Urine pH"
    };

    private final String[] stoolTests = {
            "Stool pH","Occult Blood","Stool Fat","Stool WBC","Parasite Test"
    };

    private final String[] mouthTests = {
            "Oral Bacteria","Candida","Strep Test","Oral Viral Load","Oral pH"
    };

    // constructor
    public LabGUI() {

        // get patient info
        getPatientInfo();

        // create frame
        frame = new JFrame("Clinical Chemistry Lab Simulator");
        frame.setSize(900, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BG_COLOR);

        // patient info panel
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        infoPanel.setBackground(BG_COLOR);
        infoPanel.add(new JLabel("Name: " + patientName));
        infoPanel.add(new JLabel("Age: " + patientAge));
        infoPanel.add(new JLabel("Sex: " + patientSex));
        mainPanel.add(infoPanel, BorderLayout.NORTH);

        // tabbed panel
        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Blood", createBloodPanel());
        tabs.addTab("Urine", createUrinePanel());
        tabs.addTab("Stool", createStoolPanel());
        tabs.addTab("Mouth", createMouthPanel());

        mainPanel.add(tabs, BorderLayout.CENTER);

        // right panel
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(BG_COLOR);

        // selected tests list
        selectedTestsModel = new DefaultListModel<>();
        selectedTestsList = new JList<>(selectedTestsModel);
        selectedTestsList.setBackground(FIELD_COLOR);

        rightPanel.add(new JLabel("Selected Tests:"), BorderLayout.NORTH);
        rightPanel.add(new JScrollPane(selectedTestsList), BorderLayout.CENTER);

        // take test button
        takeTestButton = new JButton("Take Test");
        styleButton(takeTestButton);
        takeTestButton.addActionListener(e -> simulateSelectedTests());

        rightPanel.add(takeTestButton, BorderLayout.SOUTH);

        mainPanel.add(rightPanel, BorderLayout.EAST);

        // add panel to frame
        frame.add(mainPanel);
        frame.setVisible(true);
    }

    // create test panel
    private JPanel createTestPanel(String[] tests, Color shade) {

        // panel
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(shade);

        // test list
        JList<String> list = new JList<>(tests);
        list.setBackground(FIELD_COLOR);
        list.setBorder(BorderFactory.createTitledBorder("Available Tests"));

        // add button
        JButton addBtn = new JButton("Add Test");
        styleButton(addBtn);

        // add button action
        addBtn.addActionListener(e -> {
            String selected = list.getSelectedValue();
            if (selected != null) {

                // add label prefix
                String prefix = "";
                if(Arrays.asList(bloodTests).contains(selected)) prefix = "[BLOOD] ";
                else if(Arrays.asList(urineTests).contains(selected)) prefix = "[URINE] ";
                else if(Arrays.asList(stoolTests).contains(selected)) prefix = "[STOOL] ";
                else prefix = "[MOUTH] ";

                String labeled = prefix + selected;

                // add to selected list
                if (!selectedTestsModel.contains(labeled)) {
                    selectedTestsModel.addElement(labeled);
                }
            }
        });

        // bottom panel
        JPanel bottom = new JPanel();
        bottom.setBackground(shade);
        bottom.add(addBtn);

        panel.add(new JScrollPane(list), BorderLayout.CENTER);
        panel.add(bottom, BorderLayout.SOUTH);

        return panel;
    }

    // blood panel
    private JPanel createBloodPanel() {
        JPanel p = createTestPanel(bloodTests, new Color(255, 220, 140));
        p.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
        return p;
    }

    // urine panel
    private JPanel createUrinePanel() {
        JPanel p = createTestPanel(urineTests, new Color(230, 255, 210));
        p.setBorder(BorderFactory.createDashedBorder(Color.BLUE));
        return p;
    }

    // stool panel
    private JPanel createStoolPanel() {
        JPanel p = createTestPanel(stoolTests, new Color(210, 235, 170));
        p.setBorder(BorderFactory.createLineBorder(new Color(120, 80, 40), 2));
        return p;
    }

    // mouth panel
    private JPanel createMouthPanel() {
        JPanel p = createTestPanel(mouthTests, new Color(255, 210, 150));
        p.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 2));
        return p;
    }

    // simulate tests
    private void simulateSelectedTests() {

        // check if empty
        if(selectedTestsModel.isEmpty()){
            JOptionPane.showMessageDialog(frame,"No tests selected!");
            return;
        }

        // random generator
        Random rand = new Random();

        // text areas
        JTextArea bloodArea = new JTextArea();
        JTextArea urineArea = new JTextArea();
        JTextArea stoolArea = new JTextArea();
        JTextArea mouthArea = new JTextArea();

        bloodArea.setEditable(false);
        urineArea.setEditable(false);
        stoolArea.setEditable(false);
        mouthArea.setEditable(false);

        // result builders
        StringBuilder blood = new StringBuilder();
        StringBuilder urine = new StringBuilder();
        StringBuilder stool = new StringBuilder();
        StringBuilder mouth = new StringBuilder();

        double total = 0;

        // loop selected tests
        for(int i=0;i<selectedTestsModel.size();i++){

            String labeled = selectedTestsModel.get(i);

            // remove label
            String name = labeled.replace("[BLOOD] ", "")
                                 .replace("[URINE] ", "")
                                 .replace("[STOOL] ", "")
                                 .replace("[MOUTH] ", "");

            // select test class
            selectTest(name);

            // add price
            total += currentTest.getPrice();

            // random value
            double value = getRandomValue(name, rand);

            // evaluate result
            String result = currentTest.evaluate(value, patientSex);

            // result block
            String block =
                    "\nTEST: " + name +
                    "\nVALUE: " + String.format("%.2f", value) +
                    "\nRESULT: " + result +
                    "\n" + currentTest.info() +
                    "\n----------------------------------------\n";

            // assign to category
            if(labeled.contains("[BLOOD]")) blood.append(block);
            else if(labeled.contains("[URINE]")) urine.append(block);
            else if(labeled.contains("[STOOL]")) stool.append(block);
            else mouth.append(block);
        }

        // payment confirm
        int confirm = JOptionPane.showConfirmDialog(frame,
                "Total Price: $" + total + "\nProceed?",
                "Payment", JOptionPane.OK_CANCEL_OPTION);

        if(confirm != JOptionPane.OK_OPTION) return;

        // receipt frame
        JFrame receiptFrame = new JFrame("Official Receipt");

        JTextArea receiptArea = new JTextArea();
        receiptArea.setEditable(false);

        StringBuilder receipt = new StringBuilder();

        // receipt content
        receipt.append("===== RECEIPT =====\n");
        receipt.append("Patient: ").append(patientName).append("\n");
        receipt.append("Age: ").append(patientAge).append("\n");
        receipt.append("Sex: ").append(patientSex).append("\n");
        receipt.append("----------------------------------------\n");

        // list tests
        for(int i = 0; i < selectedTestsModel.size(); i++){

            String labeled = selectedTestsModel.get(i);

            String name = labeled.replace("[BLOOD] ", "")
                                 .replace("[URINE] ", "")
                                 .replace("[STOOL] ", "")
                                 .replace("[MOUTH] ", "");

            selectTest(name);

            receipt.append(name)
                    .append(" - $")
                    .append(currentTest.getPrice())
                    .append("\n");
        }

        receipt.append("----------------------------------------\n");
        receipt.append("TOTAL: $").append(total).append("\n");
        receipt.append("Thank you!\n");

        receiptArea.setText(receipt.toString());

        receiptFrame.add(new JScrollPane(receiptArea));
        receiptFrame.setSize(350, 400);
        receiptFrame.setVisible(true);

        // clear selected list
        selectedTestsModel.clear();
    }

    // button style
    private void styleButton(JButton b){
        b.setBackground(BUTTON_COLOR);
        b.setForeground(TEXT_COLOR);
    }

    // get patient info dialog
    private void getPatientInfo() {

        JTextField nameField = new JTextField();
        JTextField ageField = new JTextField();
        JComboBox<String> sexBox = new JComboBox<>(new String[]{"Male","Female"});

        JPanel panel = new JPanel(new GridLayout(3,2));
        panel.add(new JLabel("Name:")); panel.add(nameField);
        panel.add(new JLabel("Sex:")); panel.add(sexBox);
        panel.add(new JLabel("Age:")); panel.add(ageField);

        int result;

        // loop until valid input
        do {
            result = JOptionPane.showConfirmDialog(null, panel, "Patient Info",
                    JOptionPane.OK_CANCEL_OPTION);

            if(result != JOptionPane.OK_OPTION) System.exit(0);

        } while(nameField.getText().isEmpty() || ageField.getText().isEmpty());

        patientName = nameField.getText();
        patientSex = sexBox.getSelectedItem().equals("Male") ? 'M' : 'F';

        try { patientAge = Integer.parseInt(ageField.getText()); }
        catch(Exception e) { patientAge = 0; }
    }


    private double getRandomValue(String test, Random rand) {
        switch(test) {

            // BLOOD CHEMISTRY
            case "FBS": return 60 + rand.nextDouble() * 140; // 60–200
            case "RBS": return 70 + rand.nextDouble() * 180;
            case "Total Cholesterol": return 120 + rand.nextDouble() * 200;
            case "HDL": return 30 + rand.nextDouble() * 70;
            case "LDL": return 50 + rand.nextDouble() * 160;
            case "Triglycerides": return 50 + rand.nextDouble() * 250;
            case "Creatinine": return 0.5 + rand.nextDouble() * 2.0;
            case "Uric Acid": return 2.0 + rand.nextDouble() * 8.0;
            case "BUN": return 7 + rand.nextDouble() * 25;
            case "AST": return 10 + rand.nextDouble() * 60;
            case "ALT": return 10 + rand.nextDouble() * 70;
            case "Albumin": return 3.0 + rand.nextDouble() * 2.0;
            case "Total Protein": return 6.0 + rand.nextDouble() * 3.0;
            case "ALP": return 40 + rand.nextDouble() * 200;
            case "Total Calcium": return 8.0 + rand.nextDouble() * 3.0;

            case "Sodium": return 135 + rand.nextDouble() * 10;
            case "Potassium": return 3.5 + rand.nextDouble() * 2.0;
            case "Chloride": return 98 + rand.nextDouble() * 10;
            case "Ionized Calcium": return 1.0 + rand.nextDouble() * 0.5;

            // URINE
            case "Urinalysis": return rand.nextDouble() * 10;
            case "Urine Protein": return rand.nextDouble() * 30;
            case "Urine Glucose": return rand.nextDouble() * 20;
            case "Urine Ketones": return rand.nextDouble() * 15;
            case "Urine Specific Gravity": return 1.005 + rand.nextDouble() * 0.020;
            case "Urine pH": return 4.5 + rand.nextDouble() * 4.0;

            // STOOL
            case "Stool pH": return 5.5 + rand.nextDouble() * 3.0;
            case "Occult Blood": return rand.nextDouble() * 5;
            case "Stool Fat": return rand.nextDouble() * 20;
            case "Stool WBC": return rand.nextDouble() * 50;
            case "Parasite Test": return rand.nextDouble() * 3;

            // MOUTH
            case "Oral Bacteria": return rand.nextDouble() * 100;
            case "Candida": return rand.nextDouble() * 50;
            case "Strep Test": return rand.nextDouble() * 30;
            case "Oral Viral Load": return rand.nextDouble() * 200;
            case "Oral pH": return 6.0 + rand.nextDouble() * 2.0;

            default: return rand.nextDouble() * 100;
        }
    }

    private void selectTest(String selected){
        switch(selected){
            case "FBS": currentTest = new SerumTests.FBS(); break;
            case "RBS": currentTest = new SerumTests.RBS(); break;
            case "Total Cholesterol": currentTest = new SerumTests.TotalCholesterol(); break;
            case "HDL": currentTest = new SerumTests.HDL(); break;
            case "LDL": currentTest = new SerumTests.LDL(); break;
            case "Triglycerides": currentTest = new SerumTests.Triglycerides(); break;
            case "Creatinine": currentTest = new SerumTests.Creatinine(); break;
            case "Uric Acid": currentTest = new SerumTests.UricAcid(); break;
            case "BUN": currentTest = new SerumTests.BUN(); break;
            case "AST": currentTest = new SerumTests.AST(); break;
            case "ALT": currentTest = new SerumTests.ALT(); break;
            case "Albumin": currentTest = new SerumTests.Albumin(); break;
            case "Total Protein": currentTest = new SerumTests.TotalProtein(); break;
            case "ALP": currentTest = new SerumTests.ALP(); break;
            case "Total Calcium": currentTest = new SerumTests.TotalCalcium(); break;

            case "Sodium": currentTest = new PlasmaTests.Sodium(); break;
            case "Potassium": currentTest = new PlasmaTests.Potassium(); break;
            case "Chloride": currentTest = new PlasmaTests.Chloride(); break;
            case "Ionized Calcium": currentTest = new WholeBloodTests.IonizedCalcium(); break;

            case "Urinalysis": currentTest = new UrineTests.Urinalysis(); break;
            case "Urine Protein": currentTest = new UrineTests.UrineProtein(); break;
            case "Urine Glucose": currentTest = new UrineTests.UrineGlucose(); break;
            case "Urine Ketones": currentTest = new UrineTests.UrineKetones(); break;
            case "Urine Specific Gravity": currentTest = new UrineTests.UrineSpecificGravity(); break;
            case "Urine pH": currentTest = new UrineTests.UrinepH(); break;

            case "Stool pH": currentTest = new StoolTests.StoolPH(); break;
            case "Occult Blood": currentTest = new StoolTests.OccultBlood(); break;
            case "Stool Fat": currentTest = new StoolTests.StoolFat(); break;
            case "Stool WBC": currentTest = new StoolTests.StoolWBC(); break;
            case "Parasite Test": currentTest = new StoolTests.ParasiteTest(); break;

            case "Oral Bacteria": currentTest = new MouthSwabTests.OralBacteria(); break;
            case "Candida": currentTest = new MouthSwabTests.CandidaTest(); break;
            case "Strep Test": currentTest = new MouthSwabTests.StrepTest(); break;
            case "Oral Viral Load": currentTest = new MouthSwabTests.ViralLoad(); break;
            case "Oral pH": currentTest = new MouthSwabTests.PHLevel(); break;
        }
    }
}