package ru.gr0946x.ui.animation;

import javax.swing.*;
import java.awt.*;

public class KeyFrameRenderer extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        if (value instanceof KeyFrame) {
            KeyFrame frame = (KeyFrame) value;
            label.setIcon(frame.getImage());
            label.setText(null);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setVerticalAlignment(SwingConstants.CENTER);
            label.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        }
        return label;
    }
}