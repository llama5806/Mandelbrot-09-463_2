package ru.gr0946x.ui.animation;

import javax.swing.*;

public class KeyFrame {
    private final double xMin;
    private final double xMax;
    private final double yMin;
    private final double yMax;
    private final ImageIcon image;

    public KeyFrame(double xMin, double xMax, double yMin, double yMax, ImageIcon image) {
        this.xMin = xMin;
        this.xMax = xMax;
        this.yMin = yMin;
        this.yMax = yMax;
        this.image = image;
    }

    public double getXMin() {
        return xMin;
    }

    public double getXMax() {
        return xMax;
    }

    public double getYMin() {
        return yMin;
    }

    public double getYMax() {
        return yMax;
    }

    public ImageIcon getImage() {
        return image;
    }
}