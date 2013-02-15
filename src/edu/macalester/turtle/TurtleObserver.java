package edu.macalester.turtle;

import java.awt.Color;

public interface TurtleObserver {

    void drawLine(double x0, double y0, double x1, double y1, Color color, double strokeWidth);

    void turtleChanged(Turtle turtle);

}
