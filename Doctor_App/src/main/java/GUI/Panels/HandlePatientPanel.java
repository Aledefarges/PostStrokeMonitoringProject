
package GUI.Panels;

import org.example.Connection.Connection_Doctor;
import org.example.POJOS.Patient;

import java.awt.*;
import javax.swing.*;
import java.util.List;



public class HandlePatientPanel extends JPanel {
    private Connection_Doctor connection;
    private AppFrameDoctor appFrame;
    private JList<Patient> patient_list;
    private volatile boolean running = true;

    public HandlePatientPanel(AppFrameDoctor appFrame) {
        this.connection = appFrame.getConnection();
        this.appFrame = appFrame;
        initComponents();

        label1.setFont(new Font("Arial", Font.BOLD, 16));
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(70,130,180),1,true),
                BorderFactory.createEmptyBorder(40,70,40,40))
        );

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
        
        back_button.setFont(new Font("Arial", Font.PLAIN, 14));
        back_button.setBackground(new Color(62, 156, 118));
        back_button.setForeground(Color.WHITE);
        
        back_button.addActionListener(e -> backToMenu());

        loadPatientList();
        configureHandleSelect();
        SwingUtilities.invokeLater(this::startAutoRefresh);

    }


    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        label1 = new JLabel();
        scrollPane1 = new JScrollPane();
        patient_list = new JList<>();
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
        add(scrollPane1, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0,
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
        running = false;
        appFrame.switchPanel(new DoctorMenuPanel(appFrame));
    }

    private void loadPatientList(){
        DefaultListModel<Patient> patient_model =  new DefaultListModel<>();
        patient_list.setVisibleRowCount(6);
        patient_list.setFixedCellHeight(80);
        patient_list.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        List<Patient> patients = connection.requestAllPatients();

        if(patients == null){
            JOptionPane.showMessageDialog(this,"No patients found");
        } else if(patients.isEmpty()){
            JOptionPane.showMessageDialog(this,"No patients assigned yet", "INFORMATION",
                    JOptionPane.INFORMATION_MESSAGE);
        }else{
            for(Patient patient: patients){
                patient_model.addElement(patient);
            }
        }
        patient_list.setModel(patient_model);
    }
    private void configureHandleSelect(){
        patient_list.addListSelectionListener( e-> {
            if(!e.getValueIsAdjusting()){ // indica que se ha terminado de selecionar un elemento de la lista
                Patient selected = patient_list.getSelectedValue();
                if(selected!=null){
                    appFrame.switchPanel(new PatientOptionPanel(appFrame,selected));
                }
            }
        } );
    }
    private void startAutoRefresh(){
        running = true;
        new Thread(()->{
            while(running){
                try{
                    List<Patient> updated_list = connection.requestAllPatients();
                    SwingUtilities.invokeLater(()->{
                        DefaultListModel<Patient> patient_model =  (DefaultListModel<Patient>) patient_list.getModel();
                        patient_model.clear();
                        if(updated_list != null){
                            for (Patient patient: updated_list){
                                patient_model.addElement(patient);
                            }
                        }

                    });

                    Thread.sleep(5000);
                }catch(Exception e){
                    e.printStackTrace();
                    running = false;
                    break;
                }
            }
        }).start();
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JLabel label1;
    private JScrollPane scrollPane1;
    private JButton back_button;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on


}
