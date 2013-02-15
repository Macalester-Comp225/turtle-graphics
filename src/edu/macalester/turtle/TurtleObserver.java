package edu.macalester.turtle;


public interface TurtleObserver {

    void turtleMoved(Turtle turtle, double x0, double y0, double x1, double y1);

    void turtleTurned(Turtle turtle, double oldDir, double newDir);

    void turtleChanged(Turtle turtle);
}
