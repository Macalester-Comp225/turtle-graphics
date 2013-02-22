package edu.macalester.generator;

import static org.junit.Assert.*;

import org.junit.Test;

public class SteppingGeneratorTest {

    @Test
    public void startsAtStart() {
        SteppingGenerator gen = new SteppingGenerator(2, 7);
        assertEquals(2, gen.next(), 0.00001);
    }

    @Test
    public void stepsByStep() {
        SteppingGenerator gen = new SteppingGenerator(2, 7);
        gen.next();
        assertEquals(9, gen.next(), 0.00001);
        assertEquals(16, gen.next(), 0.00001);
    }
}
