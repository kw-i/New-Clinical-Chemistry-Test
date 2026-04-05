import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class LabGUI {
    private JFrame frame;
    private JList<String> testList;
    private DefaultListModel<String> selectedTestsModel;
    private JList<String> selectedTestsList;
    private JButton addTestButton, takeTestButton;
    private Test currentTest;

    private String patientName;
    private char patientSex;
    private int patientAge;

    private final Color BG_COLOR = new Color(255, 248, 180);
    private final Color PANEL_COLOR = new Color(255, 239, 140);
    private final Color BUTTON_COLOR = new Color(255, 204, 0);
    private final Color TEXT_COLOR = new Color(102, 75, 0);
    private final Color FIELD_COLOR = new Color(255, 255, 210);

    private final String[] tests = {
            "FBS","RBS","Total Cholesterol","HDL","LDL","Triglycerides",
            "Creatinine","Uric Acid","BUN","AST","ALT","Albumin",
            "Total Protein","ALP","Total Calcium",
            "Sodium","Potassium","Chloride",
            "Ionized Calcium"
    };

    public LabGUI() {
        getPatientInfo();

        frame = new JFrame("Clinical Chemistry Lab Simulator");
        frame.setSize(750, 450);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BG_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        // Top info panel
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 5));
        infoPanel.setBackground(PANEL_COLOR);
        infoPanel.add(createLabel("Name: " + patientName));
        infoPanel.add(createLabel("Age: " + patientAge));
        infoPanel.add(createLabel("Sex: " + patientSex));
        mainPanel.add(infoPanel, BorderLayout.NORTH);

        // Center panel: Available + Selected tests
        JPanel centerPanel = new JPanel(new BorderLayout(10,0));
        centerPanel.setBackground(BG_COLOR);

        // Available tests panel
        JPanel availablePanel = new JPanel(new BorderLayout());
        availablePanel.setBackground(PANEL_COLOR);
        availablePanel.add(createLabel("Available Tests:"), BorderLayout.NORTH);

        testList = new JList<>(tests);
        testList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        testList.setBackground(FIELD_COLOR);
        testList.setForeground(TEXT_COLOR);
        testList.setVisibleRowCount(15);
        availablePanel.add(new JScrollPane(testList), BorderLayout.CENTER);

        // Buttons panel
        JPanel buttonsPanel = new JPanel(new FlowLayout());
        buttonsPanel.setBackground(PANEL_COLOR);
        addTestButton = new JButton("Add Test");
        takeTestButton = new JButton("Take Test (Simulated)");
        styleButton(addTestButton);
        styleButton(takeTestButton);
        buttonsPanel.add(addTestButton);
        buttonsPanel.add(takeTestButton);
        availablePanel.add(buttonsPanel, BorderLayout.SOUTH);

        centerPanel.add(availablePanel, BorderLayout.CENTER);

        // Selected tests panel
        JPanel selectedPanel = new JPanel(new BorderLayout());
        selectedPanel.setBackground(PANEL_COLOR);
        selectedPanel.add(createLabel("Selected Tests:"), BorderLayout.NORTH);

        selectedTestsModel = new DefaultListModel<>();
        selectedTestsList = new JList<>(selectedTestsModel);
        selectedTestsList.setBackground(FIELD_COLOR);
        selectedTestsList.setForeground(TEXT_COLOR);

        JScrollPane selectedScroll = new JScrollPane(selectedTestsList);
        selectedScroll.setPreferredSize(new Dimension(180, 200));
        selectedPanel.add(selectedScroll, BorderLayout.CENTER);

        centerPanel.add(selectedPanel, BorderLayout.EAST);
        mainPanel.add(centerPanel);
        frame.add(mainPanel);

        // Action listeners
        addTestButton.addActionListener(e -> addSelectedTest());
        takeTestButton.addActionListener(e -> simulateSelectedTests());

        frame.setVisible(true);
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

        JPanel inputPanel = new JPanel(new GridLayout(3,2,5,5));
        inputPanel.setBackground(PANEL_COLOR);
        inputPanel.add(new JLabel("Name:")); inputPanel.add(nameField);
        inputPanel.add(new JLabel("Sex:")); inputPanel.add(sexBox);
        inputPanel.add(new JLabel("Age:")); inputPanel.add(ageField);

        int result;
        do {
            result = JOptionPane.showConfirmDialog(null, inputPanel, "Enter Patient Information",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if(result != JOptionPane.OK_OPTION) System.exit(0);
        } while(nameField.getText().isEmpty() || ageField.getText().isEmpty());

        patientName = nameField.getText();
        patientSex = sexBox.getSelectedItem().equals("Male") ? 'M' : 'F';
        try { patientAge = Integer.parseInt(ageField.getText()); }
        catch(NumberFormatException e) { patientAge = 0; }
    }

    private void styleButton(JButton button){
        button.setBackground(BUTTON_COLOR);
        button.setForeground(TEXT_COLOR);
        button.setFocusPainted(false);
    }

    private void addSelectedTest() {
        String selected = testList.getSelectedValue();
        if(selected != null && !selectedTestsModel.contains(selected)) {
            selectedTestsModel.addElement(selected);
        }
    }

    private void simulateSelectedTests() {
        if(selectedTestsModel.isEmpty()){
            JOptionPane.showMessageDialog(frame,"No tests selected!");
            return;
        }

        // Calculate total price
        double totalPrice = 0;
        for(int i=0;i<selectedTestsModel.size();i++){
            selectTest(selectedTestsModel.get(i));
            totalPrice += currentTest.getPrice();
        }

        // Payment simulation
        String paymentMsg = String.format("Total Price: $%.2f\nProceed with payment?", totalPrice);
        int payment = JOptionPane.showConfirmDialog(frame, paymentMsg, "Payment", JOptionPane.OK_CANCEL_OPTION);
        if(payment != JOptionPane.OK_OPTION){
            JOptionPane.showMessageDialog(frame,"Payment cancelled. Tests not processed.");
            return;
        }

        // Generate results
        Random rand = new Random();
        int xOffset = 30, yOffset = 30;
        int count = 0;
        int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;

        for(int i=0;i<selectedTestsModel.size();i++){
            String testName = selectedTestsModel.get(i);
            selectTest(testName);

            boolean takesTime = currentTest.takesTime();
            if(takesTime){
                String timeRequired = currentTest.getProcessingTime();
                JOptionPane.showMessageDialog(frame,
                        testName + ":\nCollect your results in " + timeRequired + ".");
            } else {
                double value = rand.nextDouble() * 300;

                String result = currentTest.evaluate(value, patientSex);
                String interpretation = currentTest.info();
                String range = currentTest.getRange().getRangeString(patientSex);

                JFrame resultFrame = new JFrame(testName + " Result");
                resultFrame.setSize(400,300);

                int x = 100 + (count * xOffset);
                int y = 100 + (count * yOffset);
                if(x + 400 > screenWidth) x = 50;
                if(y + 300 > screenHeight) y = 50;
                resultFrame.setLocation(x, y);
                count++;

                JTextArea textArea = new JTextArea();
                textArea.setEditable(false);
                textArea.setBackground(BG_COLOR);

                textArea.setText(
                        "PATIENT: " + patientName +
                                "\nSEX: " + patientSex +
                                "\nAGE: " + patientAge +
                                "\n\nTEST: " + testName +
                                "\nPRICE: $" + String.format("%.2f", currentTest.getPrice()) +
                                "\nVALUE: " + String.format("%.2f", value) +
                                "\nREFERENCE RANGE: " + range +
                                "\nRESULT: " + result +
                                "\n\nINTERPRETATION:\n" + interpretation
                );

                if(result.equals("HIGH")) textArea.setForeground(Color.RED);
                else if(result.equals("LOW")) textArea.setForeground(Color.BLUE);
                else textArea.setForeground(new Color(0,128,0));

                resultFrame.add(new JScrollPane(textArea));
                resultFrame.setVisible(true);
            }
        }

        // Clear selected tests
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
        }
    }
}