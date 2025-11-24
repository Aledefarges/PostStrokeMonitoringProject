package org.example.Server.IFaces;

import java.util.List;

public interface RecordingFramesManager {
    public void addFrame(int recording_id, int frameIndex, int crc, int seq, int[]analog, int[]digital);
    public void deleteFramesByRecording(int recording_id);
    public List<int[]> getFramesByRecording(int recording_id);

}
