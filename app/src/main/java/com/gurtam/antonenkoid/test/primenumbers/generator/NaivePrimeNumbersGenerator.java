package com.gurtam.antonenkoid.test.primenumbers.generator;

import com.gurtam.antonenkoid.test.primenumbers.PrimeNumbersRepository;
import com.gurtam.antonenkoid.test.primenumbers.generator.storage.PrimeNumberEntity;

/**
 * Generate and save sequence of prime numbers using brut force.
 */
public class NaivePrimeNumbersGenerator implements PrimeNumbersGenerator {

    @Override
    public void generate(int limit, PrimeNumbersRepository repository) {
        repository.clearPrimeNumbers();

        for (int number = 2; number < limit; number++) {
            if (isPrime(number)) {
                repository.insertPrimeNumber(new PrimeNumberEntity(number));
            }
        }
    }

    private boolean isPrime(int number) {
        for (int i = 2; i < number; i++) {
            if (number % i == 0) {
                return false;
            }
        }

        return true;
    }
}

