/*
 * Created by JFormDesigner on Thu Nov 27 15:51:20 CET 2025
 */

package GUI.Panels;

import org.example.Connection.Connection_Doctor;
import org.example.POJOS.Patient;

import java.awt.*;
import java.sql.Date;
import javax.swing.*;


public class HandlePatientPanel extends JPanel {
    private Connection_Doctor connection;
    private AppFrameDoctor appFrame;

    public HandlePatientPanel(AppFrameDoctor appFrame, Connection_Doctor connection) {
        this.connection = connection;
        this.appFrame = appFrame;
        initComponents();

        label1.setFont(new Font("Arial", Font.BOLD, 16));
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(70,130,180),1,true),
                BorderFactory.createEmptyBorder(40,70,40,40))
        );


        DefaultListModel<Patient> list =  new DefaultListModel<>();
        String response = connection.requestAllPatients();
        patient_list.setVisibleRowCount(6);
        patient_list.setFixedCellHeight(80);
        patient_list.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        if (response.startsWith("PATIENTS_LIST|")){
            try{
                String data = response.substring("PATIENTS_LIST|".length());
                String[] parts = data.split("\\|");
                for(String part:parts){
                    String[] patient = part.split(";");
                    int patient_id = Integer.parseInt(patient[0]);
                    String name = patient[1];
                    String surname = patient[2];
                    String dob = patient[3];
                    Date dob1 = Date.valueOf(dob);
                    String email = patient[4];
                    int phone = Integer.parseInt(patient[5]);
                    String medical_history = patient[6];
                    String sex = patient[7];
                    Patient.Sex sexEnum = Patient.Sex.valueOf(sex);

                    Patient patients = new Patient(patient_id, "", name, surname, dob1, email, phone, medical_history, sexEnum);
                    list.addElement(patients);
                }
            }catch(Exception e){
                e.printStackTrace();
            }

        }
        patient_list.setModel(list);

        patient_list.setCellRenderer(new ListCellRenderer<Patient>() {
            public Component getListCellRendererComponent(JList<? extends Patient> list, Patient patient, int index, boolean selected, boolean focused) {
                String text = "<html>" + patient.getName() + " " + patient.getSurname() + "<br>" +
                        patient.getDob() + " | " + patient.getSex() + "<br>" +
                        "Medical history: " + patient.getMedicalhistory() + "<br>" +
                        "Contact: " + patient.getEmail() + " | " + patient.getPhone() + "<br>" +
                        "     -------------------     " + "</html>";
                JLabel label = new JLabel(text);
                label.setFont(new Font("Arial", Font.BOLD, 14));
                label.setOpaque(true);
                if(selected){
                    label.setBackground(new Color(70,130,180));
                    label.setForeground(Color.white);
                }else{
                    label.setBackground(Color.white);
                    label.setForeground(Color.black);
                }
                return label;
            }
        });

        patient_list.addListSelectionListener( e-> {
            if(!e.getValueIsAdjusting()){ // indica que se ha terminado de selecionar un elemento de la lista
                Patient selected = (Patient) patient_list.getSelectedValue();
                if(selected!=null){
                    appFrame.switchPanel(new PatientOptionPanel(appFrame,connection,selected));
                }
            }
        } );
        
        back_button.setFont(new Font("Arial", Font.PLAIN, 14));
        back_button.setBackground(new Color(62, 156, 118));
        back_button.setForeground(Color.WHITE);
        
        back_button.addActionListener(e -> backToMenu()); 
        

    }


    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - Nerea Leria
        label1 = new JLabel();
        scrollPane1 = new JScrollPane();
        patient_list = new JList();
        back_button = new JButton();

        //======== this ========
        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {331, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {0.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0E-4};

        //---- label1 ----
        label1.setText("List of patients (click the one of interest)");
        add(label1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //======== scrollPane1 ========
        {
            scrollPane1.setViewportView(patient_list);
            scrollPane1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        }
        add(scrollPane1, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //---- back_button ----
        back_button.setText("BACK TO DOCTOR MENU");
        add(back_button, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }
    
    public void backToMenu(){
        appFrame.switchPanel(new DoctorMenuPanel(appFrame,connection));
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - Nerea Leria
    private JLabel label1;
    private JScrollPane scrollPane1;
    private JList patient_list;
    private JButton back_button;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on


}
