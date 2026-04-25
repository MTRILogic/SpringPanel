package com.mtrilogic.desktop.abstracts;

import javax.swing.*;
import java.util.Objects;

/**
 * An abstract JPanel that provides a fluent API for SpringLayout constraints.
 * This class simplifies the process of setting up complex SpringLayout layouts
 * by offering method chaining for constraint configuration.
 * 
 * <p>Example usage:
 * <pre>{@code
 * SpringPanel panel = new MySpringPanel()
 *     .with(button)
 *     .north(10)
 *     .west(10)
 *     .width(100)
 *     .height(30)
 *     .apply();
 * }</pre>
 * 
 * @author MTRILogic
 * @version 1.0
 * @since 1.0
 */
@SuppressWarnings("unused")
public abstract class SpringPanel extends JPanel {
    private JComponent component;
    private Restrictions r;

    /**
     * Creates a new SpringPanel with a SpringLayout as its layout manager.
     */
    public SpringPanel(){
        setLayout(new SpringLayout());
    }

    /**
     * Sets the component to be configured with constraints.
     * This method must be called before setting any constraints.
     * 
     * @param component the JComponent to be added to this panel
     * @return this SpringPanel instance for method chaining
     * @throws IllegalArgumentException if component is null
     * @throws IllegalStateException if a previous component hasn't been applied yet
     */
    public SpringPanel with(JComponent component){
        if (component == null) {
            throw new IllegalArgumentException("Component cannot be null");
        }
        if (this.component != null) {
            throw new IllegalStateException("Previous component not applied. Call apply() before calling with() again.");
        }
        this.component = component;
        r = new Restrictions();
        return this;
    }

    /**
     * Gets the SpringLayout constraints for the specified component.
     * 
     * @param component the component to get constraints for
     * @return the SpringLayout.Constraints for the component
     */
    public SpringLayout.Constraints getConstraints(JComponent component){
        SpringLayout layout = (SpringLayout) getLayout();
        return layout.getConstraints(component);
    }

    /**
     * Applies all configured constraints to the component and adds it to the panel.
     * This method must be called for each component added to the layout,
     * after setting all or some of its north, south, east, and west positions.
     * 
     * @throws IllegalStateException if no component has been set or if component belongs to another container
     */
    public void apply(){
        if (component == null) {
            throw new IllegalStateException("No component set. Call with() before apply().");
        }
        if (component.getParent() != null && component.getParent() != this) {
            throw new IllegalStateException("Component already belongs to another container.");
        }
        SpringLayout layout = (SpringLayout) getLayout();
        if (r.northSpring != null) {
            if (r.northComponent != null) {
                layout.putConstraint(SpringLayout.NORTH, component, r.northSpring, SpringLayout.SOUTH, r.northComponent);
            } else {
                layout.putConstraint(SpringLayout.NORTH, component, r.northSpring, SpringLayout.NORTH, this);
            }
        }
        if (r.southSpring != null) {
            if (r.southComponent != null) {
                layout.putConstraint(SpringLayout.SOUTH, component, r.southSpring, SpringLayout.NORTH, r.southComponent);
            } else {
                layout.putConstraint(SpringLayout.SOUTH, component, r.southSpring, SpringLayout.SOUTH, this);
            }
        }
        if (r.eastSpring != null) {
            if (r.eastComponent != null) {
                layout.putConstraint(SpringLayout.EAST, component, r.eastSpring, SpringLayout.WEST, r.eastComponent);
            } else {
                layout.putConstraint(SpringLayout.EAST, component, r.eastSpring, SpringLayout.EAST, this);
            }
        }
        if (r.westSpring != null) {
            if (r.westComponent != null) {
                layout.putConstraint(SpringLayout.WEST, component, r.westSpring, SpringLayout.EAST, r.westComponent);
            } else {
                layout.putConstraint(SpringLayout.WEST, component, r.westSpring, SpringLayout.WEST, this);
            }
        }
        if (r.horizontalSpring != null) {
            layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, component, r.horizontalSpring, SpringLayout.HORIZONTAL_CENTER, Objects.requireNonNullElse(r.horizontalComponent, this));
        }
        if (r.verticalSpring != null) {
            layout.putConstraint(SpringLayout.VERTICAL_CENTER, component, r.verticalSpring, SpringLayout.VERTICAL_CENTER, Objects.requireNonNullElse(r.verticalComponent, this));
        }
        if (r.baselineSpring != null) {
            layout.putConstraint(SpringLayout.BASELINE, component, r.baselineSpring, SpringLayout.BASELINE, Objects.requireNonNullElse(r.baselineComponent, this));
        }

        SpringLayout.Constraints constraints = layout.getConstraints(component);
        if (r.widthSpring != null) {
            constraints.setWidth(r.widthSpring);
        }
        if (r.heightSpring != null) {
            constraints.setHeight(r.heightSpring);
        }
        if (r.xSpring != null) {
            constraints.setX(r.xSpring);
        }
        if (r.ySpring != null) {
            constraints.setY(r.ySpring);
        }

        if (component.getParent() == null) {
            add(component);
        }

        component = null;
        r = null;
    }

    /*===========================================================================
    CONSTRAINT SETTING METHODS
    ===========================================================================*/

    /**
     * Sets the north (top) constraint for the component.
     * 
     * @param northComponent the component to constrain relative to (null for panel edge)
     * @param northPad the padding from the reference component/edge
     * @return this SpringPanel instance for method chaining
     */
    public SpringPanel north(JComponent northComponent, int northPad){
        ensureComponentSet(northPad, "NORTH");
        r.northSpring = Spring.constant(northPad);
        r.northComponent = northComponent;
        return this;
    }

    /**
     * Sets the north (top) constraint relative to the panel edge.
     * 
     * @param northPad the padding from the panel's top edge
     * @return this SpringPanel instance for method chaining
     */
    public SpringPanel north(int northPad){
        return north(null, northPad);
    }

    /**
     * Sets the south (bottom) constraint for the component.
     * 
     * @param southComponent the component to constrain relative to (null for panel edge)
     * @param southPad the padding from the reference component/edge
     * @return this SpringPanel instance for method chaining
     */
    public SpringPanel south(JComponent southComponent, int southPad){
        ensureComponentSet(southPad, "SOUTH");
        r.southSpring  = Spring.constant(-southPad);
        r.southComponent = southComponent;
        return this;
    }

    /**
     * Sets the south (bottom) constraint relative to the panel edge.
     * 
     * @param southPad the padding from the panel's bottom edge
     * @return this SpringPanel instance for method chaining
     */
    public SpringPanel south(int southPad){
        return south(null, southPad);
    }

    /**
     * Sets the east (right) constraint for the component.
     * 
     * @param eastComponent the component to constrain relative to (null for panel edge)
     * @param eastPad the padding from the reference component/edge
     * @return this SpringPanel instance for method chaining
     */
    public SpringPanel east(JComponent eastComponent, int eastPad){
        ensureComponentSet(eastPad, "EAST");
        r.eastSpring = Spring.constant(-eastPad);
        r.eastComponent = eastComponent;
        return this;
    }

    /**
     * Sets the east (right) constraint relative to the panel edge.
     * 
     * @param eastPad the padding from the panel's right edge
     * @return this SpringPanel instance for method chaining
     */
    public SpringPanel east(int eastPad) {
        return east(null, eastPad);
    }

    /**
     * Sets the west (left) constraint for the component.
     * 
     * @param westComponent the component to constrain relative to (null for panel edge)
     * @param westPad the padding from the reference component/edge
     * @return this SpringPanel instance for method chaining
     */
    public SpringPanel west(JComponent westComponent, int westPad){
        ensureComponentSet(westPad, "WEST");
        r.westSpring = Spring.constant(westPad);
        r.westComponent = westComponent;
        return this;
    }

    /**
     * Sets the west (left) constraint relative to the panel edge.
     * 
     * @param westPad the padding from the panel's left edge
     * @return this SpringPanel instance for method chaining
     */
    public SpringPanel west(int westPad){
        return west(null, westPad);
    }

    /**
     * Sets the horizontal center constraint for the component.
     * 
     * @param horizontalComponent the component to center relative to (null for panel)
     * @param horizontalPad the offset from the center position
     * @return this SpringPanel instance for method chaining
     */
    public SpringPanel horizontalCenter(JComponent horizontalComponent, int horizontalPad) {
        ensureComponentSet(horizontalPad, "HORIZONTAL_CENTER");
        r.horizontalSpring = Spring.constant(horizontalPad);
        r.horizontalComponent = horizontalComponent;
        return this;
    }

    /**
     * Sets the horizontal center constraint relative to the panel.
     * 
     * @param horizontalPad the offset from the panel's horizontal center
     * @return this SpringPanel instance for method chaining
     */
    public SpringPanel horizontalCenter(int horizontalPad) {
        return horizontalCenter(null, horizontalPad);
    }

    /**
     * Sets the vertical center constraint for the component.
     * 
     * @param verticalComponent the component to center relative to (null for panel)
     * @param verticalPad the offset from the center position
     * @return this SpringPanel instance for method chaining
     */
    public SpringPanel verticalCenter(JComponent verticalComponent, int verticalPad) {
        ensureComponentSet(verticalPad, "VERTICAL_CENTER");
        r.verticalSpring = Spring.constant(verticalPad);
        r.verticalComponent = verticalComponent;
        return this;
    }

    /**
     * Sets the vertical center constraint relative to the panel.
     * 
     * @param verticalPad the offset from the panel's vertical center
     * @return this SpringPanel instance for method chaining
     */
    public SpringPanel verticalCenter(int verticalPad) {
        return verticalCenter(null, verticalPad);
    }

    /**
     * Sets the baseline constraint for the component.
     * 
     * @param baselineComponent the component to align baseline with (null for panel)
     * @param baselinePad the offset from the baseline
     * @return this SpringPanel instance for method chaining
     */
    public SpringPanel baseline(JComponent baselineComponent, int baselinePad) {
        ensureComponentSet(baselinePad, "BASELINE");
        r.baselineSpring = Spring.constant(baselinePad);
        r.baselineComponent = baselineComponent;
        return this;
    }

    /**
     * Sets the baseline constraint relative to the panel.
     * 
     * @param baselinePad the offset from the panel's baseline
     * @return this SpringPanel instance for method chaining
     */
    public SpringPanel baseline(int baselinePad) {
        return baseline(null, baselinePad);
    }

    /**
     * Sets the width constraint for the component.
     * 
     * @param widthPad the base width value
     * @param widthFactor the scaling factor for the width
     * @return this SpringPanel instance for method chaining
     */
    public SpringPanel width(int widthPad, float widthFactor) {
        ensureComponentSet(widthPad, "WIDTH");
        if (widthFactor != 1) {
            r.widthSpring = Spring.scale(Spring.constant(widthPad), widthFactor);
        } else {
            r.widthSpring = Spring.constant(widthPad);
        }
        return this;
    }

    /**
     * Sets the width constraint for the component with no scaling.
     * 
     * @param widthPad the width value
     * @return this SpringPanel instance for method chaining
     */
    public SpringPanel width(int widthPad) {
        return width(widthPad, 1);
    }

    /**
     * Sets the height constraint for the component.
     * 
     * @param heightPad the base height value
     * @param heightFactor the scaling factor for the height
     * @return this SpringPanel instance for method chaining
     */
    public SpringPanel height(int heightPad, float heightFactor) {
        ensureComponentSet(heightPad, "HEIGHT");
        if (heightFactor != 1) {
            r.heightSpring = Spring.scale(Spring.constant(heightPad), heightFactor);
        } else {
            r.heightSpring = Spring.constant(heightPad);
        }
        return this;
    }

    /**
     * Sets the height constraint for the component with no scaling.
     * 
     * @param heightPad the height value
     * @return this SpringPanel instance for method chaining
     */
    public SpringPanel height(int heightPad) {
        return height(heightPad, 1);
    }

    /**
     * Sets the X position constraint for the component.
     * 
     * @param xPad the base X position value
     * @param xFactor the scaling factor for the X position
     * @return this SpringPanel instance for method chaining
     */
    public SpringPanel x(int xPad, float xFactor) {
        ensureComponentSet(xPad, "X");
        if (xFactor != 1) {
            r.xSpring = Spring.scale(Spring.constant(xPad), xFactor);
        } else {
            r.xSpring = Spring.constant(xPad);
        }
        return this;
    }

    /**
     * Sets the X position constraint for the component with no scaling.
     * 
     * @param xPad the X position value
     * @return this SpringPanel instance for method chaining
     */
    public SpringPanel x(int xPad) {
        return x(xPad, 1);
    }

    /**
     * Sets the Y position constraint for the component.
     * 
     * @param yPad the base Y position value
     * @param yFactor the scaling factor for the Y position
     * @return this SpringPanel instance for method chaining
     */
    public SpringPanel y(int yPad, float yFactor) {
        ensureComponentSet(yPad, "Y");
        if (yFactor != 1) {
            r.ySpring = Spring.scale(Spring.constant(yPad), yFactor);
        } else {
            r.ySpring = Spring.constant(yPad);
        }
        return this;
    }

    /**
     * Sets the Y position constraint for the component with no scaling.
     * 
     * @param yPad the Y position value
     * @return this SpringPanel instance for method chaining
     */
    public SpringPanel y(int yPad) {
        return y(yPad, 1);
    }

    private void ensureComponentSet(int pad, String edge) {
        validPadSet(pad, edge);
        if (component == null) {
            throw new IllegalStateException("Call with(component) before setting constraints.");
        }
    }

    private void validPadSet(int pad, String edge) {
        if (pad < 0) {
            throw new IllegalArgumentException(edge + " must be >= 0");
        }
    }

    /**
     * Inner class to store constraint configuration for a component.
     * This class holds all the Spring objects and reference components
     * needed to define the layout constraints.
     */
    private static class Restrictions {
        private JComponent
                northComponent,
                westComponent,
                southComponent,
                eastComponent,
                horizontalComponent,
                verticalComponent,
                baselineComponent;
        private Spring
                northSpring,
                westSpring,
                eastSpring,
                southSpring,
                horizontalSpring,
                verticalSpring,
                baselineSpring,
                widthSpring,
                heightSpring,
                xSpring,
                ySpring;
    }
}
