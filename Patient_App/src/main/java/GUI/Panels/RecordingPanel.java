
package GUI.Panels;


import Connection.Connection_Patient;
import Bitalino.Frame;
import Bitalino.BITalino;
import org.example.Server.Visualization.PlotRecordings;


import java.awt.*;
import java.io.IOException;
import javax.swing.*;



public class RecordingPanel extends JPanel {
    private Connection_Patient connection;
    private AppFrame appFrame;
    public RecordingPanel(AppFrame appFrame) {
        this.appFrame = appFrame;
        this.connection = appFrame.getConnection();
        
        initComponents();

        setBorder(BorderFactory.createEmptyBorder(40,110,30,30));
        
        label1.setFont(new Font("Arial", Font.BOLD, 18));
        label2.setFont(new Font("Arial", Font.BOLD, 18));
        emg_button.setFont(new Font("Arial", Font.PLAIN, 14));
        ecg_button.setFont(new Font("Arial", Font.PLAIN, 14));
        both_button.setFont(new Font("Arial", Font.PLAIN, 14));
        back_button.setFont(new Font("Arial", Font.PLAIN, 14));
        done_button.setFont(new Font("Arial", Font.PLAIN, 14));

        emg_button.setBackground(new Color(70,130,180));
        emg_button.setForeground(Color.WHITE);
        ecg_button.setBackground(new Color(70,130,180));
        ecg_button.setForeground(Color.WHITE);
        both_button.setBackground(new Color(70,130,180));
        both_button.setForeground(Color.WHITE);
        back_button.setBackground(new Color(62,156, 118));
        back_button.setForeground(Color.WHITE);
        done_button.setBackground(new Color(62,156, 118));
        done_button.setForeground(Color.WHITE);

        emg_button.addActionListener(e -> startRecording("EMG"));
        ecg_button.addActionListener(e -> startRecording("ECG"));
        both_button.addActionListener(e -> startRecording("BOTH"));
        back_button.addActionListener(e-> backToMenu());
        done_button.addActionListener(e-> macAddressCheck());

    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        label2 = new JLabel();
        MAC_field = new JTextField();
        done_button = new JButton();
        separator1 = new JSeparator();
        label1 = new JLabel();
        emg_button = new JButton();
        ecg_button = new JButton();
        both_button = new JButton();
        back_button = new JButton();

        //======== this ========

        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {277, 0, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

        //---- label2 ----
        label2.setText("Introduce the BITalino MAC address");
        add(label2, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));
        add(MAC_field, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- done_button ----
        done_button.setText("DONE");
        add(done_button, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));
        add(separator1, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- label1 ----
        label1.setText("Select type of recording");
        add(label1, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- emg_button ----
        emg_button.setText("EMG");
        add(emg_button, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- ecg_button ----
        ecg_button.setText("ECG");
        add(ecg_button, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- both_button ----
        both_button.setText("BOTH");
        add(both_button, new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- back_button ----
        back_button.setText("BACK TO PATIENT MENU");
        add(back_button, new GridBagConstraints(0, 7, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 5), 0, 0));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }//

    private void startRecording(String type) {
    // This disables the buttons while recording is in progress
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
                String mac = MAC_field.getText().trim();
                bita.open(mac,100);
                bita.start(channel);

                int numFrames = 1000;
                for(int i = 0; i < numFrames; i++){
                    Frame[] block = bita.read(1);
                    connection.sendFrames(block, channel);
                }

                bita.stop();
                bita.close();

                String endResponse = connection.endRecordingAndGetResponse();

                if(endResponse != null && endResponse.startsWith("ERROR|EMPTY_RECORDING")){
                    SwingUtilities.invokeLater(() -> {
                        JOptionPane.showMessageDialog(
                                this,
                                "Recording cancelled. \n No data was being received from BITalino. \nPlease check the connection and try again.",
                                "Empty Recording",
                                JOptionPane.WARNING_MESSAGE);
                    });
                    return;
                }
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

    private static void plotSignalByType(Connection_Patient connect, int recording_id, String type) throws IOException {
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
    public void backToMenu() {
        appFrame.switchPanel(new PatientMenuPanel(appFrame));
    }

    private void macAddressCheck() {
        String mac = MAC_field.getText().trim().replace(":", "");

        if(!mac.matches("^[0-9A-Fa-f]{12}$")){
            JOptionPane.showMessageDialog(this, "Invalid MAC address format.",
                    "MAC Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        final String mac_final = mac;
        new Thread(() -> {
            try{
                BITalino bita = new BITalino();
                bita.open(mac_final, 100);
                bita.close();

                SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(
                        this,
                        "BITalino detected successfully.",
                        "MAC OK",
                        JOptionPane.INFORMATION_MESSAGE));
                SwingUtilities.invokeLater(() -> MAC_field.setText(mac_final));
            }catch (Exception e){
                SwingUtilities.invokeLater(() ->
                                JOptionPane.showMessageDialog(this,
                            "BITalino not found or cannot connect.\n" +
                                    "Check that it is ON and paired.\n" +
                                    "Error: "+ e.getMessage(),
                            "CONNECTION ERROR",
                                        JOptionPane.ERROR_MESSAGE
                                )
                        );
            }
        }).start();
    }
    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JLabel label2;
    private JTextField MAC_field;
    private JButton done_button;
    private JSeparator separator1;
    private JLabel label1;
    private JButton emg_button;
    private JButton ecg_button;
    private JButton both_button;
    private JButton back_button;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
