package com.gurtam.antonenkoid.test.primenumbers.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Generates sequence of prime numbers using brut force and stores it in {@link PrimeNumbersCache}.
 *
 * <p>Uses cache when generating.</p>
 *
 * @author antonenkoid
 */
public class ConcurrentNaivePrimeNumbersGenerator implements PrimeNumbersGenerator {

    private PrimeNumbersCache cache;

    public ConcurrentNaivePrimeNumbersGenerator(PrimeNumbersCache cache) {
        this.cache = cache;
    }

    @Override
    public void generate(int limit) {
        if (limit < 2) {
            return;
        }

        final int cachedNumbersCount = cache.getCachedNumbersCount();

        if (cachedNumbersCount >= limit) {
            return;
        }

        insertEmptyValues(cachedNumbersCount, limit);

        int threadPoolSize = calculateThreadPoolSize(cachedNumbersCount, limit);
        ExecutorService threadPool = Executors.newFixedThreadPool(threadPoolSize);

        try {
            threadPool.invokeAll(createChunkProcessors(cachedNumbersCount, limit, threadPoolSize));
        }
        catch (InterruptedException e) {
            throw new IllegalStateException();
        }
    }

    private void insertEmptyValues(int cachedNumbersCount, int limit) {
        for (int i = cachedNumbersCount; i < limit; i++) {
            cache.put(i, false);
        }
    }

    private int calculateThreadPoolSize(int cachedNumbersCount, int limit) {
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        int itemsToProcessCount = limit - cachedNumbersCount;

        return itemsToProcessCount < availableProcessors ? itemsToProcessCount : availableProcessors;
    }

    private List<Callable<Void>> createChunkProcessors(int cachedNumbersCount, int limit, int threadPoolSize) {
        List<Callable<Void>> processors = new ArrayList<>();

        int fullChunkSize = (int) Math.ceil((double) (limit - cachedNumbersCount) / threadPoolSize);

        for (int i = 0; i < threadPoolSize; i++) {
            int numbersLeftToProcess = (limit - i * fullChunkSize - cachedNumbersCount);
            int chunkSize = numbersLeftToProcess > fullChunkSize ? fullChunkSize : numbersLeftToProcess;
            int offset = i * fullChunkSize + cachedNumbersCount;
            processors.add(new ProcessNumbersChunkCallable(offset, offset + chunkSize, cache));
        }

        return processors;
    }

    private boolean isPrime(int number) {
        if (number == 0 || number == 1) {
            return false;
        }

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

        private PrimeNumbersCache cache;

        ProcessNumbersChunkCallable(int from, int to, PrimeNumbersCache cache) {
            this.from = from;
            this.to = to;
            this.cache = cache;
        }

        @Override
        public Void call() {
            for (int number = from; number < to; number++) {
                if (isPrime(number)) {
                    cache.put(number, true);
                }
            }

            return null;
        }
    }
}

