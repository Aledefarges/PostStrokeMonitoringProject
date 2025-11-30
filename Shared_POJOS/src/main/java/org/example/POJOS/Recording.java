package org.example.POJOS;

import java.io.Serializable;
import java.time.LocalDateTime;


public class Recording implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private LocalDateTime dateRecording;
    private final int max_duration = 60;
    private Type type;
    private int patient_id;

    public Recording(LocalDateTime dateRecording, Type type, int patient_id) {
        this.dateRecording = dateRecording;
        this.type = type;
        this.patient_id = patient_id;
    }


    public int getId() {
        return id;
    }

    public LocalDateTime getDateRecording() {
        return dateRecording;
    }

    public Type getType() {
        return type;
    }

    public void setId(int id) {
        this.id = id;
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
        ECG,EMG,BOTH;
    }
}

