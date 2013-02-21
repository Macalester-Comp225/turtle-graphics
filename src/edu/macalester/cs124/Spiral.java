package edu.macalester.cs124;

import java.awt.Color;

import edu.macalester.generator.PrimeGenerator;
import edu.macalester.turtle.Turtle;
import edu.macalester.turtle.TurtleProgram;

public class Spiral extends TurtleProgram {
    public void run() {
        setTurtleSpeedFactor(0);
        
        spiral(Color.GREEN, 90);
        spiral(Color.BLUE, 90.1);
        
        println("Done!");
    }

    private void spiral(Color spiralColor, double angle) {
        PrimeGenerator generator = new PrimeGenerator(2);
        
        Turtle turtle = new Turtle(getWidth() / 2, getHeight() / 2);
        add(turtle);
        turtle.setColor(spiralColor);
        turtle.setStepSize(1);
        
        while(turtleIsNearScreen(turtle)) {
            turtle.forward(generator.next());
            turtle.left(angle);
        }
        remove(turtle);
    }

    private boolean turtleIsNearScreen(Turtle turtle) {
        double margin = Math.max(getWidth(), getHeight()) / 2;
        return turtle.getX() > -margin
            && turtle.getY() > -margin
            && turtle.getX() < getWidth()  + margin
            && turtle.getY() < getHeight() + margin;
    }
}
