package edu.macalester.generator;

import static org.junit.Assert.*;

import org.junit.Test;

public class ConstantGeneratorTest {
    @Test
    public void alwaysReturnsConstant() {
        ConstantGenerator gen = new ConstantGenerator(0.739);
        assertEquals(0.739, gen.next(), 0);
        assertEquals(0.739, gen.next(), 0);
        assertEquals(0.739, gen.next(), 0);
    }
}
