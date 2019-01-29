package com.gurtam.antonenkoid.test.primenumbers.generator;

import java.util.Arrays;
import java.util.List;

/**
 * Stub realization of {@link PrimeNumbersGenerator}
 */
public class StubPrimeNumbersGenerator implements PrimeNumbersGenerator {

    @Override
    public void generate(int limit) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace(); //fixme
        }

    }
}
