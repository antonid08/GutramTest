package com.gurtam.antonenkoid.test.primenumbers.generator;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

/**
 * Stub realization of {@link PrimeNumbersGenerator}
 */
public class StubPrimeNumbersGenerator implements PrimeNumbersGenerator {

    @Override
    public List<BigInteger> generate(BigInteger limit) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace(); //fixme
        }

        return Arrays.asList(BigInteger.valueOf(2), BigInteger.valueOf(5), BigInteger.valueOf(7));
    }
}
