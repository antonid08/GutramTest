package com.gurtam.antonenkoid.test.primenumbers.generation.generator;

/**
 * Generates prime numbers from 2 to passed limitInput and saves they to repository.
 *
 * @author antonenkoid
 */
public interface PrimeNumbersGenerator {

    /**
     * Generate sequence of prime numbers and store it in cache.
     *
     * @param limit upper limitInput for prime numbers
     * @param progressListener progress listener of generation
     */
    void generate(int limit, GenerationProgressListener progressListener) throws GenerationTimeoutException;

}
