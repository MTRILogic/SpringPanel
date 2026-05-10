package com.mtrilogic.desktop.abstracts;

import javax.swing.*;

/**
 * A fluent API wrapper for {@link SpringLayout} that simplifies complex UI layouts
 * with intuitive method chaining.
 *
 * <p>SpringPanel extends {@link JPanel} and automatically sets its layout to
 * {@link SpringLayout}. Components are configured via a {@link ConstraintsBuilder}
 * returned by the {@link #with(JComponent)} method.</p>
 *
 * <p>Basic usage:</p>
 * <pre>{@code
 * public class MyPanel extends SpringPanel {
 *     public MyPanel() {
 *         JButton button = new JButton("Click Me");
 *         with(button)
 *             .x(10)
 *             .y(10)
 *             .width(100)
 *             .height(30);
 *     }
 * }
 * }</pre>
 *
 * @see ConstraintsBuilder
 */
@SuppressWarnings({"unused", "UnusedReturnValue"})
public class SpringPanel extends JPanel {

    /**
     * Creates a new SpringPanel with {@link SpringLayout} pre-configured.
     */
    public SpringPanel() {
        setLayout(new SpringLayout());
    }

    /**
     * Selects a component for constraint configuration.
     *
     * <p>If the component has not been added to this panel yet, it is automatically
     * added. All subsequent chained calls on the returned {@link ConstraintsBuilder}
     * will apply to this component.</p>
     *
     * @param component the component to configure; must not be {@code null}
     * @return a {@link ConstraintsBuilder} for configuring the component
     * @throws IllegalArgumentException if {@code component} is {@code null}
     * @throws IllegalStateException    if the component already belongs to another container
     */
    public ConstraintsBuilder with(JComponent component) {
        if (component == null) {
            throw new IllegalArgumentException("Component cannot be null");
        }
        if (component.getParent() != null && component.getParent() != this) {
            throw new IllegalStateException("Component already belongs to another container.");
        }
        if (component.getParent() == null) {
            add(component);
        }
        return new ConstraintsBuilder(component);
    }

    /**
     * Returns the raw {@link SpringLayout.Constraints} for the given component.
     *
     * @param component the component whose constraints are requested
     * @return the current constraints of the component
     */
    public SpringLayout.Constraints getConstraints(JComponent component) {
        SpringLayout layout = (SpringLayout) getLayout();
        return layout.getConstraints(component);
    }

    // =========================
    // BUILDER
    // =========================

    /**
     * Fluent builder for configuring {@link SpringLayout} constraints on a component.
     *
     * <p>All methods return {@code this} to enable chaining. Constraints are applied
     * immediately as each method is called.</p>
     */
    public class ConstraintsBuilder {
        private final SpringLayout.Constraints constraints;
        private final JComponent component;
        private final SpringLayout layout;

        ConstraintsBuilder(JComponent component) {
            this.component = component;
            layout = (SpringLayout) getLayout();
            constraints = layout.getConstraints(component);
        }

        // =========================
        // POSITION
        // =========================

        /**
         * Anchors the {@code NORTH} edge of the component to the {@code SOUTH} edge
         * of another component with the specified padding.
         *
         * @param other the reference component; must not be {@code null}
         * @param pad   the padding in pixels; must be {@code >= 0}
         * @return this builder
         */
        public ConstraintsBuilder north(JComponent other, int pad) {
            requireNonNullComponent(other);
            validatePad(pad, "NORTH");
            layout.putConstraint(SpringLayout.NORTH, component, pad, SpringLayout.SOUTH, other);
            return this;
        }

        /**
         * Anchors the {@code NORTH} edge of the component to the {@code NORTH} edge
         * of this panel with the specified padding.
         *
         * @param pad the padding in pixels; must be {@code >= 0}
         * @return this builder
         */
        public ConstraintsBuilder north(int pad) {
            validatePad(pad, "NORTH");
            layout.putConstraint(SpringLayout.NORTH, component, pad, SpringLayout.NORTH, SpringPanel.this);
            return this;
        }

        /**
         * Anchors the {@code WEST} edge of the component to the {@code EAST} edge
         * of another component with the specified padding.
         *
         * @param other the reference component; must not be {@code null}
         * @param pad   the padding in pixels; must be {@code >= 0}
         * @return this builder
         */
        public ConstraintsBuilder west(JComponent other, int pad) {
            requireNonNullComponent(other);
            validatePad(pad, "WEST");
            layout.putConstraint(SpringLayout.WEST, component, pad, SpringLayout.EAST, other);
            return this;
        }

        /**
         * Anchors the {@code WEST} edge of the component to the {@code WEST} edge
         * of this panel with the specified padding.
         *
         * @param pad the padding in pixels; must be {@code >= 0}
         * @return this builder
         */
        public ConstraintsBuilder west(int pad) {
            validatePad(pad, "WEST");
            layout.putConstraint(SpringLayout.WEST, component, pad, SpringLayout.WEST, SpringPanel.this);
            return this;
        }

        /**
         * Anchors the {@code EAST} edge of the component to the {@code WEST} edge
         * of another component with the specified padding.
         *
         * @param other the reference component; must not be {@code null}
         * @param pad   the padding in pixels; must be {@code >= 0}
         * @return this builder
         */
        public ConstraintsBuilder east(JComponent other, int pad) {
            requireNonNullComponent(other);
            validatePad(pad, "EAST");
            layout.putConstraint(SpringLayout.EAST, component, pad, SpringLayout.WEST, other);
            return this;
        }

        /**
         * Anchors the {@code EAST} edge of the component to the {@code EAST} edge
         * of this panel with the specified padding.
         *
         * @param pad the padding in pixels; must be {@code >= 0}
         * @return this builder
         */
        public ConstraintsBuilder east(int pad) {
            validatePad(pad, "EAST");
            layout.putConstraint(SpringLayout.EAST, component, pad, SpringLayout.EAST, SpringPanel.this);
            return this;
        }

        /**
         * Anchors the {@code SOUTH} edge of the component to the {@code NORTH} edge
         * of another component with the specified padding.
         *
         * @param other the reference component; must not be {@code null}
         * @param pad   the padding in pixels; must be {@code >= 0}
         * @return this builder
         */
        public ConstraintsBuilder south(JComponent other, int pad) {
            requireNonNullComponent(other);
            validatePad(pad, "SOUTH");
            layout.putConstraint(SpringLayout.SOUTH, component, pad, SpringLayout.NORTH, other);
            return this;
        }

        /**
         * Anchors the {@code SOUTH} edge of the component to the {@code SOUTH} edge
         * of this panel with the specified padding.
         *
         * @param pad the padding in pixels; must be {@code >= 0}
         * @return this builder
         */
        public ConstraintsBuilder south(int pad) {
            validatePad(pad, "SOUTH");
            layout.putConstraint(SpringLayout.SOUTH, component, pad, SpringLayout.SOUTH, SpringPanel.this);
            return this;
        }

        /**
         * Stretches the component vertically to fill the panel with the given padding.
         *
         * @param pad the padding from top and bottom; must be {@code >= 0}
         * @return this builder
         */
        public ConstraintsBuilder vFill(int pad) {
            validatePad(pad, "V_FILL");
            layout.putConstraint(SpringLayout.NORTH, component, pad, SpringLayout.NORTH, SpringPanel.this);
            layout.putConstraint(SpringLayout.SOUTH, component, -pad, SpringLayout.SOUTH, SpringPanel.this);
            return this;
        }

        /**
         * Stretches the component horizontally to fill the panel with the given padding.
         *
         * @param pad the padding from left and right; must be {@code >= 0}
         * @return this builder
         */
        public ConstraintsBuilder hFill(int pad) {
            validatePad(pad, "H_FILL");
            layout.putConstraint(SpringLayout.WEST, component, pad, SpringLayout.WEST, SpringPanel.this);
            layout.putConstraint(SpringLayout.EAST, component, -pad, SpringLayout.EAST, SpringPanel.this);
            return this;
        }

        /**
         * Stretches the component in both directions to fill the panel with the given padding.
         *
         * @param pad the padding on all sides; must be {@code >= 0}
         * @return this builder
         */
        public ConstraintsBuilder fill(int pad) {
            validatePad(pad, "FILL");
            layout.putConstraint(SpringLayout.NORTH, component, pad, SpringLayout.NORTH, SpringPanel.this);
            layout.putConstraint(SpringLayout.SOUTH, component, -pad, SpringLayout.SOUTH, SpringPanel.this);
            layout.putConstraint(SpringLayout.WEST, component, pad, SpringLayout.WEST, SpringPanel.this);
            layout.putConstraint(SpringLayout.EAST, component, -pad, SpringLayout.EAST, SpringPanel.this);
            return this;
        }

        /**
         * Centers the component horizontally relative to another component.
         *
         * @param other the reference component; must not be {@code null}
         * @param pad   the offset in pixels
         * @return this builder
         */
        public ConstraintsBuilder hCenter(JComponent other, int pad) {
            requireNonNullComponent(other);
            layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, component, pad, SpringLayout.HORIZONTAL_CENTER, other);
            return this;
        }

        /**
         * Centers the component horizontally within this panel.
         *
         * @param pad the offset in pixels
         * @return this builder
         */
        public ConstraintsBuilder hCenter(int pad) {
            layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, component, pad, SpringLayout.HORIZONTAL_CENTER, SpringPanel.this);
            return this;
        }

        /**
         * Centers the component vertically relative to another component.
         *
         * @param other the reference component; must not be {@code null}
         * @param pad   the offset in pixels
         * @return this builder
         */
        public ConstraintsBuilder vCenter(JComponent other, int pad) {
            requireNonNullComponent(other);
            layout.putConstraint(SpringLayout.VERTICAL_CENTER, component, pad, SpringLayout.VERTICAL_CENTER, other);
            return this;
        }

        /**
         * Centers the component vertically within this panel.
         *
         * @param pad the offset in pixels
         * @return this builder
         */
        public ConstraintsBuilder vCenter(int pad) {
            layout.putConstraint(SpringLayout.VERTICAL_CENTER, component, pad, SpringLayout.VERTICAL_CENTER, SpringPanel.this);
            return this;
        }

        /**
         * Centers the component both horizontally and vertically within this panel.
         *
         * @param pad the offset in pixels
         * @return this builder
         */
        public ConstraintsBuilder center(int pad) {
            layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, component, pad, SpringLayout.HORIZONTAL_CENTER, SpringPanel.this);
            layout.putConstraint(SpringLayout.VERTICAL_CENTER, component, pad, SpringLayout.VERTICAL_CENTER, SpringPanel.this);
            return this;
        }

        /**
         * Aligns the text baseline of the component with the baseline of another component.
         *
         * @param other the reference component; must not be {@code null}
         * @return this builder
         */
        public ConstraintsBuilder baseline(JComponent other) {
            requireNonNullComponent(other);
            layout.putConstraint(SpringLayout.BASELINE, component, 0, SpringLayout.BASELINE, other);
            return this;
        }

        // =========================
        // SIZE
        // =========================

        /**
         * Sets a fixed width for the component.
         *
         * @param value the width in pixels
         * @return this builder
         */
        public ConstraintsBuilder width(int value) {
            constraints.setWidth(Spring.constant(value));
            return this;
        }

        /**
         * Sets the component width as a scaled factor of this panel's width.
         *
         * @param factor the scale factor (e.g., {@code 0.5f} for half width)
         * @return this builder
         */
        public ConstraintsBuilder width(float factor) {
            constraints.setWidth(Spring.scale(getWidthSpring(), factor));
            return this;
        }

        /**
         * Sets a fixed height for the component.
         *
         * @param value the height in pixels
         * @return this builder
         */
        public ConstraintsBuilder height(int value) {
            constraints.setHeight(Spring.constant(value));
            return this;
        }

        /**
         * Sets the component height as a scaled factor of this panel's height.
         *
         * @param factor the scale factor (e.g., {@code 0.5f} for half height)
         * @return this builder
         */
        public ConstraintsBuilder height(float factor) {
            constraints.setHeight(Spring.scale(getHeightSpring(), factor));
            return this;
        }

        /**
         * Sets both width and height to the same fixed value, creating a square.
         *
         * @param side the size in pixels
         * @return this builder
         */
        public ConstraintsBuilder square(int side) {
            constraints.setWidth(Spring.constant(side));
            constraints.setHeight(Spring.constant(side));
            return this;
        }

        /**
         * Sets fixed width and height in a single call.
         *
         * @param width  the width in pixels
         * @param height the height in pixels
         * @return this builder
         */
        public ConstraintsBuilder rectangle(int width, int height) {
            constraints.setWidth(Spring.constant(width));
            constraints.setHeight(Spring.constant(height));
            return this;
        }

        // =========================
        // ABSOLUTE POSITION
        // =========================

        /**
         * Sets the absolute X coordinate of the component.
         *
         * @param value the X coordinate in pixels
         * @return this builder
         */
        public ConstraintsBuilder x(int value) {
            constraints.setX(Spring.constant(value));
            return this;
        }

        /**
         * Sets the X coordinate as a scaled factor of this panel's width.
         *
         * @param factor the scale factor
         * @return this builder
         */
        public ConstraintsBuilder x(float factor) {
            constraints.setX(Spring.scale(getWidthSpring(), factor));
            return this;
        }

        /**
         * Sets the absolute Y coordinate of the component.
         *
         * @param value the Y coordinate in pixels
         * @return this builder
         */
        public ConstraintsBuilder y(int value) {
            constraints.setY(Spring.constant(value));
            return this;
        }

        /**
         * Sets the Y coordinate as a scaled factor of this panel's height.
         *
         * @param factor the scale factor
         * @return this builder
         */
        public ConstraintsBuilder y(float factor) {
            constraints.setY(Spring.scale(getHeightSpring(), factor));
            return this;
        }

        /**
         * Sets the absolute position of the component.
         *
         * @param x the X coordinate in pixels
         * @param y the Y coordinate in pixels
         * @return this builder
         */
        public ConstraintsBuilder point(int x, int y) {
            constraints.setX(Spring.constant(x));
            constraints.setY(Spring.constant(y));
            return this;
        }

        // =========================
        // VALIDATE METHODS
        // =========================

        private void requireNonNullComponent(JComponent other) {
            if (other == null) {
                throw new IllegalArgumentException("Reference component cannot be null");
            }
        }

        private void validatePad(int pad, String edge) {
            if (pad < 0) {
                throw new IllegalArgumentException(edge + " must be >= 0");
            }
        }

        // =========================
        // SPRING CONTAINER
        // =========================

        private Spring getWidthSpring() {
            return layout.getConstraint(SpringLayout.WIDTH, SpringPanel.this);
        }

        private Spring getHeightSpring() {
            return layout.getConstraint(SpringLayout.HEIGHT, SpringPanel.this);
        }
    }
}
