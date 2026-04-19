package ru.gr0946x.ui.animation;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class VideoExportManager {

    public static void export(JFrame parent, DefaultListModel<KeyFrame> listModel, int duration, int panelWidth, int panelHeight) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Сохранить видео");
        if (fileChooser.showSaveDialog(parent) != JFileChooser.APPROVE_OPTION) return;

        File file = fileChooser.getSelectedFile();
        if (!file.getName().endsWith(".mp4")) {
            file = new File(file.getAbsolutePath() + ".mp4");
        }
        File finalFile = file;

        int fps = 15;
        List<FrameState> states = FrameInterpolator.generateStates(Collections.list(listModel.elements()), duration, fps);
        int width = (panelWidth / 2) * 2;
        int height = (panelHeight / 2) * 2;

        JDialog progressDialog = new JDialog(parent, "Создание видео", true);
        JProgressBar progressBar = new JProgressBar(0, states.size());
        progressBar.setStringPainted(true);
        progressDialog.add(BorderLayout.CENTER, progressBar);
        progressDialog.setSize(300, 75);
        progressDialog.setLocationRelativeTo(parent);

        SwingWorker<Void, Integer> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                int cores = Runtime.getRuntime().availableProcessors();
                ExecutorService executor = Executors.newFixedThreadPool(cores);

                try (VideoWriter writer = new VideoWriter(finalFile, fps)) {
                    List<Future<BufferedImage>> futures = new ArrayList<>();

                    for (FrameState state : states) {
                        futures.add(executor.submit(new FrameRendererTask(state, width, height)));
                    }

                    int count = 0;
                    for (Future<BufferedImage> future : futures) {
                        BufferedImage frame = future.get();
                        writer.addFrame(frame);
                        publish(++count);
                    }
                    writer.finish();
                } finally {
                    executor.shutdown();
                }
                return null;
            }

            @Override
            protected void process(List<Integer> chunks) {
                int latest = chunks.get(chunks.size() - 1);
                progressBar.setValue(latest);
                progressBar.setString("Кадров: " + latest + " / " + states.size());
            }

            @Override
            protected void done() {
                progressDialog.dispose();
                try {
                    get();
                    JOptionPane.showMessageDialog(parent, "Видео успешно создано!");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(parent, "Ошибка: " + ex.getCause().getMessage());
                }
            }
        };

        worker.execute();
        progressDialog.setVisible(true);
    }
}