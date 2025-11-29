/*
 * Created by JFormDesigner on Wed Nov 26 11:07:32 CET 2025
 */

package UI.GUI;

import Connection.Connection_Patient;

import java.awt.*;
import java.sql.Date;
import javax.swing.*;


public class UpdatePanel extends JPanel {
    private Connection_Patient connection;
    private AppFrame appFrame;

    public UpdatePanel(AppFrame appFrame, Connection_Patient connection) {
        this.appFrame = appFrame;
        this.connection = connection;
        initComponents();

        setBorder(BorderFactory.createEmptyBorder(40,40,40,40));
        label.setFont(new Font("Arial", Font.BOLD, 20));
        name_label.setFont(new Font("Arial", Font.PLAIN, 16));
        surname_label.setFont(new Font("Arial", Font.PLAIN, 16));
        phone_label.setFont(new Font("Arial", Font.PLAIN, 16));
        mh_label.setFont(new Font("Arial", Font.PLAIN, 16));
        dob_label.setFont(new Font("Arial", Font.PLAIN, 16));
        sex_label.setFont(new Font("Arial", Font.PLAIN, 16));
        update_button.setFont(new Font("Arial", Font.PLAIN, 16));
        back_button.setFont(new Font("Arial", Font.PLAIN, 16));

        update_button.setBackground(new Color(70,130,180));
        update_button.setForeground(Color.WHITE);
        back_button.setBackground(new Color(62,156,118));
        back_button.setForeground(Color.WHITE);
        update_button.addActionListener(e-> update());
        back_button.addActionListener(e -> backToMenu());

        setBorder(BorderFactory.createEmptyBorder(40,40,40,40));

    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - Nerea Leria
        label = new JLabel();
        name_label = new JLabel();
        name_field = new JTextField();
        surname_label = new JLabel();
        surname_field = new JTextField();
        phone_label = new JLabel();
        phone_field = new JTextField();
        mh_label = new JLabel();
        mh_field = new JTextField();
        dob_label = new JLabel();
        dob_field = new JTextField();
        sex_label = new JLabel();
        sex_field = new JTextField();
        update_button = new JButton();
        back_button = new JButton();

        //======== this ========
        setBorder ( new javax . swing. border .CompoundBorder ( new javax . swing. border .TitledBorder ( new javax .
        swing. border .EmptyBorder ( 0, 0 ,0 , 0) ,  "JF\u006frmD\u0065sig\u006eer \u0045val\u0075ati\u006fn" , javax. swing .border
        . TitledBorder. CENTER ,javax . swing. border .TitledBorder . BOTTOM, new java. awt .Font ( "Dia\u006cog"
        , java .awt . Font. BOLD ,12 ) ,java . awt. Color .red ) , getBorder
        () ) );  addPropertyChangeListener( new java. beans .PropertyChangeListener ( ){ @Override public void propertyChange (java
        . beans. PropertyChangeEvent e) { if( "\u0062ord\u0065r" .equals ( e. getPropertyName () ) )throw new RuntimeException
        ( ) ;} } );
        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {131, 310, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

        //---- label ----
        label.setText("Update your information (leave any field blank to keep it unchanged)");
        add(label, new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //---- name_label ----
        name_label.setText("Name:");
        add(name_label, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));
        add(name_field, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //---- surname_label ----
        surname_label.setText("Surname:");
        add(surname_label, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));
        add(surname_field, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //---- phone_label ----
        phone_label.setText("Phone:");
        add(phone_label, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));
        add(phone_field, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //---- mh_label ----
        mh_label.setText("Medical History:");
        add(mh_label, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));
        add(mh_field, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //---- dob_label ----
        dob_label.setText("Date of birh:");
        add(dob_label, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));
        add(dob_field, new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //---- sex_label ----
        sex_label.setText("Sex");
        add(sex_label, new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));
        add(sex_field, new GridBagConstraints(1, 6, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //---- update_button ----
        update_button.setText("UPDATE");
        add(update_button, new GridBagConstraints(1, 7, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //---- back_button ----
        back_button.setText("BACK TO PATIENT MENU");
        add(back_button, new GridBagConstraints(1, 8, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }
    private void backToMenu() {
        appFrame.switchPanel(new PatientMenuPanel(appFrame,connection));
    }

    private void update() {
        try{
            boolean update = false;
            String name = name_field.getText().trim();
            if(!name.isEmpty()){
                connection.sendUpdateToServer("name",name);
                update = true;
            }
            String surname = surname_field.getText().trim();
            if(!surname.isEmpty()){
                connection.sendUpdateToServer("surname",surname);
                update = true;
            }
            String phone = phone_field.getText().trim();
            if(!phone.isEmpty()){
                connection.sendUpdateToServer("phone",phone);
                update = true;
            }
            String mh = mh_field.getText().trim();
            if(!mh.isEmpty()){
                connection.sendUpdateToServer("medical_history",mh);
                update = true;
            }
            String dob = dob_field.getText().trim();
            if(!dob.isEmpty()){
                try{
                    Date.valueOf(dob); // This checks if the format is correct
                    connection.sendUpdateToServer("dob",dob);
                    update = true;
                }catch(IllegalArgumentException e){
                    JOptionPane.showMessageDialog(this, "Use format: yyyy-mm-dd");
                    return;
                }
            }
            String sex = sex_field.getText().trim().toUpperCase();
            if (!sex.isEmpty()) {
                if(!sex.equals("M")&& !sex.equals("F")){
                    JOptionPane.showMessageDialog(this, "Use M or F");
                    return;
                }
                connection.sendUpdateToServer("sex",sex);
                update = true;
            }

            if(!update){
                JOptionPane.showMessageDialog(this, "No updates");
            }else{
                JOptionPane.showMessageDialog(this, "Your information is updated");
            }

        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "Please enter a valid data");
        }
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - Nerea Leria
    private JLabel label;
    private JLabel name_label;
    private JTextField name_field;
    private JLabel surname_label;
    private JTextField surname_field;
    private JLabel phone_label;
    private JTextField phone_field;
    private JLabel mh_label;
    private JTextField mh_field;
    private JLabel dob_label;
    private JTextField dob_field;
    private JLabel sex_label;
    private JTextField sex_field;
    private JButton update_button;
    private JButton back_button;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
