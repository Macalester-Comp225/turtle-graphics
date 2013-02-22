package edu.macalester.generator;


public class PrimeGenerator extends Generator {
    private int cur;
    
    public PrimeGenerator(int start) {
        cur = start - 1;  // make start the next number tested
    }
    
    public double next() {
        while(true) {
            cur++;
            if(isPrime(cur))
                return cur;
        }
    }

    private boolean isPrime(int n) {
        if(n <= 1)
            return false;
        for(int i = 2; i <= n/2; i++)
            if(n % i == 0)
                return false;
        return true;
    }
}
