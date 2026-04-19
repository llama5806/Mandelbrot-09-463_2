package ru.gr0946x.ui.animation;

import ru.gr0946x.ui.painting.Painter;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageConverter {

    public static BufferedImage render(int width, int height, Painter painter) {
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = img.getGraphics();

        int oldW = painter.getWidth();
        int oldH = painter.getHeight();

        painter.setWidth(width);
        painter.setHeight(height);

        painter.paint(g);

        g.dispose();

        painter.setWidth(oldW);
        painter.setHeight(oldH);

        return img;
    }
}
