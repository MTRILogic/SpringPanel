package com.mtrilogic.demos.springpaneldemo;

import com.mtrilogic.desktop.abstracts.SpringPanel;

import javax.swing.*;
import java.awt.*;

public class SpringPanelDemo extends SpringPanel {

    public SpringPanelDemo() {
        // Create avatar panel (absolute positioned)
        JPanel avatar = new JPanel();
        avatar.setBackground(Color.WHITE);
        with(avatar)
                .x(10)
                .y(10)
                .width(80)
                .height(80)
                .apply();

        // Create header panel (top, full width)
        JPanel header = new JPanel();
        header.setBackground(Color.RED);
        with(header)
                .north(0)
                .west(0)
                .east(0)
                .height(100)
                .apply();

        // Declare remaining panels before positioning
        JPanel content = new JPanel();
        content.setBackground(Color.YELLOW);
        JPanel footer = new JPanel();
        JPanel sidebar = new JPanel();
        
        // Position content panel (main area)
        with(content)
                .north(header, 0)
                .west(0)
                .east(sidebar, 0)
                .south(footer, 0)
                .apply();

        // Position sidebar (right side)
        sidebar.setBackground(Color.GREEN);
        with(sidebar)
                .north(header, 0)
                .width(150)
                .east(0)
                .south(footer, 0)
                .apply();

        // Position footer (bottom, full width)
        footer.setBackground(Color.BLUE);
        with(footer)
                .south(0)
                .west(0)
                .east(0)
                .height(50)
                .apply();
    }

    static void main() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 400);
            frame.setLocationRelativeTo(null); // Center on screen
            frame.getContentPane().add(new SpringPanelDemo());
            frame.setVisible(true);
        });
    }
}
