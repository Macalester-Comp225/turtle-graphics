package edu.macalester.cs124;

import java.awt.Color;

import edu.macalester.turtle.Turtle;
import edu.macalester.turtle.TurtleProgram;

public class TurtleExercise extends TurtleProgram {
    public void run() {
        
        {
            int x = 7;
            int y = 3;
            println((x + y) / (x - y));
        }
        {
            int x = 7;
            int y = 3;
            println(x + y / x - y);
        }
        {
            boolean isAlive = true;
            int neighborCount = 6;
            println(neighborCount == 3 || isAlive && neighborCount == 2);
        }
        {
            double x = 12, y = 23;
            double targetX = 9, targetY = 27;
            double minDistance = 7;
            println(Math.hypot(x - targetX, y - targetY) < minDistance);
        }
        {
            println(5 + 6 + "fish" + 7 + 8);
        }
        
        
        setTurtleSpeedFactor(1000);
        
        Turtle sally = new Turtle(200, 500, Color.MAGENTA);
        add(sally);
        
        Turtle fred = new Turtle(100, 200, Color.GREEN);
        this.add(fred);
        
        sally.forward(2);
        sally.left(90);
        fred.forward(2.5);
        sally.forward(3);
        sally.left(45);
        sally.setColor(Color.BLUE);
        fred.backward(0.5);
        fred.right(120);
        sally.forward(Math.sqrt(8));
        sally.left(90);
        fred.forward(2.5);
        sally.forward(Math.sqrt(8));
        sally.setColor(Color.MAGENTA);
        fred.backward(0.5);
        sally.left(45);
        sally.forward(3);
        fred.right(120);
        fred.forward(2.5);
        fred.penUp();
        fred.forward(1);
        fred.penDown();
        sally.left(90);
        sally.forward(2);
    }
}
