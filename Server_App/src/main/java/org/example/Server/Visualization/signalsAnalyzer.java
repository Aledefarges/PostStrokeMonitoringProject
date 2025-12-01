package org.example.Server.Visualization;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class signalsAnalyzer {
    public static List<Integer> RPeaksDetector(Double[] data, double fs) {
        List<Integer> rPeaks = new ArrayList<>();

        if (data == null || data.length == 0) {
            return rPeaks;
        }

        // 1) Calculate average
        double sum = 0.0;
        for (double v : data) {
            sum += v;
        }
        double mean = sum / data.length;

        // 2) Calculate standard deviation
        double var = 0.0;
        for (double v : data) {
            double diff = v - mean;
            var += diff * diff;
        }
        var /= data.length;
        double std = Math.sqrt(var);

        // 3) Define threshold
        double k = 1.0;
        double threshold = mean + k * std;

        // 4) Minimum refractory period between Rs
        int minDistanceSamples = (int) (0.25 * fs); // 250 ms

        int lastPeakIndex = -minDistanceSamples;

        // 5) Look for local minimums over the threshold
        for (int i = 1; i < data.length - 1; i++) {
            double prev = data[i - 1];
            double curr = data[i];
            double next = data[i + 1];

            boolean esMaximoLocal = (curr > prev) && (curr > next);
            boolean superaUmbral = curr > threshold;
            boolean lejosDeUltimoPico = (i - lastPeakIndex) >= minDistanceSamples;

            if (esMaximoLocal && superaUmbral && lejosDeUltimoPico) {
                rPeaks.add(i);
                lastPeakIndex = i;
            }
        }

        return rPeaks;
    }

    public static String analyzeECG(Double[] data, double fs) {
        List<Integer> rPeaks = RPeaksDetector(data, fs);

        if (rPeaks.size() < 2) {
            return "The signal is too short or has no clear R peaks. It cannot be analysed.";
        }

        // Calculate RR int in seconds
        double sumRR = 0.0;
        int countRR = 0;
        for (int i = 1; i < rPeaks.size(); i++) {
            int diffSamples = rPeaks.get(i) - rPeaks.get(i - 1);
            if (diffSamples <= 0) continue;
            double rr = diffSamples / fs; // secs
            sumRR += rr;
            countRR++;
        }

        if (countRR == 0) {
            return "Valid R-R intervals could not be calculated.";
        }

        double meanRR = sumRR / countRR;      // seconds
        double heartRate = 60.0 / meanRR;     // bpm

        // Only 1 decimal
        String hrStr = String.format(Locale.US, "%.1f", heartRate);

        if (heartRate > 100.0) {
            return "Patient may have a possible tachycardia (HR ≈ " + hrStr + " bpm).";
        }else if (heartRate < 60.0) {
            return "Patient may have a possible bradycardia (HR ≈ " + hrStr + " bpm).";
        }else {
            return "Heart rate within range (HR ≈ " + hrStr + " bpm).";
        }

    }

    public static String analyzeECGFromFrames(List<int[]> frames, double fs) {
        if (frames == null || frames.isEmpty()) {
            return "There are no frames to analyze.";
        }

        Double[] data = new Double[frames.size()];
        for (int i = 0; i < frames.size(); i++) {
            data[i] = (double) frames.get(i)[2];
        }

        return analyzeECG(data, fs);
    }


}
