package edu.macalester.cs124;

import edu.macalester.turtle.Turtle;
import edu.macalester.turtle.TurtleProgram;

public class Spirals extends TurtleProgram {
    public void run() {
        Turtle turtle = new Turtle(getWidth() / 2, getHeight() / 2);
        add(turtle);
        turtle.setPenWidth(0.1);
        
        setTurtleSpeedFactor(0);
        
        double angle = 0;
        while(true) {
            turtle.forward(0.1);
            turtle.left(angle);
            angle = (angle + 1.01) % 360;
        }
    }
}
