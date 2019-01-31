package com.gurtam.antonenkoid.test.primenumbers.generation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import com.gurtam.antonenkoid.test.R;
import com.gurtam.antonenkoid.test.primenumbers.generation.generator.ConcurrentNaivePrimeNumbersGenerator;
import com.gurtam.antonenkoid.test.primenumbers.generation.generator.GenerationProgressListener;
import com.gurtam.antonenkoid.test.primenumbers.generation.generator.GenerationTimeoutException;
import com.gurtam.antonenkoid.test.primenumbers.generation.generator.PrimeNumbersCache;
import com.gurtam.antonenkoid.test.primenumbers.generation.generator.PrimeNumbersChunk;
import com.gurtam.antonenkoid.test.primenumbers.generation.generator.PrimeNumbersGenerator;
import com.gurtam.antonenkoid.test.primenumbers.generation.generator.storage.NumberEntity;
import com.gurtam.antonenkoid.test.primenumbers.history.GenerationsRepository;
import com.gurtam.antonenkoid.test.primenumbers.history.storage.GenerationEntity;
import com.gurtam.antonenkoid.test.utils.BaseWeakReferenceAsyncTask;
import com.gurtam.antonenkoid.test.utils.Status;
import com.gurtam.antonenkoid.test.utils.pagination.Page;
import com.gurtam.antonenkoid.test.utils.pagination.PaginationManager;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
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

    private GenerationsRepository generationsRepository;

    private MutableLiveData<Integer> generatingProgress;

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

        generationsRepository = new GenerationsRepository(getApplication(), Executors.newSingleThreadExecutor());

        generatingProgress = new MutableLiveData<>();
    }

    void generatePrimeNumbers(int limit) {
        currentUpperLimit = limit;
        status.setValue(Status.onStart());
        new GeneratePrimeNumbersAsyncTask(this, limit, new GenerationPercentageListener()).execute();
    }

    MutableLiveData<PrimeNumbersChunk> getPrimeNumbersChunk() {
        return primeNumbersChunk;
    }

    void receiveFirstPrimeNumbersPage() {
        paginationManager = new PaginationManager();
        new ReceivePrimeNumbersAsyncTask(this, paginationManager.getCurrentPage(), currentUpperLimit).execute();
    }

    void receivePreviousPrimeNumbersPage() {
        new ReceivePrimeNumbersAsyncTask(this, paginationManager.previousPage(), currentUpperLimit).execute();
    }

    void receiveNextPrimeNumbersPage() {
        new ReceivePrimeNumbersAsyncTask(this, paginationManager.nextPage(), currentUpperLimit).execute();
    }

    MutableLiveData<Status> getStatus() {
        return status;
    }

    void clearPrimeNumbersCache() {
        new ClearCacheAsyncTask(this).execute();
    }

    TimeTracker getGeneratingTimeTracker() {
        return generatingTimeTracker;
    }

    void addGenerationToHistory(float elapsedTimeSeconds) {
        generationsRepository.
            addItem(new GenerationEntity(generatingTimeTracker.getStartTime(), currentUpperLimit, elapsedTimeSeconds));
    }

    MutableLiveData<Integer> getGeneratingProgress() {
        return generatingProgress;
    }


    private static class GeneratePrimeNumbersAsyncTask extends BaseWeakReferenceAsyncTask<PrimeNumbersViewModel, Void, Void> {

        private int limit;
        private GenerationProgressListener progressListener;

        GeneratePrimeNumbersAsyncTask(PrimeNumbersViewModel viewModel, int limit, GenerationProgressListener progressListener) {
            super(viewModel);
            this.limit = limit;
            this.progressListener = progressListener;
        }

        @Override
        protected Void executeAsyncOperation(Void... voids) throws Exception {
            getReference().primeNumbersGenerator.
                generate(limit, progressListener);
            return null;
        }


        @Override
        protected void success(Void aVoid) {
            getReference().status.setValue(com.gurtam.antonenkoid.test.utils.Status.onSuccess());
        }

        @Override
        protected void fail(Exception e) {
            if (e instanceof GenerationTimeoutException) {
                getReference().status.setValue(com.gurtam.antonenkoid.test.utils.Status.onError(
                    getReference().getApplication().getString(R.string.generation_timeout_message)));
            } else {
                throw new RuntimeException(e);
            }
        }


    }

    private static class ReceivePrimeNumbersAsyncTask
        extends BaseWeakReferenceAsyncTask<PrimeNumbersViewModel, Void, PrimeNumbersChunk> {

        private Page page;

        private int upperLimit;

        ReceivePrimeNumbersAsyncTask(PrimeNumbersViewModel viewModel, Page page, int upperLimit) {
            super(viewModel);
            this.page = page;
            this.upperLimit = upperLimit;
        }

        @Override
        protected PrimeNumbersChunk executeAsyncOperation(Void... voids) throws Exception {
            List<NumberEntity> primeNumberEntities =
                getReference().primeNumbersRepository.getPrimeNumbers(page.getIndex(), page.getSize() + 1, upperLimit);

            boolean isNextPageAvailable = primeNumberEntities.size() == page.getSize() + 1;
            List<Integer> primeNumbers =
                convert(isNextPageAvailable ? primeNumberEntities.subList(0, page.getSize()) : primeNumberEntities);

            return new PrimeNumbersChunk(primeNumbers, page, isNextPageAvailable);
        }


        @Override
        protected void success(PrimeNumbersChunk numbersChunk) {
            getReference().primeNumbersChunk.setValue(numbersChunk);
        }

        @Override
        protected void fail(Exception e) {
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

    private static class ClearCacheAsyncTask extends BaseWeakReferenceAsyncTask<PrimeNumbersViewModel, Void, Void> {

        ClearCacheAsyncTask(PrimeNumbersViewModel viewModel) {
            super(viewModel);
        }

        @Override
        protected Void executeAsyncOperation(Void... voids) throws Exception {
            getReference().primeNumbersCache.clear();
            return null;
        }

        @Override
        protected void success(Void aVoid) {
            getReference().status.setValue(com.gurtam.antonenkoid.test.utils.Status.onSuccess());
        }

        @Override
        protected void fail(Exception e) {

        }
    }

    private class GenerationPercentageListener implements GenerationProgressListener {

        @Override
        public void percentsGenerated(int percents) {
            new Handler(Looper.getMainLooper()).post(() -> generatingProgress.setValue(percents));
        }
    }

}
