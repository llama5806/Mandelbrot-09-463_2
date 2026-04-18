package ru.gr0946x.ui.animation;

import ru.gr0946x.Converter;
import ru.gr0946x.ui.SelectablePanel;
import ru.gr0946x.ui.fractals.Fractal;
import ru.gr0946x.ui.fractals.Mandelbrot;
import ru.gr0946x.ui.painting.FractalPainter;
import ru.gr0946x.ui.painting.Painter;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import static java.lang.Math.*;

public class AnimationWindow extends JFrame {

    private final SelectablePanel mainPanel;
    private final Painter painter;
    private final Fractal mandelbrot;
    private final Converter conv;

    private final DefaultListModel<KeyFrame> listModel;
    private final JList<KeyFrame> framesList;
    private final JButton btnAddFrame;
    private final JButton btnRemoveFrame;
    private final JButton btnCreateFrame;

    public AnimationWindow() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(1020, 650));

        mandelbrot = new Mandelbrot();
        conv = new Converter(-2.0, 1.0, -1.0, 1.0);
        painter = new FractalPainter(mandelbrot, conv, (value) -> {
            if (value == 1.0) return Color.BLACK;
            var r = (float) abs(sin(5 * value));
            var g = (float) abs(cos(8 * value) * sin(3 * value));
            var b = (float) abs((sin(7 * value) + cos(15 * value)) / 2f);
            return new Color(r, g, b);
        });

        mainPanel = new SelectablePanel(painter);
        mainPanel.setBackground(Color.WHITE);
        mainPanel.addSelectListener((r) -> {
            var xMin = conv.xScr2Crt(r.x);
            var xMax = conv.xScr2Crt(r.x + r.width);
            var yMin = conv.yScr2Crt(r.y + r.height);
            var yMax = conv.yScr2Crt(r.y);
            conv.setXShape(xMin, xMax);
            conv.setYShape(yMin, yMax);
            mainPanel.repaint();
        });

        listModel = new DefaultListModel<>();
        framesList = new JList<>(listModel);
        framesList.setCellRenderer(new KeyFrameRenderer());
        framesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        btnAddFrame = new JButton("+");
        btnAddFrame.addActionListener(e -> {
            ImageIcon image = createImage();
            KeyFrame frame = new KeyFrame(
                    conv.getXMin(), conv.getXMax(),
                    conv.getYMin(), conv.getYMax(),
                    image
            );
            listModel.addElement(frame);
        });

        btnRemoveFrame = new JButton("-");
        btnRemoveFrame.addActionListener(e -> {
            int selectedIndex = framesList.getSelectedIndex();
            if (selectedIndex != -1) {
                listModel.remove(selectedIndex);
            }
        });

        btnCreateFrame = new JButton("Создать видео");

        setContent();
    }

    private ImageIcon createImage() {
        int width = 160;
        int height = 120;
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = img.getGraphics();

        int oldW = painter.getWidth();
        int oldH = painter.getHeight();

        painter.setWidth(width);
        painter.setHeight(height);
        painter.paint(g);

        painter.setWidth(oldW);
        painter.setHeight(oldH);

        return new ImageIcon(img);
    }

    private void setContent() {
        var gl = new GroupLayout(getContentPane());
        setLayout(gl);

        JScrollPane scrollPane = new JScrollPane(framesList);

        gl.setVerticalGroup(gl.createSequentialGroup()
                .addGap(8)
                .addGroup(gl.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(mainPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(gl.createSequentialGroup()
                                .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(8)
                                .addComponent(btnAddFrame)
                                .addGap(8)
                                .addComponent(btnRemoveFrame)
                        )
                )
                .addGap(8)
        );

        gl.setHorizontalGroup(gl.createSequentialGroup()
                .addGap(8)
                .addComponent(mainPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(8)
                .addGroup(gl.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(scrollPane, 200, 200, 200)
                        .addComponent(btnAddFrame, 200, 200, 200)
                        .addComponent(btnRemoveFrame, 200, 200, 200)
                )
                .addGap(8)
        );
    }
}