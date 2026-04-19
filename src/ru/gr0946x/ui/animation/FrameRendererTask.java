package ru.gr0946x.ui.animation;

import ru.gr0946x.Converter;
import ru.gr0946x.ui.fractals.Mandelbrot;
import ru.gr0946x.ui.painting.FractalPainter;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.Callable;

public class FrameRendererTask implements Callable<BufferedImage> {

    private final FrameState state;
    private final int width;
    private final int height;

    public FrameRendererTask(FrameState state, int width, int height) {
        this.state = state;
        this.width = width;
        this.height = height;
    }

    @Override
    public BufferedImage call() {
        Converter localConv = new Converter(state.xMin(), state.xMax(), state.yMin(), state.yMax());
        FractalPainter localPainter = new FractalPainter(new Mandelbrot(), localConv, value -> {
            if (value == 1.0) return Color.BLACK;
            var r = (float) Math.abs(Math.sin(5 * value));
            var g = (float) Math.abs(Math.cos(8 * value) * Math.sin(3 * value));
            var b = (float) Math.abs((Math.sin(7 * value) + Math.cos(15 * value)) / 2f);
            return new Color(r, g, b);
        });

        return ImageConverter.render(width,height,localPainter);
    }
}