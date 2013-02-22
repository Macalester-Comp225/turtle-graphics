package edu.macalester.generator;


public class SteppingGenerator extends Generator {
    private double cur;
    private double step;
    
    public SteppingGenerator(double start, double step) {
        this.cur = start - step;
        this.step = step;
    }
    
    public double next() {
        cur += step;
        return cur;
    }
}
