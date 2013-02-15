package edu.macalester.turtle;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.Timer;

import acm.program.Program;

public abstract class TurtleProgram extends Program implements TurtleObserver {
    
    private BufferedImage paper;
    private Graphics2D paperGraphics;
    private boolean paintNeeded;
    
    private Map<Turtle, TurtleSprite> sprites;
    private Set<AnimationCallback> animationsInProgress;
    private double turtleSpeedFactor;
    
    // ------ Setup ------
    
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
        sprites = new HashMap<Turtle, TurtleSprite>();
        animationsInProgress = new HashSet<AnimationCallback>();
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
        g.setRenderingHint(
            RenderingHints.KEY_STROKE_CONTROL,
            RenderingHints.VALUE_STROKE_PURE);
    }


    // ------ Display settings ------

    public synchronized void add(Turtle turtle) {
        sprites.put(turtle, new TurtleSprite(turtle));
        turtle.addObserver(this);
        paintNeeded = true;
    }
    
    public synchronized void remove(Turtle turtle) {
        sprites.remove(turtle);
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
    
    public void setTurtleSize(double size) {
        for(TurtleSprite sprite : sprites.values())
            sprite.setTurtleSize(size * 0.5);
    }


    // ------ Turtle state updates ------

    @Override
    public void turtleMoved(final Turtle turtle, final double x0, final double y0, final double x1, final double y1) {
        animate(turtle, Math.pow(Math.hypot(x1-x0, y1-y0) / 50, 0.7), new AnimationCallback() {
            @Override
            public void animate(TurtleSprite sprite) {
                sprite.setX(curX());
                sprite.setY(curY());
            }

            @Override
            public void paint(Graphics2D g) {
                if(turtle.isPenDown()) {
                    g.setStroke(new BasicStroke(
                        (float) turtle.getPenWidth(),
                        BasicStroke.CAP_ROUND,
                        BasicStroke.JOIN_ROUND));
                    g.setPaint(turtle.getColor());
                    g.draw(new Line2D.Double(
                        x0 + 0.5,
                        y0 + 0.5,
                        curX() + 0.5,
                        curY() + 0.5));
                }
            }
            
            private double curX() {
                return x0 + (x1 - x0) * t;
            }
            
            private double curY() {
                return y0 + (y1 - y0) * t;
            }
        });
    }

    @Override
    public void turtleTurned(final Turtle turtle, final double oldDir, final double newDir) {
        animate(turtle, Math.abs(newDir - oldDir) / 360, new AnimationCallback() {
            @Override
            public void animate(TurtleSprite sprite) {
                sprite.setDirection(oldDir + (newDir - oldDir) * t);
            }
        });
    }

    @Override
    public synchronized void turtleChanged(Turtle turtle) {
        sprites.get(turtle).sync();
        paintNeeded = true;
    }
    
    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        enableAntialiasing(g2);
        
        synchronized(this) {
            if(paper != null)
                g2.drawImage(paper, 0, 0, null);
            
            if(animationsInProgress != null)
                for(AnimationCallback callback : animationsInProgress)
                    callback.paint(g2);
            
            if(sprites != null)
                for(TurtleSprite sprite: sprites.values())
                    sprite.draw(g2);
        }
    }
    
    protected Rectangle2D normalizeRect(Rectangle2D.Double r) {
        if(r.width < 0) {
            r.x += r.width;
            r.width *= -1;
        }
        if(r.height < 0) {
            r.y += r.height;
            r.height *= -1;
        }
        return r;
    }
    
    
    // ------ Turtle animation ------
    
    private abstract class AnimationCallback {
        public double t;
        
        public abstract void animate(TurtleSprite sprite);
        
        public void paint(Graphics2D g) { }
    }
    
    private void animate(Turtle turtle, double animTime, AnimationCallback callback) {
        synchronized (this) {
            animationsInProgress.add(callback);
        }
        
        animTime *= turtleSpeedFactor;
        long animStart = System.currentTimeMillis();
        if(animTime >= 0.001) {
            while(true) {
                long now = System.currentTimeMillis();
                double t = (now - animStart) / animTime;
                if(t >= 1)
                    break;
                
                synchronized(this) {
                    callback.t = t;
                    callback.animate(sprites.get(turtle));
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
        
        synchronized(this) {
            animationsInProgress.remove(callback);
            callback.t = 1;
            callback.paint(paperGraphics);
        }
        turtleChanged(turtle); // animation done; get everything set to final state
    }
}
