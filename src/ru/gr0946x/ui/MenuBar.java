package ru.gr0946x.ui;

import ru.gr0946x.ui.animation.AnimationWindow;
import javax.swing.*;

import static ru.gr0946x.ui.saveAndOpen.SaveAndOpenActions.openAction;
import static ru.gr0946x.ui.saveAndOpen.SaveAndOpenActions.saveAction;


public class MenuBar extends JMenuBar {

    private MainWindow mainWindow;

    public MenuBar(MainWindow mainWindow) {
        this.mainWindow = mainWindow;

        var saveMenu = new JMenu("Сохранить");
        var saveItem = new JMenuItem("Сохранить как...");
        saveItem.addActionListener(e -> saveAction(mainWindow));
        saveMenu.add(saveItem);
        add(saveMenu);

        var openMenu = new JMenu("Открыть");
        var openItem = new JMenuItem("Открыть .frac...");
        openItem.addActionListener(e -> openAction(mainWindow));
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