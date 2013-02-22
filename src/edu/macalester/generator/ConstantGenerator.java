package edu.macalester.generator;

public class ConstantGenerator extends Generator {
    private double constant;
    
    public ConstantGenerator(double constant) {
        this.constant = constant;
    }
    
    public double next() {
        return constant;
    }
}
