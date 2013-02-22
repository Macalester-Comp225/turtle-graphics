package edu.macalester.generator;

public class SineGenerator extends Generator {
    private double x;
    private double step;
    private double amp;
    
    public SineGenerator(double x, double step, double amp) {
        this.x = x;
        this.step = step;
        this.amp = amp;
    }

    public double next() {
        x += step;
        return Math.sin(x) * amp;
    }

}
