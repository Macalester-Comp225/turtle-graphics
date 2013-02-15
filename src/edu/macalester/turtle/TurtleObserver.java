package edu.macalester.turtle;


/**
 * For receiving notifications of changes in a {@link Turtle}'s state. This is typically for
 * the purpose of rendering the turtle graphics, but could also be used for showing the turtle
 * on the screen or recording its actions for later playback.
 * <p>
 * This interface gives special privilege to the two events that correspond to physical motion
 * of the virtual turtle robot: moving and turning.
 * 
 * @author Paul Cantrell
 */
public interface TurtleObserver {

    /**
     * Called after a turtle has handled a {@link Turtle#forward() forward()} or
     * {@link Turtle#backward() backward()} message. The turtle traveled from
     * (x0,y0) to (x1,y1).
     * <p>
     * This method is <b>not</b> called when the turtle is moved by a setter
     * method such as {@link Turtle#setX(double)}; setters represent discontinuous
     * motion, and thus result in a call to {@link #turtleChanged(Turtle)};
     */
    void turtleMoved(Turtle turtle, double x0, double y0, double x1, double y1);

    /**
     * Called after a turtle has handled a {@link Turtle#forward() forward()} or
     * {@link Turtle#backward() backward()} message. The turtle traveled from
     * (x0,y0) to (x1,y1).
     */
    void turtleTurned(Turtle turtle, double oldDir, double newDir);

    /**
     * Called after <i>any</i> turtle state change not handled by this interface's other methods.
     * This includes, for exmaple, a change in color, pen up/down, or a change due to an absolute
     * setter method. 
     */
    void turtleChanged(Turtle turtle);
}
