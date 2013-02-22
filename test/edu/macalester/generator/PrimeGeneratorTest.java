package edu.macalester.generator;

import static org.junit.Assert.*;

import org.junit.Test;

public class PrimeGeneratorTest {

    @Test
    public void generatorSkipsComposites() {
        PrimeGenerator gen = new PrimeGenerator(8);
        assertEquals(11, gen.next(), 0.0001);
        assertEquals(13, gen.next(), 0.0001);
    }

    @Test
    public void generatorIncludesStart() {
        PrimeGenerator gen = new PrimeGenerator(2);
        assertEquals(2, gen.next(), 0.0001);
        assertEquals(3, gen.next(), 0.0001);
        assertEquals(5, gen.next(), 0.0001);
    }

    @Test
    public void startsAt2() {
        PrimeGenerator gen = new PrimeGenerator(-100);
        assertEquals(2, gen.next(), 0.0001);
    }
}
