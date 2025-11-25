package Connection;



import Bitalino.BITalino;
import Bitalino.Frame;
import org.example.POJOS.Patient;
import org.example.Server.JDBC.JDBCManager;
import org.example.Server.Visualization.PlotRecordings;

import javax.bluetooth.RemoteDevice;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.Scanner;
import java.util.Vector;

public class TestConnection_With_Server {

    public static void main(String[] args) {

        //String ip = "172.20.10.3";


        String ip = "10.60.105.250";

        //String ip = "172.16.205.116";
        try {

                // 1. Connect
                Connection_With_Server connect = new Connection_With_Server();
                if (!connect.connection(ip,9100)){
                    System.out.println("Could not connect to server.");
                    return;
                } else System.out.println("Connected to server!");

                // 2. Ask patient data
                Scanner sc = new Scanner(System.in);

                /*System.out.print("Name: ");
                String name = sc.nextLine();
                System.out.print("Surname: ");
                String surname = sc.nextLine();
                System.out.print("Date of Birth (yyyy-mm-dd): ");
                Date dob = Date.valueOf(sc.nextLine());
                System.out.print("Email: ");
                String email = sc.nextLine();
                System.out.print("Phone: ");
                int phone = Integer.parseInt(sc.nextLine());
                System.out.print("Medical History: ");
                String history = sc.nextLine();
                System.out.print("Sex (M/F): ");
                String sex = sc.nextLine().toUpperCase();
                Patient.Sex sexEnum = Patient.Sex.valueOf(sex);
                System.out.println("Create a password: ");
                String password = sc.nextLine();
                Patient p = new Patient(name, surname, dob, email,sexEnum, history, phone, password);

                 */
/*
                // 3. Send patient to server
                boolean send_patient = connect.sendPatientToServer(p);
                if (send_patient) System.out.println("Patient saved in server!");
                else System.out.println("Error saving patient.");


                // 4. Delete patient from server
                System.out.println("Enter email to delete: ");
                String email_to_delete = sc.nextLine();
                boolean delete_patient = connect.deletePatientFromServer(email_to_delete);
                if(delete_patient) System.out.println("Patient deleted successfully!");
                else System.out.println("Could not delete patient.");

                //5. Log In
                System.out.println("Enter email to log in: ");
                String emailLogin = sc.nextLine();
                System.out.print("Enter password to log in: ");
                String pwLogin = sc.nextLine();
                boolean loginOK = connect.sendLogIn(emailLogin, pwLogin);
                if(loginOK) System.out.println("Login successful!");
                else System.out.println("Login failed!.");

                //6. Change password
                System.out.print("Email: ");
                String cpEmail = sc.nextLine();
                System.out.print("Old password: ");
                String oldPw = sc.nextLine();
                System.out.print("New password: ");
                String newPw = sc.nextLine();
                boolean pwChanged = connect.sendChangePassword(cpEmail, oldPw, newPw);
                if(pwChanged) System.out.println("Password changed!");
                else System.out.println("Error changing password.");

                //7. Change email
                System.out.print("Current email: ");
                String oldEmail = sc.nextLine();
                System.out.print("New email: ");
                String newEmail = sc.nextLine();
                boolean emailChanged = connect.sendChangeEmail(oldEmail, newEmail);
                if(emailChanged) System.out.println("Email changed successfully!");
                else System.out.println("Error changing email.");


                //8. Update patient information

                System.out.print("Email: ");
                String upemail = sc.nextLine();
                updatePatient(connect, upemail); */



            //9. BITALINO RECORDING TEST
                    System.out.println("\n--- REAL BITALINO RECORDING ---");
                    System.out.print("Enter patient_id: ");
                    int p_id = Integer.parseInt(sc.nextLine());

                    System.out.print("Type (ECG/EMG/BOTH): ");
                    String type = sc.nextLine().toUpperCase();

                    // Start recording on server
                    int[] channels = connect.startRecording(p_id, type);
                    System.out.println("Recording started in server.");
            // Read server confirmation with recording_id
            String startResp = connect.in.readLine();
            // Example: OK|RECORDING_STARTED|12
            String[] respParts = startResp.split("\\|");
            int recording_id = Integer.parseInt(respParts[2]);
                    // 1. Discover BITalino
                    BITalino bita = new BITalino();
                    String mac = "20:17:11:20:50:77";  // tu MAC real
                    /*
                    Vector<RemoteDevice> devices = bita.findDevices();
                    Thread.sleep(5000); // wait for scan

                    if (devices.size() == 0) {
                        System.out.println("No BITalino found!");
                        return;
                    }
                     */

                    //String mac = devices.firstElement().getBluetoothAddress();
                    //System.out.println("BITalino detected: " + mac);

                    //2. Connect BITalino
                    bita.open(mac,100); //100Hz
                    bita.start(channels); //channels: {0} ECG, {5} EMG, {0,5} BOTH

                    System.out.println("BITalino started recording...");

                    //3. Acquire and send real frames
                    int num_frames = 1000; // 10sec at 100Hz
                    for(int i = 0; i < num_frames; i++) {
                        Frame[] block = bita.read(1);   // 1 sample
                        connect.sendFrames(block, channels);
                    }


                 System.out.println("Recording ID received from server: " + recording_id);

                //4. End recording
                bita.stop();
                bita.close();
                connect.endRecording();


                 System.out.println("Recording completed successfully!");
            //10. VISUALIZE THE RECORDING AFTER SAVING IT
            Double[][] data = connect.requestRecordingData(recording_id, type);
            plotSignalByType(connect, recording_id, type);



                /*try {
                    System.out.println("\n--- VISUALIZATION STEP ---");

                    // Ask the user which recording to plot
                    System.out.print("Enter recording_id to visualize: ");
                    int recId = Integer.parseInt(sc.nextLine());

                    System.out.print("Which channel to plot? (0-5): ");
                    int ch = Integer.parseInt(sc.nextLine());

                    JDBCManager db = new JDBCManager();
                    var conn = db.getConnection();

                    var series = PlotRecordings.loadRecordingSeries(conn, recId, ch);
                    PlotRecordings.showChart(series);

                    System.out.println("Plot displayed!");

                } catch (Exception e) {
                    System.out.println("Error visualizing recording: " + e.getMessage());
                }

                 */

            connect.close();
        } catch (Exception e) {
                System.out.println("ERROR: " + e.getMessage());

        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }//
        private static void updatePatient(Connection_With_Server connect, String email) throws Exception {
                Scanner sc = new Scanner(System.in);
                boolean ok = true;

                while (ok) {
                        System.out.print("Choose the field you want to update:");
                        System.out.println("1. Name:");
                        System.out.println("2. Surname:");
                        System.out.println("3. Phone:");
                        System.out.println("4. Medical History:");
                        System.out.println("5. Date of Birth:");
                        System.out.println("6. Sex:");
                        System.out.println("0. Exit:");

                        int field = Integer.parseInt(sc.nextLine());
                        String value;

                        switch (field) {
                                case 1:
                                        System.out.print("Enter new name:");
                                        value = sc.nextLine();
                                        connect.sendUpdateToServer(email, "name", value);
                                        break;

                                case 2:
                                        System.out.print("Enter new surname:");
                                        value = sc.nextLine();
                                        connect.sendUpdateToServer(email, "surname", value);
                                        break;

                                case 3:
                                        System.out.print("Enter new phone:");
                                        value = sc.nextLine();
                                        connect.sendUpdateToServer(email, "phone", value);
                                        break;

                                case 4:
                                        System.out.print("Enter new medical history:");
                                        value = sc.nextLine();
                                        connect.sendUpdateToServer(email, "medical_history", value);
                                        break;

                                case 5:
                                        System.out.print("Enter new date of birth:");
                                        value = sc.nextLine();
                                        connect.sendUpdateToServer(email, "dob", value);
                                        break;

                                case 6:
                                        System.out.println("Enter new sex (M/F):");
                                        value = sc.nextLine();
                                        connect.sendUpdateToServer(email, "sex", value);
                                        break;

                                case 0:
                                        ok = false;
                                        break;

                                default:
                                        System.out.println("Invalid choice");

                        }
                }

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

        }
}


