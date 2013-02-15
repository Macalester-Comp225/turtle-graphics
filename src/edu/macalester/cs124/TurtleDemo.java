package edu.macalester.cs124;

import java.awt.Color;

import edu.macalester.turtle.Turtle;
import edu.macalester.turtle.TurtleProgram;

public class TurtleDemo extends TurtleProgram {
    public void run() {
        Turtle turtle = new Turtle(getWidth() * 0.5, getHeight() * 0.8, Color.BLUE);
        add(turtle);
        setTurtleSpeedFactor(1000);
        turtle.setPenWidth(1);
        turtle.setStepSize(7);
        
        for(double n = 0; n < 360; n += 9) {
            drawFunkyStar(turtle, 7, n, -117, 1.5, 2.4);
            turtle.penUp();
            turtle.forward(6);
            turtle.penDown();
            turtle.left(9);
        }
        
        turtle.setColor(new Color(1f, 0.3f, 0, 0.1f));
        turtle.setStepSize(200);
        drawMultiPoly(turtle, 5, 7, 2);
        
        remove(turtle);
    }
    
    public void drawFunkyStar(Turtle turtle, int sides, double a1, double a2, double s1, double s2) {
        double a3 = 360.0 / sides - a1 - a2;
        for(int n = 0; n < sides; n++) {
            turtle.setColor(Color.BLUE);
            turtle.forward(s1);
            turtle.left(a1);
            turtle.setColor(Color.GREEN);
            turtle.forward(s2);
            turtle.left(a2);
            turtle.setColor(Color.MAGENTA);
            turtle.forward(1);
            turtle.left(a3);
        }
    }
    
    public void drawMultiPoly(Turtle turtle, int sides, int level, double scale) {
        for(int n = 0; n < sides; n++) {
            turtle.forward(1);
            if(level > 0) {
                turtle.right(180 - 360.0 / sides);
                turtle.scaleStepBy(1 / scale);
                drawMultiPoly(turtle, sides, level - 1, scale);
                turtle.scaleStepBy(scale);
                turtle.left(180 - 360.0 / sides);
            }
            turtle.left(360.0 / sides);
        }
    }
}
