

package GUI.Panels;

import Connection.Connection_Patient;

import java.awt.*;
import javax.swing.*;

public class FeedbackPatientPanel extends JPanel {
    private Connection_Patient connection;
    private AppFrame appFrame;
    public FeedbackPatientPanel(AppFrame appFrame) {
        this.appFrame = appFrame;
        this.connection = appFrame.getConnection();
        initComponents();

        setBorder(BorderFactory.createEmptyBorder(110,110,30,30));
        feedback_text.setEditable(false); // This text can only be read
        feedback_text.setLineWrap(true);
        feedback_text.setWrapStyleWord(true);
        label1.setFont(new Font("Arial", Font.BOLD, 18));
        feedback_text.setFont(new Font("Arial", Font.BOLD, 14));
        back_button.setFont(new Font("Arial", Font.PLAIN, 14));

        back_button.setBackground(new Color(62, 156, 118));
        back_button.setForeground(Color.WHITE);

        back_button.addActionListener(e -> appFrame.switchPanel(new PatientMenuPanel(appFrame)));
        obtainFeedback();

    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - Nerea Leria
        label1 = new JLabel();
        scrollPane1 = new JScrollPane();
        feedback_text = new JTextArea();
        back_button = new JButton();

        //======== this ========
        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {285, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {0.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0E-4};

        //---- label1 ----
        label1.setText("Feedback from Doctor");
        add(label1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //======== scrollPane1 ========
        {
            scrollPane1.setViewportView(feedback_text);
        }
        add(scrollPane1, new GridBagConstraints(0, 1, 1, 2, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //---- back_button ----
        back_button.setText("BACK TO PATIENT MENU");
        add(back_button, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    private void obtainFeedback(){
        String feedback = connection.requestFeedback();
        if (feedback == null){
            feedback_text.setText("No feedback available yet");
        }else{
            feedback_text.setText(feedback);
        }
    }
    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - Nerea Leria
    private JLabel label1;
    private JScrollPane scrollPane1;
    private JTextArea feedback_text;
    private JButton back_button;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
