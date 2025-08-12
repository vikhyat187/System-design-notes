Alright — here’s the “6 months later” **production horror story** of sticking with the original `if`-based design.

---

## 📅 6 Months of Feature Creep

After a few product sprints, your app now supports:

* Circle
* Rectangle
* Triangle
* Ellipse
* Polygon
* Line
* Text box (yes, the PM called it a “shape” 😅)

The **`Shape`** class now looks like this:

```java
class Shape {
    private String type;
    private Map<String, Object> params; // radius, width, height, points, etc.

    public Shape(String type, Map<String, Object> params) {
        this.type = type;
        this.params = params;
    }

    public double calculateArea() {
        if (type.equals("circle")) {
            double r = (double) params.get("radius");
            return Math.PI * r * r;
        } else if (type.equals("rectangle")) {
            return (double) params.get("width") * (double) params.get("height");
        } else if (type.equals("triangle")) {
            double b = (double) params.get("base");
            double h = (double) params.get("height");
            return 0.5 * b * h;
        } else if (type.equals("ellipse")) {
            return Math.PI * (double) params.get("a") * (double) params.get("b");
        } else if (type.equals("polygon")) {
            // Complex polygon area formula
        } else if (type.equals("line")) {
            return 0;
        } else if (type.equals("textbox")) {
            return (double) params.get("width") * (double) params.get("height");
        }
        return 0;
    }

    public double calculatePerimeter() {
        if (type.equals("circle")) {
            double r = (double) params.get("radius");
            return 2 * Math.PI * r;
        } else if (type.equals("rectangle")) {
            return 2 * ((double) params.get("width") + (double) params.get("height"));
        } else if (type.equals("triangle")) {
            return (double) params.get("sideA") +
                   (double) params.get("sideB") +
                   (double) params.get("sideC");
        } else if (type.equals("ellipse")) {
            // Approximation formula for ellipse perimeter
        } else if (type.equals("polygon")) {
            // Loop over points and sum distances
        } else if (type.equals("line")) {
            // Just return length
        } else if (type.equals("textbox")) {
            return 2 * ((double) params.get("width") + (double) params.get("height"));
        }
        return 0;
    }

    public void renderOnCanvas(Canvas canvas) {
        if (type.equals("circle")) {
            // draw circle
        } else if (type.equals("rectangle")) {
            // draw rectangle
        } else if (type.equals("triangle")) {
            // draw triangle
        } else if (type.equals("ellipse")) {
            // draw ellipse
        } else if (type.equals("polygon")) {
            // draw polygon
        } else if (type.equals("line")) {
            // draw line
        } else if (type.equals("textbox")) {
            // draw text box
        }
    }

    // Now imagine 5 more methods like exportToSVG(), hitTest(), resize(), etc...
}
```

---

## **Pain Points You’d Hit in Reality**

1. **Monster Methods**

   * `calculateArea()` is already 50+ lines.
   * Every new shape adds **3+ changes** across multiple methods.

2. **Error Risk is Huge**

   * One missed `else if` means a method silently does nothing.
   * Changing `ellipse` logic might accidentally break `polygon` rendering because of a misplaced bracket.

3. **Merge Conflicts**

   * Different teams adding shapes → same file changes → constant Git conflicts.

4. **Impossible to Test in Isolation**

   * Testing `Triangle` logic means instantiating the generic `Shape` and feeding it a `type` + parameters map, instead of testing a dedicated class.

5. **No Room for Special Behavior**

   * If `TextBox` needs a unique way of rendering that doesn’t fit the existing parameters, you’ll keep shoving hacks into `params` and `if` chains.

6. **Performance Penalties**

   * Lots of `if-else` comparisons per method call — and it only grows with more shapes.

---

## **Contrast with the Polymorphic Approach**

If you used polymorphism:

* Each shape’s code is **self-contained** in its own class.
* Adding a `Star` shape means **only adding `Star.java`**, not touching existing classes.
* Testing becomes trivial — each shape can be unit-tested independently.
* Merge conflicts? Practically gone, since teams work in different files.

---

If you want, I can **refactor this 6-month monstrosity into a clean polymorphic design** so you can see the line count drop and the maintainability skyrocket.
It’s a night-and-day difference.

Alright — let’s refactor that **6-month monstrosity** into a clean, maintainable polymorphic design that follows **OCP** and **SRP**.

We’ll use:

* An **abstract base class** `Shape`
* One **subclass per shape**
* No `if-else` chains
* Type safety instead of `Map<String, Object>` parameter dumping

---

## **Polymorphic Refactor**

```java
// Base class for all shapes
abstract class Shape {
    public abstract double calculateArea();
    public abstract double calculatePerimeter();
    public abstract void renderOnCanvas(Canvas canvas);
}
```

---

### **Circle**

```java
class Circle extends Shape {
    private double radius;

    public Circle(double radius) {
        this.radius = radius;
    }

    @Override
    public double calculateArea() {
        return Math.PI * radius * radius;
    }

    @Override
    public double calculatePerimeter() {
        return 2 * Math.PI * radius;
    }

    @Override
    public void renderOnCanvas(Canvas canvas) {
        canvas.drawCircle(radius);
    }
}
```

---

### **Rectangle**

```java
class Rectangle extends Shape {
    private double width, height;

    public Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public double calculateArea() {
        return width * height;
    }

    @Override
    public double calculatePerimeter() {
        return 2 * (width + height);
    }

    @Override
    public void renderOnCanvas(Canvas canvas) {
        canvas.drawRectangle(width, height);
    }
}
```

---

### **Triangle**

```java
class Triangle extends Shape {
    private double sideA, sideB, sideC;
    private double height, base;

    public Triangle(double sideA, double sideB, double sideC, double base, double height) {
        this.sideA = sideA;
        this.sideB = sideB;
        this.sideC = sideC;
        this.base = base;
        this.height = height;
    }

    @Override
    public double calculateArea() {
        return 0.5 * base * height;
    }

    @Override
    public double calculatePerimeter() {
        return sideA + sideB + sideC;
    }

    @Override
    public void renderOnCanvas(Canvas canvas) {
        canvas.drawTriangle(sideA, sideB, sideC);
    }
}
```

---

### **Polygon**

```java
class Polygon extends Shape {
    private List<Point> points;

    public Polygon(List<Point> points) {
        this.points = points;
    }

    @Override
    public double calculateArea() {
        double area = 0;
        for (int i = 0; i < points.size(); i++) {
            Point p1 = points.get(i);
            Point p2 = points.get((i + 1) % points.size());
            area += (p1.x * p2.y) - (p2.x * p1.y);
        }
        return Math.abs(area / 2.0);
    }

    @Override
    public double calculatePerimeter() {
        double perimeter = 0;
        for (int i = 0; i < points.size(); i++) {
            Point p1 = points.get(i);
            Point p2 = points.get((i + 1) % points.size());
            perimeter += Math.hypot(p1.x - p2.x, p1.y - p2.y);
        }
        return perimeter;
    }

    @Override
    public void renderOnCanvas(Canvas canvas) {
        canvas.drawPolygon(points);
    }
}
```

---

### **Usage in Production**

```java
public class DrawingApp {
    public static void main(String[] args) {
        Canvas canvas = new Canvas();

        List<Shape> shapes = List.of(
            new Circle(5),
            new Rectangle(4, 6),
            new Triangle(3, 4, 5, 3, 4),
            new Polygon(List.of(new Point(0,0), new Point(4,0), new Point(4,3)))
        );

        for (Shape shape : shapes) {
            System.out.println("Area: " + shape.calculateArea());
            System.out.println("Perimeter: " + shape.calculatePerimeter());
            shape.renderOnCanvas(canvas);
        }
    }
}
```

---

## **Advantages Now**

✅ **Open for extension** — Add a new shape by making a new class, no changes to existing code.
✅ **Single responsibility** — Each shape handles only its own logic.
✅ **Type safety** — No `Map` casting errors.
✅ **No merge conflicts** — Teams can work on different shape files.
✅ **Easier testing** — Each shape can be tested in isolation.

---

If you want, I can also show **before vs after line count & change impact** so you see how, after adding even 5 more shapes, the if-else version explodes while this one stays flat. That’s the killer interview point for explaining **OCP**.
