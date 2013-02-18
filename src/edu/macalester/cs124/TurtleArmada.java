package edu.macalester.cs124;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import edu.macalester.turtle.Turtle;
import edu.macalester.turtle.TurtleProgram;

public class TurtleArmada extends TurtleProgram {
    public void run() {
        List<Turtle> turtles = new ArrayList<Turtle>();
        for(int n = 1; n < 101; n++) {
            Turtle turtle = new Turtle(
                getWidth() / 2,
                getHeight() / 2,
                new Color(((n * 236374563) & 0xFFFFFF) | 0x55000000, true));
            turtles.add(turtle);
            add(turtle);
        }
        
        setTurtleSpeedFactor(0);
        double angle = 0;
        while(true) {
            for(Turtle turtle : turtles) {
                turtle.forward(0.1);
                turtle.right(angle);
                angle = (angle + 0.05) % 360;
            }
        }
    }
}
