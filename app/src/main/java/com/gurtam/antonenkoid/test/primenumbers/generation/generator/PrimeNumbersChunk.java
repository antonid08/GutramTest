package com.gurtam.antonenkoid.test.primenumbers.generation.generator;

import java.util.List;

import com.gurtam.antonenkoid.test.utils.pagination.Page;

/**
 * Represents list of prime numbers and info about current page of numbers.
 *
 * @author antonenkoid
 */
public class PrimeNumbersChunk {

    private Page page;

    private List<Integer> numbers;

    private boolean isNextPageAvailable;

    public PrimeNumbersChunk(List<Integer> numbers, Page page, boolean isNextPageAvailable) {
        this.numbers = numbers;
        this.page = page;
        this.isNextPageAvailable = isNextPageAvailable;
    }

    public List<Integer> getNumbers() {
        return numbers;
    }

    public Page getPage() {
        return page;
    }

    public boolean isNextPageAvailable() {
        return isNextPageAvailable;
    }

}
