package ru.gr0946x.ui.fractals;

public class DynamicIterations {
    private static final int BASE_ITERATIONS = 50;
    private static final double ZOOM_COEFFICIENT = 0.8;
    private static final int MAX_LIMIT = 2000;

    public static int getIterations(double zoomLevel) {
        if (zoomLevel <= 1.0) {
            return BASE_ITERATIONS;
        }
        double logZoom = Math.log10(zoomLevel);
        int iterations = BASE_ITERATIONS + (int)(ZOOM_COEFFICIENT * logZoom * 100);
        return Math.min(iterations, MAX_LIMIT);
    }
}