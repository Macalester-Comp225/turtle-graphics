package edu.macalester.cs124;

import java.awt.Color;

import edu.macalester.generator.ConstantGenerator;
import edu.macalester.generator.Generator;
import edu.macalester.generator.PrimeGenerator;
import edu.macalester.generator.SineGenerator;
import edu.macalester.turtle.Turtle;
import edu.macalester.turtle.TurtleProgram;


public class Spiral extends TurtleProgram {
    public void run() {
        setTurtleSpeedFactor(0);
        
        spiral(
            Color.BLUE,
            new SineGenerator(
                new ConstantGenerator(1),
                new SineGenerator(0, 0.00004, 1500)),
            new ConstantGenerator(50),
            2);
        
        spiral(
            Color.BLACK,
            new ConstantGenerator(5),
            new SineGenerator(
                new SineGenerator(0, 0.0004, 5),
                new ConstantGenerator(180)),
            2);
        
        spiral(
            new Color(0.7f, 0.1f, 0.6f, 0.2f),
            new ConstantGenerator(1),
            new PrimeGenerator(2),   // prime used for angle is essentially random
            2);
    }

    private void spiral(Color spiralColor, Generator stepGen, Generator angleGen, int skipSize) {
        Turtle turtle = new Turtle(getWidth() / 2, getHeight() / 2);
        add(turtle);
        turtle.setColor(spiralColor);
        turtle.setStepSize(1);
        
        while(turtleIsNearScreen(turtle)) {
            stepGen.skip(skipSize);
            angleGen.skip(skipSize);
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













