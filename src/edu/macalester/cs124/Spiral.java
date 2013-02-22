package edu.macalester.cs124;

import java.awt.Color;

import edu.macalester.generator.ConstantGenerator;
import edu.macalester.generator.Generator;
import edu.macalester.generator.SineGenerator;
import edu.macalester.turtle.Turtle;
import edu.macalester.turtle.TurtleProgram;


public class Spiral extends TurtleProgram {
    public void run() {
        setTurtleSpeedFactor(0);
        
        spiral(
            Color.BLUE,
            new ConstantGenerator(2),  // step
            new SineGenerator(0, 0.0004, 180));  // angle
        
        spiral(
            Color.GREEN,
            new ConstantGenerator(1),
            new SineGenerator(0, 0.0004, 180));
    }

    private void spiral(Color spiralColor, Generator stepGen, Generator angleGen) {
        Turtle turtle = new Turtle(getWidth() / 2, getHeight() / 2);
        add(turtle);
        turtle.setColor(spiralColor);
        turtle.setStepSize(1);
        
        while(turtleIsNearScreen(turtle)) {
            turtle.forward(stepGen.next());
            turtle.left(angleGen.next());
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
