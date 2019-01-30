package com.gurtam.antonenkoid.test.primenumbers.generator;

import com.gurtam.antonenkoid.test.primenumbers.PrimeNumbersRepository;

/**
 * Generates prime numbers from 2 to passed limitInput and saves they to repository.
 *
 * @author antonenkoid
 */
public interface PrimeNumbersGenerator {

    /**
     * Generate sequence of prime numbers and save it.
     *
     * @param limit upper limitInput for prime numbers
     * @param repository repository to store generated prime numbers
     *
     * @return list of prime numbers from 2 to limitInput
     */
    void generate(int limit, PrimeNumbersRepository repository);

}
