package ru.gr0946x.ui;

import ru.gr0946x.ui.animation.AnimationWindow;

import javax.swing.*;

public class MenuBar extends JMenuBar {
    public MenuBar() {
        var saveMenu = new JMenu("Сохранить");
        var saveItem = new JMenuItem("...");
        saveItem.addActionListener(e -> {
        });
        saveMenu.add(saveItem);
        add(saveMenu);

        var openMenu = new JMenu("Открыть");
        var openItem = new JMenuItem("...");
        openItem.addActionListener(e -> {
        });
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
}
