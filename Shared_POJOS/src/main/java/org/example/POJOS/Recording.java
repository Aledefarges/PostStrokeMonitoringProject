package org.example.POJOS;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

//bA.KNZ
public class Recording implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private LocalDate dateRecording;
    private final int max_duration = 60;
    private Type type;
    private int patient_id;
    private List<int[]> frames;

    public Recording(int id, LocalDate dateRecording, Type type, int patient_id) {
        this.id = id;
        this.dateRecording = dateRecording;
        this.type = type;
        this.patient_id = patient_id;
    }

    public int getId() {
        return id;
    }

    public LocalDate getDateRecording() {
        return dateRecording;
    }

    public int getMax_duration() {
        return max_duration;
    }

    public Type getType() {
        return type;
    }
    public List<int[]> getFrames(){
        return frames;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDateRecording(LocalDate dateRecording) {
        this.dateRecording = dateRecording;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setFrames(List<int[]> frames){
        this.frames = frames;
    }


    public int getPatient_id() {return patient_id;}

    public void setPatient_id(int patient_id) {this.patient_id = patient_id;}

    @Override
    public String toString() {
        return "Recording{" +
                "id=" + id +
                ", dateRecording=" + dateRecording +
                ", max_duration=" + max_duration +
                ", type=" + type +
                ", patient_id=" + patient_id +
                '}';
    }

    public enum Type{
        ECG,EMG;
    }
}

