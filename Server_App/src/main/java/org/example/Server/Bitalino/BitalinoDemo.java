package org.example.Server.Bitalino;

import javax.bluetooth.RemoteDevice;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BitalinoDemo {
    public static Frame[] frame;

    public static void main(String[] args){
        BITalino bitalino = null;
        try {
            bitalino = new BITalino();
            Vector<RemoteDevice> devices = bitalino.findDevices();
            System.out.println(devices);

            //MAC ADDRESS
            String macAddress = "20:17:11:20:50:77";
            int SamplingRate = 100;
            bitalino.open(macAddress, SamplingRate);

            //Start acquisition on channels A2 and A6
            // Channels A1, A3, A4 --> {0,2,3}
            int[] channelsToAcquire = {1, 5};
            bitalino.start(channelsToAcquire);

            //read 10000 samples
            for (int j = 0; j < 10000000; j++) {

                //Read a block of 100 samples
                frame = bitalino.read(10);

                System.out.println("size block: " + frame.length);

                //Print the samples
                for (int i = 0; i < frame.length; i++) {
                    System.out.println((j * 100 + i) + " seq: " + frame[i].seq + " "
                                    + frame[i].analog[0] + " "
                                    + frame[i].analog[1] + " "
                            //  + frame[i].analog[2] + " "
                            //  + frame[i].analog[3] + " "
                            //  + frame[i].analog[4] + " "
                            //  + frame[i].analog[5]
                    );

                }
            }

            //Stop acquisition
            bitalino.stop();
        }
        catch (BITalinoException ex) {
            Logger.getLogger(BitalinoDemo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Throwable ex) {
            Logger.getLogger(BitalinoDemo.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                //close bluetooth connection
                if (bitalino != null) {
                    bitalino.close();
                }
            } catch (BITalinoException ex) {
                Logger.getLogger(BitalinoDemo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
