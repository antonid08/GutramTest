package com.gurtam.antonenkoid.test.primenumbers;

import java.util.ArrayList;
import java.util.List;

import com.gurtam.antonenkoid.test.primenumbers.generator.ConcurrentNaivePrimeNumbersGenerator;
import com.gurtam.antonenkoid.test.primenumbers.generator.PrimeNumbersCache;
import com.gurtam.antonenkoid.test.primenumbers.generator.PrimeNumbersChunk;
import com.gurtam.antonenkoid.test.primenumbers.generator.PrimeNumbersGenerator;
import com.gurtam.antonenkoid.test.primenumbers.generator.storage.NumberEntity;
import com.gurtam.antonenkoid.test.utils.BaseAsyncTask;
import com.gurtam.antonenkoid.test.utils.Status;
import com.gurtam.antonenkoid.test.utils.pagination.Page;
import com.gurtam.antonenkoid.test.utils.pagination.PaginationManager;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

/**
 * ViewModel for prime numbers generation screen {@link PrimeNumbersActivity}.
 *
 * @author antonenkoid
 */
public class PrimeNumbersViewModel extends AndroidViewModel {

    private MutableLiveData<PrimeNumbersChunk> primeNumbersChunk;

    private MutableLiveData<Status> status;

    private PrimeNumbersRepository primeNumbersRepository;

    private PrimeNumbersGenerator primeNumbersGenerator;

    private PrimeNumbersCache primeNumbersCache;

    private PaginationManager paginationManager;

    private int currentUpperLimit;

    private TimeTracker generatingTimeTracker;

    public PrimeNumbersViewModel(@NonNull Application application) {
        super(application);

        //fixme inject
        primeNumbersRepository = new PrimeNumbersRepository(getApplication());
        primeNumbersCache = new PrimeNumbersCache(primeNumbersRepository);
        primeNumbersGenerator = new ConcurrentNaivePrimeNumbersGenerator(primeNumbersCache);

        status = new MutableLiveData<>();
        primeNumbersChunk = new MutableLiveData<>();

        paginationManager = new PaginationManager();

        generatingTimeTracker = new TimeTracker();
    }

    void generatePrimeNumbers(int limit) {
        currentUpperLimit = limit;
        status.setValue(Status.onStart());
        new PrimeNumberAsyncTask(limit).execute();
    }

    MutableLiveData<PrimeNumbersChunk> getPrimeNumbersChunk() {
        return primeNumbersChunk;
    }

    void receiveFirstPrimeNumbersPage() {
        paginationManager = new PaginationManager();
        new GetNumbersAsyncTask(paginationManager.getCurrentPage(), currentUpperLimit).execute();
    }

    void receivePreviousPrimeNumbersPage() {
        new GetNumbersAsyncTask(paginationManager.previousPage(), currentUpperLimit).execute();
    }

    void receiveNextPrimeNumbersPage() {
        new GetNumbersAsyncTask(paginationManager.nextPage(), currentUpperLimit).execute();
    }

    MutableLiveData<Status> getStatus() {
        return status;
    }

    void clearPrimeNumbersCache() {
        new ClearCacheAsyncTask().execute();
    }

    TimeTracker getGeneratingTimeTracker() {
        return generatingTimeTracker;
    }

    // fixme remove it
    private class PrimeNumberAsyncTask extends BaseAsyncTask<Void, Void> {

        private int limit;

        PrimeNumberAsyncTask(int limit) {
            this.limit = limit;
        }

        @Override
        protected Void asyncOperation(Void... voids) throws Exception {
            primeNumbersGenerator.generate(limit);
            return null;
        }

        @Override
        protected void onSuccess(Void aVoid) {
            status.setValue(com.gurtam.antonenkoid.test.utils.Status.onSuccess());
        }

        @Override
        protected void onFail(Exception e) {
            // todo
        }
    }

    private class GetNumbersAsyncTask extends BaseAsyncTask<Void, PrimeNumbersChunk> {

        private Page page;

        private int upperLimit;

        public GetNumbersAsyncTask(Page page, int upperLimit) {
            this.page = page;
            this.upperLimit = upperLimit;
        }

        @Override
        protected PrimeNumbersChunk asyncOperation(Void... voids) throws Exception {
            List<NumberEntity> primeNumberEntities =
                primeNumbersRepository.getPrimeNumbers(page.getIndex(), page.getSize() + 1, upperLimit);

            boolean isNextPageAvailable = primeNumberEntities.size() == page.getSize() + 1;
            List<Integer> primeNumbers =
                convert(isNextPageAvailable ? primeNumberEntities.subList(0, page.getSize()) : primeNumberEntities);

            return new PrimeNumbersChunk(primeNumbers, page, isNextPageAvailable);
        }


        @Override
        protected void onSuccess(PrimeNumbersChunk numbersChunk) {
            primeNumbersChunk.setValue(numbersChunk);
        }

        @Override
        protected void onFail(Exception e) {
            // todo
        }

        private List<Integer> convert(List<NumberEntity> primeNumbers) {
            List<Integer> result = new ArrayList<>();
            for (NumberEntity numberEntity : primeNumbers) {
                result.add(numberEntity.getNumber());
            }

            return result;
        }
    }

    private class ClearCacheAsyncTask extends BaseAsyncTask<Void, Void> {

        @Override
        protected Void asyncOperation(Void... voids) throws Exception {
            primeNumbersCache.clear();
            return null;
        }

        @Override
        protected void onSuccess(Void aVoid) {
            status.setValue(com.gurtam.antonenkoid.test.utils.Status.onSuccess());
        }

        @Override
        protected void onFail(Exception e) {

        }
    }

}
