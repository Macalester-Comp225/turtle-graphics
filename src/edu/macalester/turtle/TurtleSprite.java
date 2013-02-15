package edu.macalester.turtle;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * A visual representation of a virtual turtle robot. A TurtleSprite's position and direction may
 * differ from the state of the turtle it represents; this is useful for animating state changes.
 * You can bring a sprite's state back into sync with the turtle's using {@link #sync()}.
 * 
 * @author Paul Cantrell
 */
public class TurtleSprite {
    private static final double TURTLE_BODY_SIZE = 32;
    private static final BufferedImage shadowImg, bodyImg, overlayImg;

    static {
        overlayImg = readImageResource("Turtle-overlay");
        bodyImg    = readImageResource("Turtle-body");
        shadowImg  = readImageResource("Turtle-shadow");
    }
    
    private static BufferedImage readImageResource(String imgName) {
        try {
            return ImageIO.read(TurtleProgram.class.getResource("/images/" + imgName + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
            return new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        }
    }
    
    // ------ Instance ------
    
    private final Turtle turtle;
    private double x, y, direction;
    private double turtleSize = 0.5;

    /**
     * Creates a sprite to represent the given turtle.
     */
    public TurtleSprite(Turtle turtle) {
        this.turtle = turtle;
        sync();
    }
    
    /**
     * Makes the sprite's state match its turtle's, in case the sprite's position or direction
     * have changed during animation.
     */
    public void sync() {
        x = turtle.getX();
        y = turtle.getY();
        direction = turtle.getDirection();
    }

    /**
     * Draws the sprite to the given graphics context.
     */
    public void draw(Graphics2D g2) {
        AffineTransform trans = AffineTransform.getTranslateInstance(
            x - shadowImg.getWidth()  / 2 * turtleSize,
            y - shadowImg.getHeight() / 2 * turtleSize);
        trans.scale(turtleSize, turtleSize);
        
        AffineTransform transAndRot = new AffineTransform(trans); 
        transAndRot.rotate(
            (direction + 90) * Math.PI / 180,   // image is oriented up
            shadowImg.getWidth()  / 2,
            shadowImg.getHeight() / 2);
        
        g2.drawImage(shadowImg, trans, null);
        
        double radius = TURTLE_BODY_SIZE * turtleSize / 2;
        if(turtle.isPenDown()) {
            g2.setPaint(turtle.getColor());
            g2.fill(new Ellipse2D.Double(
                x - radius,
                y - radius,
                radius * 2,
                radius * 2));
        }
        
        g2.drawImage(bodyImg, transAndRot, null);
        g2.drawImage(overlayImg, trans, null);
    }
    
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getDirection() {
        return direction;
    }

    public void setDirection(double direction) {
        this.direction = direction;
    }

    /**
     * The scale of the turtle sprite, relative to its default size. 
     */
    public double getTurtleSize() {
        return turtleSize;
    }

    public void setTurtleSize(double turtleSize) {
        this.turtleSize = turtleSize;
    }
}
