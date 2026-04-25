package com.mtrilogic.demos.springpaneldemo;

import com.mtrilogic.desktop.abstracts.SpringPanel;

import javax.swing.*;
import java.awt.*;

public class BaselineDemo extends SpringPanel {

    public BaselineDemo() {
        JPanel northPanel = createNorthPanel();
        JPanel baselinePanel = createBaselinePanel();
        // Position north panel at top, full width
        with(northPanel).north(0).west(0).east(0).height(130).apply();
        // Position baseline panel below north panel, remaining space
        with(baselinePanel).north(northPanel, 0).west(0).east(0).south(0).apply();
    }

    static void main() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("SpringPanel2: NORTH vs BASELINE");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(500, 300);

            BaselineDemo demo = new BaselineDemo();
            frame.add(demo, BorderLayout.CENTER);

            frame.setLocationRelativeTo(null); // Center on screen
            frame.setVisible(true);
        });
    }

    private static JPanel createNorthPanel() {
        SpringPanel panel = new SpringPanelImpl();
        panel.setBorder(BorderFactory.createTitledBorder("NORTH (visually misaligned)"));

        JLabel label = new JLabel("Name:");
        JTextField field = new JTextField("Example text");
        field.setPreferredSize(new Dimension(200, 40)); // Make field taller

        // Position label at top-left
        panel.with(label)
                .north(10)
                .west(10)
                .apply();

        // Position field using NORTH alignment (misaligned with label text)
        panel.with(field)
                .north(10)   // Aligns to top edge, not baseline
                .west(label, 10)
                .apply();

        return panel;
    }

    private static JPanel createBaselinePanel() {
        SpringPanel panel = new SpringPanelImpl();
        panel.setBorder(BorderFactory.createTitledBorder("BASELINE (correct alignment)"));

        JLabel label = new JLabel("Name:");
        JTextField field = new JTextField("Example text");
        field.setPreferredSize(new Dimension(200, 40)); // Same size as other field

        // Position label at top-left
        panel.with(label)
                .north(10)
                .west(10)
                .apply();

        // Position field using BASELINE alignment (aligned with label text)
        panel.with(field)
                .baseline(label, 0) // Aligns text baselines
                .west(label, 10)
                .apply();

        return panel;
    }

    // Concrete implementation of SpringPanel for demo
    private static class SpringPanelImpl extends SpringPanel {

    }
}
