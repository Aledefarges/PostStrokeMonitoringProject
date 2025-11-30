/*
 * Created by JFormDesigner on Fri Nov 28 17:05:40 CET 2025
 */
//
package GUI.Panels;

import org.example.Connection.Connection_Doctor;
import org.example.POJOS.Patient;
import org.example.POJOS.Recording;
import org.example.Server.Visualization.PlotRecordings;

import java.awt.*;
import java.util.List;
import javax.swing.*;

public class RecordingsPanel extends JPanel {
    private Connection_Doctor connection;
    private AppFrameDoctor appFrame;
    private Patient patient;
    private JList<Recording> recording_list;
    
    public RecordingsPanel(AppFrameDoctor appFrame, Connection_Doctor connection, Patient patient) {
        this.connection = connection;
        this.appFrame = appFrame;
        this.patient = patient;
        
        initComponents();

        label1.setFont(new Font("Arial", Font.BOLD, 16));
        back_button.setFont(new Font("Arial", Font.PLAIN, 14));
        back_button.setBackground(new Color(62, 156, 118));
        back_button.setForeground(Color.WHITE);

        back_button.addActionListener(e-> backToMenu());
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(70,130,180),1,true),
                BorderFactory.createEmptyBorder(60,70,40,40))
        );


        recording_list.setCellRenderer(new ListCellRenderer<Recording>() {
            public Component getListCellRendererComponent(JList<? extends Recording> list, Recording recording, int index, boolean selected, boolean focused) {
                String text = "<html>" + recording.getId() + "| "  + recording.getType() + " | " +
                        recording.getDateRecording() + "<br>" +
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

        loadRecordings();
        configureSelectHandle();
        SwingUtilities.invokeLater(this::startAutoRefresh);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        label1 = new JLabel();
        back_button = new JButton();
        recording_list = new JList<Recording>();
        scrollPane1 = new JScrollPane();


        //======== this ========
        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {274, 63, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};

        //---- label1 ----
        label1.setText("Select which recording to observe");
        add(label1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- back_button ----
        back_button.setText("BACK");
        add(back_button, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //======== scrollPane1 ========
        {
            scrollPane1.setViewportView(recording_list);
        }
        add(scrollPane1, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JLabel label1;
    private JButton back_button;
    private JScrollPane scrollPane1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on

    public void loadRecordings(){
        DefaultListModel<Recording> recording_model =  new DefaultListModel<>();
        List<Recording> recordings = connection.requestRecordingsByPatient(patient.getPatient_id());
        recording_list.setVisibleRowCount(6);
        recording_list.setFixedCellHeight(80);

        if(recordings == null){
            JOptionPane.showMessageDialog(this, "No recordings found");
        }else if(recordings.isEmpty()){
            JOptionPane.showMessageDialog(this, "No recordings created yet",
                    "NO RECORDINGS", JOptionPane.INFORMATION_MESSAGE);
        }else{
            for(Recording recording : recordings){
                recording_model.addElement(recording);
            }
        }
        recording_list.setModel(recording_model);
    }

    private void configureSelectHandle(){
        recording_list.addListSelectionListener(e->{

            if(e.getValueIsAdjusting()) return;
            Recording recording = recording_list.getSelectedValue();
            if(recording == null) return;

            String response = connection.requestSpecificRecording(recording.getId());

            if (response == null || !response.startsWith("RECORDING_DATA|")) {
                JOptionPane.showMessageDialog(this, "No data available for this recording.");
                return;
            }

            String[] parts = response.split("\\|");
            String part = parts[2];
            String[] frames = part.split(",");

            String diagnosis = null;
            if(parts.length >= 4){
                diagnosis = parts[3];
            }
            // Se pone menos de 4 porque solo analiza la señal ECG

            if (recording.getType() == Recording.Type.ECG ||  recording.getType() ==  Recording.Type.EMG){
                Double[] data = new Double[frames.length];
                for(int i = 0; i < frames.length; i++){
                    data[i] = Double.parseDouble(frames[i]);
                }
                PlotRecordings.showChartFromArray(data, "Recording ID " +recording.getId());

            } else if(recording.getType() == Recording.Type.BOTH){
                Double [] emg = new Double[frames.length];
                Double [] ecg = new Double[frames.length];

                for(int i = 0; i < frames.length; i++){
                    String[] pair = frames[i].split(";");
                    emg[i] = Double.parseDouble(pair[0]);
                    ecg[i] = Double.parseDouble(pair[1]);

                }
                PlotRecordings.showChartFromArray(emg, "EMG Recording ID " +recording.getId());
                PlotRecordings.showChartFromArray(ecg, "ECG Recording ID " +recording.getId());
            }

            if(diagnosis != null){
                JOptionPane.showMessageDialog(this, diagnosis, "ECG Analysis", JOptionPane.INFORMATION_MESSAGE);
            }

        });
    }

    private void backToMenu() {
        appFrame.switchPanel(new PatientOptionPanel(appFrame, connection,patient));
    }

    private void startAutoRefresh(){
        new Thread(()->{
            while(true){
                try{
                    List<Recording> updated_list = connection.requestRecordingsByPatient(patient.getPatient_id());
                    SwingUtilities.invokeLater(()->{
                        DefaultListModel<Recording> recording_model =  (DefaultListModel<Recording>) recording_list.getModel();
                        recording_model.clear();
                        if(updated_list != null){
                            for (Recording recording: updated_list){
                                recording_model.addElement(recording);
                            }
                        }

                    });

                    Thread.sleep(5000);
                }catch(Exception e){
                    e.printStackTrace();
                    break;
                }
            }
        }).start();
    }


}
