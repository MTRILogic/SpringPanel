# SpringPanel (Legacy)

[![](https://jitpack.io/v/MTRILogic/SpringPanel.svg)](https://jitpack.io/#MTRILogic/SpringPanel)

A fluent API wrapper for Java Swing's `SpringLayout` that simplifies complex UI layouts with intuitive method chaining.

## 🚀 Why SpringPanel?

`SpringLayout` is powerful but verbose and error-prone. SpringPanel provides:

- **Fluent API**: Chain methods for readable, concise layout code
- **Type Safety**: Compile-time checking of constraints
- **Less Boilerplate**: No more manual constraint management
- **Intuitive Syntax**: `with(component).constraint(value)` pattern
- **Error Prevention**: Built-in validation prevents common mistakes

## 📦 Installation

### Gradle

Add JitPack to your `build.gradle` or `build.gradle.kts`:

```gradle
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.MTRILogic:SpringPanel:Tag'
}
```

### Maven

```xml
<repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
</repository>

<dependency>
    <groupId>com.github.MTRILogic</groupId>
    <artifactId>SpringPanel</artifactId>
    <version>Tag</version>
</dependency>
```

## 💡 Basic Usage

The SpringPanel pattern follows a simple fluent approach:

1. **WITH** - Select the component to position
2. **CONSTRAINTS** - Define positioning and sizing via chained calls

```java
public class MyPanel extends SpringPanel {
    public MyPanel() {
        JButton button = new JButton("Click Me");

        // Position button at (10, 10) with size 100x30
        with(button)
            .x(10)
            .y(10)
            .width(100)
            .height(30);
    }
}
```

## 📚 API Reference

### `SpringPanel` Class

Extends `JPanel` and uses `SpringLayout` internally.

| Method                                 | Description                                                                                                                 |
|----------------------------------------|-----------------------------------------------------------------------------------------------------------------------------|
| `SpringPanel()`                        | Creates a new panel with `SpringLayout` pre-configured.                                                                     |
| `with(JComponent component)`           | Selects a component and returns a `ConstraintsBuilder`. Automatically adds the component to the panel if not already added. |
| `getConstraints(JComponent component)` | Returns the raw `SpringLayout.Constraints` for the given component.                                                         |

> **Validation Rules for `with(JComponent)`:**
> - Throws `IllegalArgumentException` if `component` is `null`.
> - Throws `IllegalStateException` if the component already belongs to another container.

---

### `ConstraintsBuilder` Inner Class

Returned by `with(JComponent)`. All methods return `this` to enable fluent chaining.

#### Edge Constraints (Absolute)

Anchor a component edge to the corresponding edge of the `SpringPanel`.

| Method           | Description                                     |
|------------------|-------------------------------------------------|
| `north(int pad)` | Distance from the **top** edge of the panel.    |
| `south(int pad)` | Distance from the **bottom** edge of the panel. |
| `east(int pad)`  | Distance from the **right** edge of the panel.  |
| `west(int pad)`  | Distance from the **left** edge of the panel.   |

> `pad` must be `>= 0`.

#### Edge Constraints (Relative)

Anchor a component edge to an edge of another component.

| Method                             | Description                                                                 |
|------------------------------------|-----------------------------------------------------------------------------|
| `north(JComponent other, int pad)` | Place `pad` pixels **below** `other` (`other.SOUTH` -> `component.NORTH`).  |
| `south(JComponent other, int pad)` | Place `pad` pixels **above** `other` (`other.NORTH` -> `component.SOUTH`).  |
| `east(JComponent other, int pad)`  | Place `pad` pixels **left** of `other` (`other.WEST` -> `component.EAST`).  |
| `west(JComponent other, int pad)`  | Place `pad` pixels **right** of `other` (`other.EAST` -> `component.WEST`). |

#### Fill Constraints

Convenient methods to fill large areas.

| Method           | Description                                                       |
|------------------|-------------------------------------------------------------------|
| `hFill(int pad)` | Stretches horizontally from left to right with the given padding. |
| `vFill(int pad)` | Stretches vertically from top to bottom with the given padding.   |
| `fill(int pad)`  | Stretches in **both** directions with the given padding.          |

#### Center Constraints

| Method                               | Description                                         |
|--------------------------------------|-----------------------------------------------------|
| `hCenter(int pad)`                   | Centers horizontally in the panel.                  |
| `vCenter(int pad)`                   | Centers vertically in the panel.                    |
| `center(int pad)`                    | Centers in **both** directions in the panel.        |
| `hCenter(JComponent other, int pad)` | Centers horizontally relative to another component. |
| `vCenter(JComponent other, int pad)` | Centers vertically relative to another component.   |

#### Baseline Constraint

| Method                       | Description                                                                     |
|------------------------------|---------------------------------------------------------------------------------|
| `baseline(JComponent other)` | Aligns the text baseline of the current component with the baseline of `other`. |

#### Size Constraints

| Method                             | Description                                                                    |
|------------------------------------|--------------------------------------------------------------------------------|
| `width(int value)`                 | Fixed width in pixels.                                                         |
| `width(float factor)`              | Width scaled by a factor of the **panel's** width (e.g., `0.5f` = half width). |
| `height(int value)`                | Fixed height in pixels.                                                        |
| `height(float factor)`             | Height scaled by a factor of the **panel's** height.                           |
| `square(int side)`                 | Sets both width and height to the same fixed value.                            |
| `rectangle(int width, int height)` | Sets fixed width and height in one call.                                       |

#### Absolute Position

| Method                | Description                                     |
|-----------------------|-------------------------------------------------|
| `x(int value)`        | Absolute X coordinate.                          |
| `x(float factor)`     | X coordinate as a factor of the panel's width.  |
| `y(int value)`        | Absolute Y coordinate.                          |
| `y(float factor)`     | Y coordinate as a factor of the panel's height. |
| `point(int x, int y)` | Sets both X and Y coordinates.                  |

---

## 🆚 SpringPanel vs Raw SpringLayout

### Raw SpringLayout (Verbose & Error-Prone)

```java
public class RawSpringLayoutExample extends JPanel {
    public RawSpringLayoutExample() {
        SpringLayout layout = new SpringLayout();
        setLayout(layout);

        JButton button = new JButton("Click Me");

        // Manual constraint setup
        layout.putConstraint(SpringLayout.NORTH, button, 10, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, button, 10, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.EAST, button, -10, SpringLayout.EAST, this);
        layout.putConstraint(SpringLayout.SOUTH, button, -10, SpringLayout.SOUTH, this);

        SpringLayout.Constraints constraints = layout.getConstraints(button);
        constraints.setWidth(Spring.constant(100));
        constraints.setHeight(Spring.constant(30));

        add(button);
    }
}
```

### SpringPanel (Clean & Intuitive)

```java
public class SpringPanelExample extends SpringPanel {
    public SpringPanelExample() {
        JButton button = new JButton("Click Me");

        with(button)
            .north(10)
            .west(10)
            .width(100)
            .height(30);
    }
}
```

## 🎯 Advanced Examples

### Complex Layout

```java
public class ComplexLayout extends SpringPanel {
    public ComplexLayout() {
        JPanel header = new JPanel();
        JPanel content = new JPanel();
        JPanel sidebar = new JPanel();
        JPanel footer = new JPanel();

        with(header)
            .north(0)
            .hFill(0)
            .height(60);

        with(footer)
            .south(0)
            .hFill(0)
            .height(50);

        with(sidebar)
            .north(header, 0)
            .south(footer, 0)
            .east(0)
            .width(200);

        with(content)
            .north(header, 0)
            .south(footer, 0)
            .west(0)
            .east(sidebar, 0);
    }
}
```

### Baseline Alignment

```java
public class BaselineExample extends SpringPanel {
    public BaselineExample() {
        JLabel label = new JLabel("Name:");
        JTextField field = new JTextField();

        with(label)
            .north(10)
            .west(10);

        with(field)
            .baseline(label)
            .west(label, 10)
            .width(200);
    }
}
```

### Proportional Sizing

```java
public class ProportionalExample extends SpringPanel {
    public ProportionalExample() {
        JPanel topHalf = new JPanel();
        JPanel bottomHalf = new JPanel();

        with(topHalf)
            .north(0)
            .hFill(0)
            .height(0.5f); // 50% of panel height

        with(bottomHalf)
            .north(topHalf, 0)
            .hFill(0)
            .south(0);
    }
}
```

## 📋 Features

- ✅ Fluent API design
- ✅ Method chaining support
- ✅ All SpringLayout constraints
- ✅ Relative and absolute positioning
- ✅ Baseline alignment
- ✅ Size scaling factors
- ✅ Built-in validation (null checks, negative padding checks)
- ✅ Zero external dependencies

## 🛠️ Project Information

- **Group ID:** `com.github.MTRILogic`
- **Artifact ID:** `SpringPanel`
- **Current Version:** `1.0.0`
- **Java Version:** Compatible with standard Java SE (Swing)
- **License:** MIT

## ⏭️ New Version

A newer version is available: [SpringPanel V2](https://github.com/MTRILogic/SpringPanel2)

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE.md) file for details.
