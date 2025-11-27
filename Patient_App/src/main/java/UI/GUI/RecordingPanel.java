/*
 * Created by JFormDesigner on Wed Nov 26 12:08:06 CET 2025
 */

package UI.GUI;


import Connection.Connection_With_Server;
import Bitalino.Frame;
import Bitalino.BITalino;
import org.example.Server.Visualization.PlotRecordings;


import java.awt.*;
import java.io.IOException;
import javax.swing.*;



public class RecordingPanel extends JPanel {
    private Connection_With_Server connection;
    private AppFrame appFrame;
    public RecordingPanel(AppFrame appFrame, Connection_With_Server connection) {
        this.appFrame = appFrame;
        this.connection = connection;
        
        initComponents();

        setBorder(BorderFactory.createEmptyBorder(40,40,40,40));
        
        label1.setFont(new Font("Arial", Font.BOLD, 20));
        emg_button.setFont(new Font("Arial", Font.PLAIN, 16));
        ecg_button.setFont(new Font("Arial", Font.PLAIN, 16));
        both_button.setFont(new Font("Arial", Font.PLAIN, 16));
        back_button.setFont(new Font("Arial", Font.PLAIN, 16));

        emg_button.setBackground(new Color(70,130,180));
        emg_button.setForeground(Color.WHITE);
        ecg_button.setBackground(new Color(70,130,180));
        ecg_button.setForeground(Color.WHITE);
        both_button.setBackground(new Color(70,130,180));
        both_button.setForeground(Color.WHITE);
        back_button.setBackground(new Color(62,156, 118));
        
        emg_button.addActionListener(e -> startRecording("EMG"));
        ecg_button.addActionListener(e -> startRecording("ECG"));
        both_button.addActionListener(e -> startRecording("BOTH"));
        back_button.addActionListener(e-> appFrame.switchPanel(new PatientMenuPanel(appFrame, connection)));

    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - Nerea Leria
        label1 = new JLabel();
        emg_button = new JButton();
        ecg_button = new JButton();
        both_button = new JButton();
        back_button = new JButton();

        //======== this ========

        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {224, 0, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

        //---- label1 ----
        label1.setText("Select type of recording");
        add(label1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- emg_button ----
        emg_button.setText("EMG");
        add(emg_button, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- ecg_button ----
        ecg_button.setText("ECG");
        add(ecg_button, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- both_button ----
        both_button.setText("BOTH");
        add(both_button, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- back_button ----
        back_button.setText("BACK TO PATIENT MENU");
        add(back_button, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 5), 0, 0));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    private void startRecording(String type) {
    // Desactivar botones mientras graba
    emg_button.setEnabled(false);
    ecg_button.setEnabled(false);
    both_button.setEnabled(false);
    back_button.setEnabled(false);
    new Thread(() -> {
            try{
                int[][] result = connection.startRecording(type);
                int []channel = result[0];
                int recording_id = result[1][0];

                // Start BITalino
                BITalino bita = new BITalino();
                String mac = "98:D3:51:FD:9C:72";
                bita.open(mac,100);
                bita.start(channel);

                int numFrames = 1000;
                for(int i = 0; i < numFrames; i++){
                    Frame[] block = bita.read(1);
                    connection.sendFrames(block, channel);
                }

                bita.stop();
                bita.close();
                connection.endRecording();

                SwingUtilities.invokeLater(() -> {
                    try{
                        plotSignalByType(connection,recording_id,type);
                    }catch (Exception e){
                        JOptionPane.showMessageDialog(this, "Recording saved but cannot be visualized: "+ e.getMessage());
                    }
                });
            } catch (Throwable e) {
                e.printStackTrace();
                SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(this, "Error during recording: " +e.getMessage()));
            }finally {
                SwingUtilities.invokeLater(() -> {
                    emg_button.setEnabled(true);
                    ecg_button.setEnabled(true);
                    both_button.setEnabled(true);
                    back_button.setEnabled(true);
                });
            }
        }).start();

    }

    private static void plotSignalByType(Connection_With_Server connect, int recording_id, String type) throws IOException {
        Double[][] data = connect.requestRecordingData(recording_id, type);

        if(type.equalsIgnoreCase("ECG")||type.equalsIgnoreCase("EMG")){
            PlotRecordings.showChartFromArray(data[0],type + " Recording");
        }
        else if(type.equals("BOTH")){
            PlotRecordings.showChartFromArray((data[0]), " EMG Recording");
            PlotRecordings.showChartFromArray(data[1]," ECG Recording");
        }
        else {
            System.out.println("Unknown recording type: " + type);
        }
       //
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - Nerea Leria
    private JLabel label1;
    private JButton emg_button;
    private JButton ecg_button;
    private JButton both_button;
    private JButton back_button;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
