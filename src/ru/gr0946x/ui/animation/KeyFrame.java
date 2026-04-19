package ru.gr0946x.ui.animation;

import javax.swing.*;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KeyFrame keyFrame = (KeyFrame) o;
        return Double.compare(keyFrame.xMin, xMin) == 0 &&
                Double.compare(keyFrame.xMax, xMax) == 0 &&
                Double.compare(keyFrame.yMin, yMin) == 0 &&
                Double.compare(keyFrame.yMax, yMax) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(xMin, xMax, yMin, yMax);
    }
}