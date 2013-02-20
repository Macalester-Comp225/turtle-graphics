package edu.macalester.generator;

import static org.junit.Assert.*;

import org.junit.Test;

public class PrimeGeneratorTest {

    @Test
    public void generatorSkipsComposites() {
        PrimeGenerator gen = new PrimeGenerator(8);
        assertEquals(11, gen.next());
        assertEquals(13, gen.next());
    }

    @Test
    public void generatorIncludesStart() {
        PrimeGenerator gen = new PrimeGenerator(2);
        assertEquals(2, gen.next());
        assertEquals(3, gen.next());
        assertEquals(5, gen.next());
    }

    @Test
    public void startsAt2() {
        PrimeGenerator gen = new PrimeGenerator(-100);
        assertEquals(2, gen.next());
    }
}
