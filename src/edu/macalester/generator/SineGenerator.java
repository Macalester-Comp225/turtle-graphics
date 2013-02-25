package edu.macalester.generator;

public class SineGenerator extends Generator {
    private Generator stepGen;
    private Generator ampGen;
    
    public SineGenerator(double x, double step, double amp) {
        stepGen = new SteppingGenerator(x, step);
        ampGen = new ConstantGenerator(amp);
    }

    public SineGenerator(Generator stepGen, Generator ampGen) {
        this.stepGen = stepGen;
        this.ampGen = ampGen;
    }

    public double next() {
        return Math.sin(stepGen.next()) * ampGen.next();
    }
}
