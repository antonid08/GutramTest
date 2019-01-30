package com.gurtam.antonenkoid.test.utils.pagination;

/**
 * Encapsulates info about current page and provides ability to switch between pages.
 *
 * @author antonenkoid
 */
public class PaginationManager {

    private static final int DEFAULT_PAGE_INDEX = 0;

    private static final int DEFAULT_PAGE_SIZE = 100;

    private Page currentPage;

    public PaginationManager() {
        this(DEFAULT_PAGE_INDEX);
    }

    public PaginationManager(int initialPageIndex) {
        this(initialPageIndex, DEFAULT_PAGE_SIZE);
    }

    public PaginationManager(int initialPageIndex, int initialPageSize) {
        this.currentPage = new Page(initialPageIndex, initialPageSize);
    }

    /**
     * Switch to next page.
     *
     * @return new page
     */
    public Page nextPage() {
        return currentPage = new Page(currentPage.getIndex() + currentPage.getSize(), currentPage.getSize());
    }

    /**
     * Switch to previous page.
     *
     * @return new page
     */
    public Page previousPage() {
        return currentPage = new Page(currentPage.getIndex() - currentPage.getSize(), currentPage.getSize());
    }

    /**
     * Returns current page.
     */
    public Page getCurrentPage() {
        return currentPage;
    }

}
