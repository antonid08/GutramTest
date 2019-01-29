package com.gurtam.antonenkoid.test.primenumbers.generator;

import com.gurtam.antonenkoid.test.utils.views.pagination.Page;

import java.util.List;

public class PrimeNumbersChunk {

    private Page page;

    private List<Integer> primeNumbers;

    public PrimeNumbersChunk(Page page, List<Integer> primeNumbers) {
        this.page = page;
        this.primeNumbers = primeNumbers;
    }

    public Page getPage() {
        return page;
    }

    public List<Integer> getPrimeNumbers() {
        return primeNumbers;
    }
}
