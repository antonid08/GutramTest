package com.gurtam.antonenkoid.test.utils.pagination;

/**
 * Class that represents a page in page navigation.
 *
 * @author antonenkoid
 */
public class Page {

    private final int index;
    private final int size;

    Page(int index, int size) {
        this.index = index;
        this.size = size;
    }

    /**
     * Get page index
     */
    public int getIndex() {
        return index;
    }

    /**
     * Get page size
     */
    public int getSize() {
        return size;
    }
}