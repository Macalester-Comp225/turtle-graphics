Turtle Graphics
===============

This project invludes animated turtle graphics in Java, with some entertaining examples.

Lab exercises:

- Write some tests for a new class that does not exist yet called SteppingGenerator.
  (Use PrimeGeneratorTest as an example.) The new class accepts two inputs to the constructor, start and step
  (both doubles), and its next() method returns start, then start + step, then start + 2 * step, etc.
- Implement SteppingGenerator. Make the tests pass.
- Make a FibonacciGenerator that returns the next largest Fibonacci number each time next() is called, and make some
  tests for it. (It does not need to take any inputs in the constructor; it's OK to always start with 0, 1, ....)
- Now go back to your spiral() method, and make it take a SteppingGenerator, FibonacciGenerator or PrimeGenerator, so that
  the same code can generate any of these types of spirals.

We'll stop and discuss at this step. After this discussion, play! Some ideas:

- Make spiral() take one generator for the step size, and a second one for the turn size.
- Make a SineGenerator with a customizable frequency and angle. This is particularly entertaining to use for the turn size.
- Combine and invent your own!
