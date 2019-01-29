package com.gurtam.antonenkoid.test.primenumbers.generator;

/**
 * Generates prime numbers from 2 to passed limitInput.
 *
 * @author antonenkoid
 */
public interface PrimeNumbersGenerator {

    /**
     * Generate sequence of prime numbers.
     *
     * @param limit upper limitInput for prime numbers
     * @return list of prime numbers from 2 to limitInput
     */
    void generate(int limit);

}
