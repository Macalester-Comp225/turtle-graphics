package edu.macalester.generator;

public abstract class Generator {
    public abstract double next();

    public void skip(int numToSkip) {
        for(int n = 0; n < numToSkip; n++)
            next();
    }
}
