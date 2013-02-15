package edu.macalester.cs124;

import java.awt.Color;

import edu.macalester.turtle.Turtle;
import edu.macalester.turtle.TurtleProgram;

public class TurtleExercise extends TurtleProgram {
    public void run() {
        Turtle turtle = new Turtle(400, 400, Color.MAGENTA);
        add(turtle);
        
        turtle.forward(2);
        turtle.left(90);
        turtle.forward(3);
        turtle.left(45);
        turtle.setColor(Color.BLUE);
        turtle.forward(Math.sqrt(8));
        turtle.left(90);
        turtle.forward(Math.sqrt(8));
        turtle.setColor(Color.MAGENTA);
        turtle.left(45);
        turtle.forward(3);
        turtle.left(90);
        turtle.forward(2);
    }
}
