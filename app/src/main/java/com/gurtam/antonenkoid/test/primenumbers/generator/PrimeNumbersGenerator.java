package com.gurtam.antonenkoid.test.primenumbers.generator;

import java.math.BigInteger;
import java.util.List;

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
    List<BigInteger> generate(BigInteger limit);

}
