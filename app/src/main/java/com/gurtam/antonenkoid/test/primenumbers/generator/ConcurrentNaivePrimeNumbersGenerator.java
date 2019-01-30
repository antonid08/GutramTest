package com.gurtam.antonenkoid.test.primenumbers.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.gurtam.antonenkoid.test.primenumbers.PrimeNumbersRepository;
import com.gurtam.antonenkoid.test.primenumbers.generator.storage.NumberEntity;

/**
 * Generate and save sequence of prime numbers using brut force.
 *
 * @author antonenkoid
 */
public class ConcurrentNaivePrimeNumbersGenerator implements PrimeNumbersGenerator {

    private PrimeNumbersRepository repository;

    @Override
    public void generate(int limit, PrimeNumbersRepository repository) {
        this.repository = repository;

        repository.clearPrimeNumbers();

        insertEmptyValues(limit);

        int threadPoolSize = calculateThreadPoolSize(limit);
        ExecutorService threadPool = Executors.newFixedThreadPool(threadPoolSize);

        try {
            threadPool.invokeAll(createChunkProcessors(limit, threadPoolSize));
        }
        catch (InterruptedException e) {
            //todo exception
        }
    }

    private void insertEmptyValues(int limit) {
        for (int i = 2; i < limit; i++) {
            repository.insertNumber(new NumberEntity(i, false));
        }
    }

    private int calculateThreadPoolSize(int limit) {
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        return (limit - 2) < availableProcessors ? (limit - 1) : availableProcessors;
    }

    private List<Callable<Void>> createChunkProcessors(int limit, int threadPoolSize) {
        List<Callable<Void>> processors = new ArrayList<>();

        int fullChunkSize = (int) Math.ceil((double) (limit - 2) / threadPoolSize);

        for (int i = 0; i < threadPoolSize; i++) {
            int availableNumbers = (limit - i * (fullChunkSize + 1));
            int chunkSize = availableNumbers > fullChunkSize ? fullChunkSize : availableNumbers;
            int offset = i * chunkSize + 2;
            processors.add(new ProcessNumbersChunkCallable(offset, offset + chunkSize, repository));
        }

        return processors;
    }

    private boolean isPrime(int number) {
        for (int i = 2; i < number; i++) {
            if (number % i == 0) {
                return false;
            }
        }

        return true;
    }

    private class ProcessNumbersChunkCallable implements Callable<Void> {

        private int from;
        private int to;

        private PrimeNumbersRepository repository;

        ProcessNumbersChunkCallable(int from, int to, PrimeNumbersRepository repository) {
            this.from = from;
            this.to = to;
            this.repository = repository;
        }

        @Override
        public Void call() {
            for (int number = from; number < to; number++) {
                if (isPrime(number)) {
                    repository.updateNumber(new NumberEntity(number, true));
                }
            }

            return null;
        }
    }
}

