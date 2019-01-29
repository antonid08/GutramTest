package com.gurtam.antonenkoid.test.primenumbers.generator;

import com.gurtam.antonenkoid.test.primenumbers.PrimeNumbersRepository;
import com.gurtam.antonenkoid.test.primenumbers.generator.storage.PrimeNumberEntity;

public class DummyPrimeNumbersGenerator implements PrimeNumbersGenerator {

    private PrimeNumbersRepository primeNumbersRepository;

    public DummyPrimeNumbersGenerator(PrimeNumbersRepository primeNumbersRepository) {
        this.primeNumbersRepository = primeNumbersRepository;
    }

    @Override
    public void generate(int limit) {
        primeNumbersRepository.clearPrimeNumbers();

        for (int number = 2; number < limit; number++) {
            if (isPrime(number)) {
                primeNumbersRepository.insertPrimeNumber(new PrimeNumberEntity(number));
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

