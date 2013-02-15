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
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Timer;

import acm.program.Program;

public abstract class TurtleProgram extends Program implements TurtleObserver {
    
    private BufferedImage paper;
    private Graphics2D paperGraphics;
    private boolean paintNeeded;
    private List<Turtle> turtles;
    private Path2D turtlePath;
    private boolean turboMode;
    
    // ------ Setup ------
    
    @Override
    public void startHook() {
        setBackground(Color.WHITE);
        
        initPaper();
        initTurtleDisplay();
        startUpdateTimer();
    }

    private void initPaper() {
        paper = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        paperGraphics = paper.createGraphics();
        paperGraphics.setColor(Color.WHITE);
        paperGraphics.fillRect(0, 0, paper.getWidth(), paper.getHeight());
        
        paperGraphics.setTransform(AffineTransform.getTranslateInstance(getWidth() / 2, getHeight() / 2));
        enableAntialiasing(paperGraphics);
        paintNeeded = true;
    }
    
    private void initTurtleDisplay() {
        turtles = new ArrayList<Turtle>();
        
        turtlePath = new Path2D.Double();
        double w = 7, h = 5;
        turtlePath.moveTo(w, 0);
        turtlePath.lineTo(-w, h);
        turtlePath.quadTo(0, 0, -w, -h);
        turtlePath.closePath();
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
    
    public synchronized void add(Turtle turtle) {
        turtles.add(turtle);
        turtle.addObserver(this);
        paintNeeded = true;
    }
    
    public synchronized void remove(Turtle turtle) {
        turtles.remove(turtle);
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
    
    // ------ Drawing ------

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        enableAntialiasing(g2);
        
        synchronized(this) {
            if(paper != null)
                g2.drawImage(paper, 0, 0, null);
        
            if(turtles != null)
                for(Turtle turtle : turtles)
                    drawTurtle(turtle, g2);
        }
    }

    @Override
    public synchronized void drawLine(double x0, double y0, double x1, double y1, Color color, double strokeWidth) {
        paperGraphics.setStroke(new BasicStroke(
            (float) strokeWidth,
            BasicStroke.CAP_ROUND,
            BasicStroke.JOIN_ROUND));
        paperGraphics.setPaint(color);
        paperGraphics.draw(new Line2D.Double(x0, y0, x1, y1));
        paintNeeded = true;
    }

    @Override
    public void turtleChanged(Turtle turtle) {
        boolean sleep = false;
        synchronized(this) {
            paintNeeded = true;
            if(!turboMode)
                sleep = true;
        }
        
        if(sleep)
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                // ignore
            }
    }

    private void drawTurtle(Turtle turtle, Graphics2D g2) {
        AffineTransform savedXform = g2.getTransform();
        
        g2.setTransform(paperGraphics.getTransform());
        g2.translate(turtle.getX(), turtle.getY());
        g2.rotate(turtle.getDirection() * Math.PI / 180);
        g2.setPaint(turtle.getColor());
        g2.fill(turtlePath);
        g2.setPaint(Color.BLACK);
        g2.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER));
        g2.draw(turtlePath);
        
        g2.setTransform(savedXform);
    }
    
    public void setTurboMode(boolean turboMode) {
        this.turboMode = turboMode;
    }
}



