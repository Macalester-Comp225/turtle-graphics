package edu.macalester.turtle;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Turtle {
    private final List<TurtleObserver> observers;
    private double x, y, stepSize, direction;
    private double penWidth;
    private Color color;
    private boolean drawing;
    
    public Turtle(double x, double y) {
        this(x, y, Color.BLACK);
    }

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
    
    public void forward() {
        forward(1);
    }
    
    public void forward(double steps) {
        double x0 = x, y0 = y;
        x += steps * stepSize * cos(direction);
        y += steps * stepSize * sin(direction);
        if(drawing)
            for(TurtleObserver observer : observers)
                observer.drawLine(x0, y0, x, y, color, penWidth);
        changed();
    }
    
    public void backward() {
        forward(-1);
    }
    
    public void backward(double steps) {
        forward(-steps);
    }
    
    public void right(double degrees) {
        direction += degrees * PI / 180;
        changed();
    }
    
    public void left(double degrees) {
        right(-degrees);
    }
    
    public void scaleStepBy(double scale) {
        stepSize *= scale;
        changed();
    }
    
    public void penUp() {
        drawing = false;
        changed();
    }
    
    public void penDown() {
        drawing = true;
        changed();
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

    public boolean isPenDown() {
        return drawing;
    }
    
    // ------ Observers ------
    
    public void addObserver(TurtleObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(TurtleObserver observer) {
        observers.remove(observer);
    }

    private void changed() {
        for(TurtleObserver observer : observers)
            observer.turtleChanged(this);
    }
}
