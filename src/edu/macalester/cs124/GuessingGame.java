package edu.macalester.cs124;

import acm.program.ConsoleProgram;
import edu.macalester.generator.Generator;
import edu.macalester.generator.PrimeGenerator;

public class GuessingGame extends ConsoleProgram {
    public void run() {
        playGuessingGame(new PrimeGenerator(2));
    }

    private void playGuessingGame(Generator gen) {
        println("The first few numbers in the sequence are:");
        for(int n = 0; n < 4; n++)
            println(gen.next());
        double answer = gen.next();
        double guess = readDouble("What is the next number? ");
        if(answer == guess)
            println("Correct!");
        else
            println("No, it is " + answer + ".");
    }

}
