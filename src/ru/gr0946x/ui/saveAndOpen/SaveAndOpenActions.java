package ru.gr0946x.ui.saveAndOpen;

import ru.gr0946x.ui.MainWindow;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

public class SaveAndOpenActions {
    public static void saveAction(MainWindow mainWindow) {
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

    public static void openAction(MainWindow mainWindow) {
        if (mainWindow == null) return;

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Открыть файл фрактала");

        FileNameExtensionFilter filter = FractalSaver.fracFilter();
        fileChooser.addChoosableFileFilter(filter);
        fileChooser.setFileFilter(filter);

        if (fileChooser.showOpenDialog(mainWindow) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            if (!selectedFile.getName().toLowerCase().endsWith(".frac")) {
                JOptionPane.showMessageDialog(mainWindow,
                        "Для восстановления фрактала необходимо выбрать файл параметров (.frac)",
                        "Неверный формат файла", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                var newConv = FractalSaver.loadFractalParams(selectedFile);
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
