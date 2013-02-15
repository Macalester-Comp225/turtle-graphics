package edu.macalester.turtle;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * A system for drawing vector graphics using relative positioning.
 * A turtle lives on a plane, has a <b>position</b> and a <b>direction</b>, and draws as it moves.
 * The original turtles were physical robots which used an actual pen on actual paper!
 * This class mimics the behavior of that physical system in software.
 * <p>
 * You can control the turtle with the {@link #forward(double) forward()}, {@link #backward(double) backward()},
 * {@link #left(double) left()} and {@link #right(double) right()} messages, which move the turtle relative
 * to its current state. (Turtle presentation mechanisms may choose to animate these transitions.)
 * You can also instantly change the turtle's state using setter methods such as {@link #setX(double) setX()};
 * these methods work in absolute coordinates.
 * <p>
 * The turtle has a <b>pen state</b>, which includes a <b>color</b> and <b>stroke width</b>. The pen can also
 * be <b>up</b> or <b>down</b>; when it is up, the turtle moves without drawing.
 * <p>
 * In addition to using a relative system of position and direction, the turtle uses a relative
 * system of distance: the forward() and backward() work relative to the current <b>step size</b>.
 * If you only change the step size using {@link #scaleStepBy(double) scaleStepBy()} (and not the absolute
 * setter {@link #setStepSize(double) setStepSize()}, you can easily rescale an entire turtle program by
 * changing the initial step size.
 * 
 * @see <a href="http://en.wikipedia.org/wiki/Turtle_graphics">Wikipedia: Turtle graphics</a>
 * @see <a href="http://en.wikipedia.org/wiki/Turtle_(robot)">Wikipedia: Turtle robot</a>
 * @author Paul Cantrell
 */
public class Turtle {
    private final List<TurtleObserver> observers;
    private double x, y, stepSize, direction;
    private double penWidth;
    private Color color;
    private boolean drawing;
    
    /**
     * Creates a turtle with a black pen at the given initial position.
     */
    public Turtle(double x, double y) {
        this(x, y, Color.BLACK);
    }

    /**
     * Creates a turtle at the given initial position with a pen of the given color.
     */
    public Turtle(double x, double y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
        
        penWidth = 1;
        stepSize = 30;
        drawing = true;
        
        this.observers = new ArrayList<TurtleObserver>();
    }
    
    // ------ Turtle commands ------
    
    /**
     * Moves the turtle forward one step. Draws a line segment if the pen is down.
     */
    public void forward() {
        forward(1);
    }
    
    /**
     * Moves the turtle forward by an arbitrary multiplier of the current step size.
     * Draws a line segment if the pen is down.
     */
    public void forward(double steps) {
        double x0 = x, y0 = y;
        
        x += steps * stepSize * cos(direction);
        y += steps * stepSize * sin(direction);
        
        for(TurtleObserver observer : observers)
            observer.turtleMoved(this, x0, y0, x, y);
    }
    
    /**
     * Moves the turtle backward one step. Draws a line segment if the pen is down.
     */
    public void backward() {
        forward(-1);
    }
    
    /**
     * Moves the turtle backward by an arbitrary multiplier of the current step size.
     * Draws a line segment if the pen is down.
     */
    public void backward(double steps) {
        forward(-steps);
    }
    
    /**
     * Turns the turtle to its right (clockwise when viewed from above) by the given number of degrees.
     */
    public void right(double degrees) {
        double oldDirection = getDirection();
        
        direction += degrees * PI / 180;
        
        for(TurtleObserver observer : observers)
            observer.turtleTurned(this, oldDirection, getDirection());
    }
    
    /**
     * Turns the turtle to its left (counterclockwise when viewed from above) by the given number of degrees.
     */
    public void left(double degrees) {
        right(-degrees);
    }
    
    /**
     * Multiplies the current step size by the given multiplier.
     */
    public void scaleStepBy(double scale) {
        stepSize *= scale;
        changed();
    }
    
    /**
     * Lifts the pen from the virtual paper, so that subsequent motions do not draw anything.
     */
    public void penUp() {
        drawing = false;
        changed();
    }
    
    /**
     * Puts the pen down on the virtual paper, so that subsequent motions leave pixel ink behind.
     */
    public void penDown() {
        drawing = true;
        changed();
    }

    public boolean isPenDown() {
        return drawing;
    }
    
    // ------ Direct property access ------
    
    public double getX() {
        return x;
    }
    
    public void setX(double x) {
        this.x = x;
        changed();
    }
    
    public double getY() {
        return y;
    }
    
    public void setY(double y) {
        this.y = y;
        changed();
    }
    
    public double getStepSize() {
        return stepSize;
    }
    
    public void setStepSize(double step) {
        this.stepSize = step;
        changed();
    }
    
    public double getPenWidth() {
        return penWidth;
    }
    
    public void setPenWidth(double strokeWidth) {
        this.penWidth = strokeWidth;
        changed();
    }
    
    public double getDirection() {
        return direction * 180 / PI;
    }
    
    public void setDirection(double direction) {
        this.direction = direction * PI / 180;
        changed();
    }
    
    public Color getColor() {
        return color;
    }
    
    public void setColor(Color color) {
        this.color = color;
        changed();
    }
    
    // ------ Observers ------
    
    /**
     * Adds an observer to be notified of this turtle's motions (typically for the purpose of
     * drawing them to paper).
     */
    public void addObserver(TurtleObserver observer) {
        observers.add(observer);
    }

    /**
     * Removes the given observer.
     */
    public void removeObserver(TurtleObserver observer) {
        observers.remove(observer);
    }

    private void changed() {
        for(TurtleObserver observer : observers)
            observer.turtleChanged(this);
    }
}
