package Bitalino;
import org.example.POJOS.Recording;

import java.util.*;
import java.time.LocalDate;

public class RecordingBuilder {
    private BITalino bitalino;

    public RecordingBuilder(){
        this.bitalino = new BITalino();
    }

    public Recording createRecording(String macAddress, int samplingRate, int[] channels, int totalSamples, int recordingID, int patientId, Recording.Type type) throws Throwable {
        //1. Connect to Bitalino
        bitalino.open(macAddress, samplingRate);
        bitalino.start(channels);

        List<int[]> frameList = new ArrayList<>();
        int blockSize = 10; //read 10 frames per chunk

        // 2. Read frames in blocks
        for(int i = 0; i < totalSamples; i += blockSize){
            Frame[] frames = bitalino.read(blockSize);

            for(Frame f : frames){
                int[] sample = convertToIntArray(f);
                frameList.add(sample);
            }
        }

        // 3. Stop and close BITalino
        bitalino.stop();
        bitalino.close();

        //4. Build and return the Recording object
        return new Recording(LocalDate.now(),type, patientId);
    }

    private int[] convertToIntArray(Frame f){
        int[] data = new int[1+6+4];
        data[0] = f.seq;

        System.arraycopy(f.analog, 0, data, 1, 6);
        System.arraycopy(f.digital, 0, data, 7, 4);

        return data;
    }
}
