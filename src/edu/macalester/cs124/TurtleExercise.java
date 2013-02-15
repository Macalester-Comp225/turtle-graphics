package edu.macalester.cs124;

import java.awt.Color;

import edu.macalester.turtle.Turtle;
import edu.macalester.turtle.TurtleProgram;

public class TurtleExercise extends TurtleProgram {
    public void run() {
        setTurtleSpeedFactor(200);
        
        Turtle sally = new Turtle(200, 500, Color.MAGENTA);
        add(sally);
        
        Turtle fred = new Turtle(100, 200, Color.GREEN);
        add(fred);
        
        sally.forward(2);
        fred.forward(2.5);
        sally.left(90);
        sally.forward(3);
        fred.backward(0.5);
        fred.right(120);
        sally.left(45);
        sally.setColor(Color.BLUE);
        fred.forward(2.5);
        sally.forward(Math.sqrt(8));
        sally.left(90);
        fred.backward(0.5);
        fred.right(120);
        sally.forward(Math.sqrt(8));
        sally.setColor(Color.MAGENTA);
        sally.left(45);
        fred.forward(2.5);
        sally.forward(3);
        fred.penUp();
        fred.forward(1);
        fred.penDown();
        sally.left(90);
        sally.forward(2);
    }
}
