package edu.macalester.generator;

import static org.junit.Assert.*;

import org.junit.Test;

public class SineGeneratorTest {
    @Test
    public void startsAtStart() {
        SineGenerator gen = new SineGenerator(Math.PI / 2, 1, 1);
        assertEquals(1, gen.next(), 0.00001);
    }

    @Test
    public void computesSines() {
        SineGenerator gen = new SineGenerator(0.5, 0.3, 1);
        assertEquals(Math.sin(0.5), gen.next(), 0.000001);
        assertEquals(Math.sin(0.8), gen.next(), 0.000001);
        assertEquals(Math.sin(1.1), gen.next(), 0.000001);
    }

    @Test
    public void usesAmplitude() {
        SineGenerator gen = new SineGenerator(0.5, 0.3, 1.5);
        assertEquals(Math.sin(0.5) * 1.5, gen.next(), 0.000001);
        assertEquals(Math.sin(0.8) * 1.5, gen.next(), 0.000001);
        assertEquals(Math.sin(1.1) * 1.5, gen.next(), 0.000001);
    }
}
