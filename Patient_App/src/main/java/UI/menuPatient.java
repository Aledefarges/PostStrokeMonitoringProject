package UI;

import Bitalino.BITalino;
import Connection.Connection_Patient;

import java.io.IOException;
import java.util.Scanner;


public class menuPatient {

    private static Scanner sc = new Scanner(System.in);
    private static Connection_Patient connect = new Connection_Patient();

    public static void main(String[] args) {

        String ip = "10.60.96.179";
        try {

            /*if (!connect.connection(ip, 9000)) {
                System.out.println("Could not connect to server.");
                return;
            } else System.out.println("Connected to server!");

            int option;
            System.out.println("\n Select an option:");
            System.out.println("1. Log in");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            option = Integer.parseInt(sc.nextLine());
            switch (option) {
                case 1:
                    login();
                    break;
                case 2:
                    register();
                    break;
                case 3:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option!");
                    break;
            }

             */


        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    /*private static void register() throws IOException {
        System.out.print("Name: ");
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

        boolean send_patient = connect.sendPatientToServer(p);
        if (send_patient) System.out.println("Patient saved in server!");
        else System.out.println("Error saving patient.");

    }
    private static void login() throws IOException {
        System.out.println("\n--- Login ---");
        System.out.println("Enter email to log in: ");
        String emailLogin = sc.nextLine();
        System.out.print("Enter password to log in: ");
        String pwLogin = sc.nextLine();
        boolean loginOK = connect.sendLogIn(emailLogin, pwLogin);
        if(loginOK) {
            System.out.println("Login successful!");
            patientMenu(emailLogin);
        }
        else System.out.println("Login failed!.");
    }

     */
    private static void updatePatient(String email) throws IOException {

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
                    connect.sendUpdateToServer("name", value);
                    break;

                case 2:
                    System.out.print("Enter new surname:");
                    value = sc.nextLine();
                    connect.sendUpdateToServer( "surname", value);
                    break;

                case 3:
                    System.out.print("Enter new phone:");
                    value = sc.nextLine();
                    connect.sendUpdateToServer( "phone", value);
                    break;

                case 4:
                    System.out.print("Enter new medical history:");
                    value = sc.nextLine();
                    connect.sendUpdateToServer( "medical_history", value);
                    break;

                case 5:
                    System.out.print("Enter new date of birth:");
                    value = sc.nextLine();
                    connect.sendUpdateToServer("dob", value);
                    break;

                case 6:
                    System.out.println("Enter new sex (M/F):");
                    value = sc.nextLine();
                    connect.sendUpdateToServer("sex", value);
                    break;

                case 0:
                    ok = false;
                    break;

                default:
                    System.out.println("Invalid choice");

            }
        }

    }
    private static void changePassword(String email) throws IOException {;
        System.out.print("Old password: ");
        String oldPw = sc.nextLine();
        System.out.print("New password: ");
        String newPw = sc.nextLine();
        boolean pwChanged = connect.sendChangePassword(oldPw, newPw);
        if(pwChanged) System.out.println("Password changed!");
        else System.out.println("Error changing password.");
    }
    private static void changeEmail() throws IOException {
        System.out.print("Current email: ");
        String oldEmail = sc.nextLine();
        System.out.print("New email: ");
        String newEmail = sc.nextLine();
        boolean emailChanged = connect.sendChangeEmail(oldEmail, newEmail);
        if(emailChanged) System.out.println("Email changed successfully!");
        else System.out.println("Error changing email.");
    }
    private static void deletePatient(String email) throws IOException {
        boolean delete_patient = connect.deletePatientFromServer();
        if(delete_patient) System.out.println("Patient deleted successfully!");
        else System.out.println("Could not delete patient.");
    }
    private static void patientMenu(String email) throws IOException {
        int option;
        System.out.println("Select and option:");
        System.out.println("1. Start recording:");
        System.out.println("2. Change email:");
        System.out.println("3. Change password:");
        System.out.println("4. Update information:");
        System.out.println("5. Delete patient:");
        System.out.println("0. Exit:");
        option = Integer.parseInt(sc.nextLine());
        switch (option) {
            case 1:
                recordingMenu();
                break;
            case 2:
                changeEmail();
                break;
            case 3:
                changePassword(email);
                break;
            case 4:
                updatePatient(email);
                break;
            case 5:
                deletePatient(email);
                break;
            case 6:
                //view patient information
                break;
            case 0:
                System.out.println("Goodbye!");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid option!");
                break;
        }
    }
    private static void recordingMenu() throws IOException {
        System.out.println("\n--- Recording ---");
        //9. BITALINO RECORDING TEST
        System.out.println("\n--- REAL BITALINO RECORDING ---");
        System.out.print("Enter patient_id: ");
        int p_id = Integer.parseInt(sc.nextLine());

        System.out.print("Type (ECG/EMG/BOTH): ");
        String type = sc.nextLine().toUpperCase();

        // Start recording on server
        int[][] channels = connect.startRecording(type);
        System.out.println("Recording started in server.");
        // Read server confirmation with recording_id
        String startResp = connect.in.readLine();
        // Example: OK|RECORDING_STARTED|12
        String[] respParts = startResp.split("\\|");
        int recording_id = Integer.parseInt(respParts[2]);
        // 1. Discover BITalino
        BITalino bita = new BITalino();
        //String mac = "20:17:11:20:50:77";
        String mac = "98:D3:51:FD:9C:72";// tu MAC real
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
                    /*bita.open(mac,100); //100Hz
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

                JDBCManager db = new JDBCManager();
                var conn = db.getConnection();

                System.out.println("Opening plots...");
                for (int ch : channels) {
                var series = PlotRecordings.loadRecordingSeries(conn, recording_id, ch);
                PlotRecordings.showChart(series);
                }

            System.out.println("Plots displayed!");
                */

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
    }

}
