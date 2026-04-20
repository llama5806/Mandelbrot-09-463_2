package ru.gr0946x.ui;

import ru.gr0946x.ui.animation.AnimationWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class MenuBar extends JMenuBar {

    private MainWindow mainWindow;

    public MenuBar(MainWindow mainWindow) {
        this.mainWindow = mainWindow;

        var saveMenu = new JMenu("Сохранить");
        var saveItem = new JMenuItem("Сохранить как...");
        saveItem.addActionListener(e -> saveFractal());
        saveMenu.add(saveItem);
        add(saveMenu);

        var openMenu = new JMenu("Открыть");
        var openItem = new JMenuItem("Открыть .frac...");
        openItem.addActionListener(e -> openFractal());
        openMenu.add(openItem);
        add(openMenu);

        var videoMenu = new JMenu("Экскурсия по фракталу");
        var videoItem = new JMenuItem("Создать анимацию");
        videoItem.addActionListener(e -> {
            AnimationWindow animationWindow = new AnimationWindow();
            animationWindow.setVisible(true);
        });
        videoMenu.add(videoItem);
        add(videoMenu);
    }

    private void saveFractal() {
        if (mainWindow == null) return;

        boolean success = FractalSaver.saveFractal(
                mainWindow,
                mainWindow.getPainter(),
                mainWindow.getConverter()
        );

        if (success) {
            JOptionPane.showMessageDialog(mainWindow,
                    "Фрактал успешно сохранён!", "Успех", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void openFractal() {
        if (mainWindow == null) return;

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Открыть файл фрактала");
        fileChooser.addChoosableFileFilter(
                new javax.swing.filechooser.FileNameExtensionFilter("Фрактал (.frac)", "frac"));
        fileChooser.setFileFilter(
                new javax.swing.filechooser.FileNameExtensionFilter("Фрактал (.frac)", "frac"));

        if (fileChooser.showOpenDialog(mainWindow) == JFileChooser.APPROVE_OPTION) {
            try {
                var newConv = FractalSaver.loadFractalParams(fileChooser.getSelectedFile());
                mainWindow.updateConverter(newConv);
                mainWindow.repaint();
                JOptionPane.showMessageDialog(mainWindow,
                        "Фрактал успешно загружен!", "Успех", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(mainWindow,
                        "Ошибка при загрузке: " + ex.getMessage(),
                        "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}