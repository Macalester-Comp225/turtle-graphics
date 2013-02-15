package edu.macalester.turtle;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.Timer;

import acm.program.Program;

public abstract class TurtleProgram extends Program implements TurtleObserver {
    
    private BufferedImage paper;
    private Graphics2D paperGraphics;
    private boolean paintNeeded;
    
    private Map<Turtle, TurtleDisplay> turtleDisplays;
    private double turtleSpeedFactor;
    private static final BufferedImage shadowImg, bodyImg, overlayImg;
    
    // ------ Setup ------
    
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
    
    @Override
    public void startHook() {
        setBackground(Color.WHITE);
        
        initPaper();
        initTurtleDisplay();
        setTurtleSpeedFactor(1000);
        startUpdateTimer();
    }

    private void initPaper() {
        paper = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        paperGraphics = paper.createGraphics();
        paperGraphics.setColor(Color.WHITE);
        paperGraphics.fillRect(0, 0, paper.getWidth(), paper.getHeight());
        
        enableAntialiasing(paperGraphics);
        paintNeeded = true;
    }
    
    private void initTurtleDisplay() {
        turtleDisplays = new HashMap<Turtle, TurtleDisplay>();
    }

    private void startUpdateTimer() {
        Timer timer = new Timer(1, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(paintNeeded)
                    repaint(1);
                paintNeeded = false;
            }
        });
        timer.setDelay(16);
        timer.start();
    }

    private void enableAntialiasing(Graphics2D g) {
        g.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(
            RenderingHints.KEY_RENDERING,
            RenderingHints.VALUE_RENDER_QUALITY);
    }


    // ------ Display settings ------

    public synchronized void add(Turtle turtle) {
        turtleDisplays.put(turtle, new TurtleDisplay(turtle));
        turtle.addObserver(this);
        paintNeeded = true;
    }
    
    public synchronized void remove(Turtle turtle) {
        turtleDisplays.remove(turtle);
        turtle.removeObserver(this);
        paintNeeded = true;
    }
    
    public synchronized void clear() {
        AffineTransform savedXform = paperGraphics.getTransform();
        paperGraphics.setTransform(AffineTransform.getTranslateInstance(0, 0));
        paperGraphics.setColor(Color.WHITE);
        paperGraphics.fillRect(0, 0, paper.getWidth(), paper.getHeight());
        paperGraphics.setTransform(savedXform);
    }
    
    public void setTurtleSpeedFactor(double factor) {
        turtleSpeedFactor = factor;
    }


    // ------ Turtle state updates ------

    @Override
    public void turtleMoved(final Turtle turtle, final double x0, final double y0, final double x1, final double y1) {
        animate(turtle, Math.pow(Math.hypot(x1-x0, y1-y0) / 50, 0.7), new AnimationCallback() {
            @Override
            public void animate(TurtleDisplay disp, double t) {
                disp.x = x0 + (x1 - x0) * t;
                disp.y = y0 + (y1 - y0) * t;
            }
        });
        
        if(turtle.isPenDown()) {
            paperGraphics.setStroke(new BasicStroke(
                (float) turtle.getPenWidth(),
                BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_ROUND));
            paperGraphics.setPaint(turtle.getColor());
            paperGraphics.draw(new Line2D.Double(x0, y0, x1, y1));
        }
    }
    
    @Override
    public void turtleTurned(final Turtle turtle, final double oldDir, final double newDir) {
        animate(turtle, Math.abs(newDir - oldDir) / 360, new AnimationCallback() {
            @Override
            public void animate(TurtleDisplay disp, double t) {
                disp.direction = oldDir + (newDir - oldDir) * t;
            }
        });
    }

    @Override
    public synchronized void turtleChanged(Turtle turtle) {
        turtleDisplays.get(turtle).sync();
        paintNeeded = true;
    }
    
    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        enableAntialiasing(g2);
        
        synchronized(this) {
            if(paper != null)
                g2.drawImage(paper, 0, 0, null);
        
            if(turtleDisplays != null)
                for(TurtleDisplay t: turtleDisplays.values())
                    drawTurtle(t, g2);
        }
    }
    
    
    // ------ Turtle animation ------
    
    private interface AnimationCallback {
        void animate(TurtleDisplay disp, double t);
    }
    
    private void animate(Turtle turtle, double animTime, AnimationCallback callback) {
        animTime *= turtleSpeedFactor;
        long animStart = System.currentTimeMillis();
        
        if(animTime >= 0.001) {
            while(true) {
                long now = System.currentTimeMillis();
                double t = (now - animStart) / animTime;
                if(t >= 1)
                    break;
                
                synchronized(this) {
                    callback.animate(turtleDisplays.get(turtle), t);
                    paintNeeded = true;
                }
                
                try {
                    long delay = 16 - (System.currentTimeMillis() - now);
                    if(delay > 0)
                        Thread.sleep(delay);
                } catch (InterruptedException e) {
                    return; // cancel animation
                }
            }
        }
        
        turtleChanged(turtle); // animation done; get everything set to final state
    }
    
    
    // ------ Drawing ------
    
    private static final double
        TURTLE_IMG_SCALE = 0.5,
        TURTLE_BODY_SIZE = 32;

    private void drawTurtle(TurtleDisplay disp, Graphics2D g2) {
        AffineTransform trans = AffineTransform.getTranslateInstance(
            disp.x - shadowImg.getWidth()  / 2 * TURTLE_IMG_SCALE,
            disp.y - shadowImg.getHeight() / 2 * TURTLE_IMG_SCALE);
        trans.scale(TURTLE_IMG_SCALE, TURTLE_IMG_SCALE);
        
        AffineTransform transAndRot = new AffineTransform(trans); 
        transAndRot.rotate(
            (disp.direction + 90) * Math.PI / 180,   // image is oriented up
            shadowImg.getWidth()  / 2,
            shadowImg.getHeight() / 2);
        
        g2.drawImage(shadowImg, trans, null);
        
        double radius = TURTLE_BODY_SIZE * TURTLE_IMG_SCALE / 2;
        if(disp.turtle.isPenDown()) {
            g2.setPaint(disp.turtle.getColor());
            g2.fill(new Ellipse2D.Double(
                disp.x - radius,
                disp.y - radius,
                radius * 2,
                radius * 2));
        }
        
        g2.drawImage(bodyImg, transAndRot, null);
        g2.drawImage(overlayImg, trans, null);
    }
    
    private class TurtleDisplay {
        public final Turtle turtle;
        public double x, y, direction;
        
        public TurtleDisplay(Turtle turtle) {
            this.turtle = turtle;
            sync();
        }

        public void sync() {
            x = turtle.getX();
            y = turtle.getY();
            direction = turtle.getDirection();
        }
    }
}
