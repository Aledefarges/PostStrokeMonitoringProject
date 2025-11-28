/*
 * Created by JFormDesigner on Thu Nov 27 15:51:20 CET 2025
 */

package GUI;

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
        DefaultListModel<Patient> list =  new DefaultListModel<>();
        String response = connection.requestAllPatients();
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
                    String patient_1 = patients.toString();
                    list.addElement(patients);
                }
            }catch(Exception e){
                e.printStackTrace();
            }

        }
        patient_list.setModel(list);

    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - Nerea Leria
        label1 = new JLabel();
        scrollPane1 = new JScrollPane();
        patient_list = new JList();

        //======== this ========
        setBorder ( new javax . swing. border .CompoundBorder ( new javax . swing. border .TitledBorder ( new javax . swing. border
        .EmptyBorder ( 0, 0 ,0 , 0) ,  "JF\u006frmDes\u0069gner \u0045valua\u0074ion" , javax. swing .border . TitledBorder. CENTER ,javax
        . swing. border .TitledBorder . BOTTOM, new java. awt .Font ( "D\u0069alog", java .awt . Font. BOLD ,
        12 ) ,java . awt. Color .red ) , getBorder () ) );  addPropertyChangeListener( new java. beans
        .PropertyChangeListener ( ){ @Override public void propertyChange (java . beans. PropertyChangeEvent e) { if( "\u0062order" .equals ( e.
        getPropertyName () ) )throw new RuntimeException( ) ;} } );
        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {331, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {0.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0E-4};

        //---- label1 ----
        label1.setText("List of patients (click one the one of interest)");
        add(label1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //======== scrollPane1 ========
        {
            scrollPane1.setViewportView(patient_list);
        }
        add(scrollPane1, new GridBagConstraints(0, 1, 1, 3, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - Nerea Leria
    private JLabel label1;
    private JScrollPane scrollPane1;
    private JList patient_list;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
