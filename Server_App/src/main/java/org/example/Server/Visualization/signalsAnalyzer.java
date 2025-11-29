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

        // 1) Calcular media
        double sum = 0.0;
        for (double v : data) {
            sum += v;
        }
        double mean = sum / data.length;

        // 2) Calcular desviación estándar
        double var = 0.0;
        for (double v : data) {
            double diff = v - mean;
            var += diff * diff;
        }
        var /= data.length;
        double std = Math.sqrt(var);

        // 3) Definir umbral
        double k = 1.0; // si detecta demasiados o muy pocos, se ajusta este valor
        double threshold = mean + k * std;

        // 4) Periodo refractario mínimo entre picos R (por ejemplo 0.25 s)
        int minDistanceSamples = (int) (0.25 * fs); // 250 ms

        int lastPeakIndex = -minDistanceSamples;

        // 5) Buscar máximos locales por encima del umbral
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

    public static String analizeECG(Double[] data, double fs) {
        List<Integer> rPeaks = RPeaksDetector(data, fs);

        if (rPeaks.size() < 2) {
            return "Señal demasiado corta o sin picos R claros para analizar.";
        }

        // Calcular intervalos RR en segundos
        double sumRR = 0.0;
        int countRR = 0;
        for (int i = 1; i < rPeaks.size(); i++) {
            int diffSamples = rPeaks.get(i) - rPeaks.get(i - 1);
            if (diffSamples <= 0) continue;
            double rr = diffSamples / fs; // segundos
            sumRR += rr;
            countRR++;
        }

        if (countRR == 0) {
            return "No se han podido calcular intervalos R-R válidos.";
        }

        double meanRR = sumRR / countRR;      // segundos
        double heartRate = 60.0 / meanRR;     // lpm

        // Formatear con 1 decimal
        String hrStr = String.format(Locale.US, "%.1f", heartRate);

        if (heartRate > 100.0) {
            return "Paciente con posible taquicardia (FC ≈ " + hrStr + " lpm).";
        } else {
            return "Frecuencia cardiaca dentro de rango (FC ≈ " + hrStr + " lpm).";
        }
    }

    public static String analizeECGFromFrames(List<int[]> frames, double fs) {
        if (frames == null || frames.isEmpty()) {
            return "No hay frames para analizar.";
        }

        Double[] data = new Double[frames.size()];
        for (int i = 0; i < frames.size(); i++) {
            data[i] = (double) frames.get(i)[2];
        }

        return analizeECG(data, fs);
    }


}
