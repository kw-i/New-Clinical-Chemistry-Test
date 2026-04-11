import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class LabGUI {
    private JFrame frame;
    private JList<String> testList;
    private DefaultListModel<String> selectedTestsModel;
    private JList<String> selectedTestsList;
    private JButton addTestButton, takeTestButton;
    private JComboBox<String> testTypeBox;

    private Test currentTest;

    private String patientName;
    private char patientSex;
    private int patientAge;

    private final Color BG_COLOR = new Color(255, 248, 180);
    private final Color PANEL_COLOR = new Color(255, 239, 140);
    private final Color BUTTON_COLOR = new Color(255, 204, 0);
    private final Color TEXT_COLOR = new Color(102, 75, 0);
    private final Color FIELD_COLOR = new Color(255, 255, 210);

    private final String[] bloodTests = {
            "FBS","RBS","Total Cholesterol","HDL","LDL","Triglycerides",
            "Creatinine","Uric Acid","BUN","AST","ALT","Albumin",
            "Total Protein","ALP","Total Calcium",
            "Sodium","Potassium","Chloride",
            "Ionized Calcium"
    };

    private final String[] urineTests = {
            "Urinalysis","Urine Protein","Urine Glucose",
            "Urine Ketones","Urine Specific Gravity","Urine pH"
    };

    public LabGUI() {
        getPatientInfo();

        frame = new JFrame("Clinical Chemistry Lab Simulator");
        frame.setSize(750, 450);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BG_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 5));
        infoPanel.setBackground(PANEL_COLOR);

        infoPanel.add(createLabel("Name: " + patientName));
        infoPanel.add(createLabel("Age: " + patientAge));
        infoPanel.add(createLabel("Sex: " + patientSex));

        testTypeBox = new JComboBox<>(new String[]{"Blood Tests", "Urine Tests"});
        testTypeBox.setBackground(FIELD_COLOR);
        testTypeBox.setForeground(TEXT_COLOR);

        infoPanel.add(createLabel("Test Type:"));
        infoPanel.add(testTypeBox);

        mainPanel.add(infoPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout(10,0));
        centerPanel.setBackground(BG_COLOR);

        JPanel availablePanel = new JPanel(new BorderLayout());
        availablePanel.setBackground(PANEL_COLOR);
        availablePanel.add(createLabel("Available Tests:"), BorderLayout.NORTH);

        testList = new JList<>(bloodTests);
        testList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        testList.setBackground(FIELD_COLOR);
        testList.setForeground(TEXT_COLOR);

        availablePanel.add(new JScrollPane(testList), BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBackground(PANEL_COLOR);

        addTestButton = new JButton("Add Test");
        takeTestButton = new JButton("Take Test (Simulated)");

        styleButton(addTestButton);
        styleButton(takeTestButton);

        buttonsPanel.add(addTestButton);
        buttonsPanel.add(takeTestButton);

        availablePanel.add(buttonsPanel, BorderLayout.SOUTH);

        centerPanel.add(availablePanel, BorderLayout.CENTER);

        JPanel selectedPanel = new JPanel(new BorderLayout());
        selectedPanel.setBackground(PANEL_COLOR);
        selectedPanel.add(createLabel("Selected Tests:"), BorderLayout.NORTH);

        selectedTestsModel = new DefaultListModel<>();
        selectedTestsList = new JList<>(selectedTestsModel);
        selectedTestsList.setBackground(FIELD_COLOR);
        selectedTestsList.setForeground(TEXT_COLOR);

        selectedPanel.add(new JScrollPane(selectedTestsList), BorderLayout.CENTER);

        centerPanel.add(selectedPanel, BorderLayout.EAST);

        mainPanel.add(centerPanel);
        frame.add(mainPanel);

        addTestButton.addActionListener(e -> addSelectedTest());
        takeTestButton.addActionListener(e -> simulateSelectedTests());
        testTypeBox.addActionListener(e -> switchTestMode());

        frame.setVisible(true);
    }

    private void switchTestMode() {
        String selected = (String) testTypeBox.getSelectedItem();

        if(selected.equals("Blood Tests")) {
            testList.setListData(bloodTests);
        } else {
            testList.setListData(urineTests);
        }
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(TEXT_COLOR);
        return label;
    }

    private void getPatientInfo() {
        JTextField nameField = new JTextField();
        JTextField ageField = new JTextField();
        JComboBox<String> sexBox = new JComboBox<>(new String[]{"Male","Female"});

        JPanel panel = new JPanel(new GridLayout(3,2));
        panel.add(new JLabel("Name:")); panel.add(nameField);
        panel.add(new JLabel("Sex:")); panel.add(sexBox);
        panel.add(new JLabel("Age:")); panel.add(ageField);

        int result;
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

    private void styleButton(JButton b){
        b.setBackground(BUTTON_COLOR);
        b.setForeground(TEXT_COLOR);
    }

    private void addSelectedTest() {
        String selected = testList.getSelectedValue();
        if(selected == null) return;

        String type = (String) testTypeBox.getSelectedItem();
        String labeled = (type.equals("Blood Tests") ? "[BLOOD] " : "[URINE] ") + selected;

        if(!selectedTestsModel.contains(labeled)){
            selectedTestsModel.addElement(labeled);
        }
    }

    private void simulateSelectedTests() {
        if(selectedTestsModel.isEmpty()){
            JOptionPane.showMessageDialog(frame,"No tests selected!");
            return;
        }

        double total = 0;

        for(int i=0;i<selectedTestsModel.size();i++){
            String labeled = selectedTestsModel.get(i);
            String name = labeled.replace("[BLOOD] ", "").replace("[URINE] ", "");
            selectTest(name);
            total += currentTest.getPrice();
        }

        int confirm = JOptionPane.showConfirmDialog(frame,
                "Total Price: $" + total + "\nProceed?",
                "Payment", JOptionPane.OK_CANCEL_OPTION);

        if(confirm != JOptionPane.OK_OPTION) return;

        Random rand = new Random();

        for(int i=0;i<selectedTestsModel.size();i++){
            String labeled = selectedTestsModel.get(i);
            String name = labeled.replace("[BLOOD] ", "").replace("[URINE] ", "");
            selectTest(name);

            double value = rand.nextDouble() * 300;
            String result = currentTest.evaluate(value, patientSex);

            String type = labeled.contains("[BLOOD]") ? "BLOOD TEST" : "URINE TEST";

            JTextArea area = new JTextArea();
            area.setEditable(false);

            area.setText(
                    "PATIENT: " + patientName +
                    "\nTYPE: " + type +
                    "\nTEST: " + name +
                    "\nVALUE: " + value +
                    "\nRESULT: " + result +
                    "\n\n" + currentTest.info()
            );

            JFrame f = new JFrame(name + " Result");
            f.setSize(350,250);
            f.add(new JScrollPane(area));
            f.setVisible(true);
        }

        selectedTestsModel.clear();
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
        }
    }
}