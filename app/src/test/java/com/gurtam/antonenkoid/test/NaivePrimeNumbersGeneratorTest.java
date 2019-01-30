package com.gurtam.antonenkoid.test;

import org.junit.Test;
import org.mockito.Mockito;

import com.gurtam.antonenkoid.test.primenumbers.PrimeNumbersRepository;
import com.gurtam.antonenkoid.test.primenumbers.generator.ConcurrentNaivePrimeNumbersGenerator;
import com.gurtam.antonenkoid.test.primenumbers.generator.PrimeNumbersCache;

public class NaivePrimeNumbersGeneratorTest {

    @Test
    public void testGeneration() {
        int count = 300000;
        long before = System.currentTimeMillis();
        new ConcurrentNaivePrimeNumbersGenerator(Mockito.mock(PrimeNumbersCache.class)).generate(count);
        long after = System.currentTimeMillis();

        double seconds = (float)(after - before) / 1000;
        System.out.println(String.format("Count: %s, time in seconds %s", count, seconds));
    }

}
