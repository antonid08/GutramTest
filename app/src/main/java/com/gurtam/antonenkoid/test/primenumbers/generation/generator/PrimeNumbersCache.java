package com.gurtam.antonenkoid.test.primenumbers.generation.generator;

import com.gurtam.antonenkoid.test.primenumbers.generation.PrimeNumbersRepository;
import com.gurtam.antonenkoid.test.primenumbers.generation.generator.storage.NumberEntity;

/**
 * Cache for prime numbers generation algorithm. Based on {@link PrimeNumbersRepository}
 *
 *  todo javadocs
 * @author antonenkoid
 */
public class PrimeNumbersCache {

    private PrimeNumbersRepository numbersRepository;

    public PrimeNumbersCache(PrimeNumbersRepository numbersRepository) {
        this.numbersRepository = numbersRepository;
    }

    int getCachedNumbersCount() {
        return numbersRepository.getNumbersCount();
    }

    void put(int number, boolean isPrime) {
        numbersRepository.updateOrInsertNumber(new NumberEntity(number, isPrime));
    }

    public void clear() {
        numbersRepository.clearPrimeNumbers();
    }


}
