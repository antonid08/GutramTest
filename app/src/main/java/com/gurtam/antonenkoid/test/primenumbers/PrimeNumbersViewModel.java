package com.gurtam.antonenkoid.test.primenumbers;

import java.util.ArrayList;
import java.util.List;

import com.gurtam.antonenkoid.test.primenumbers.generator.ConcurrentNaivePrimeNumbersGenerator;
import com.gurtam.antonenkoid.test.primenumbers.generator.PrimeNumbersChunk;
import com.gurtam.antonenkoid.test.primenumbers.generator.PrimeNumbersGenerator;
import com.gurtam.antonenkoid.test.primenumbers.generator.storage.NumberEntity;
import com.gurtam.antonenkoid.test.utils.Status;
import com.gurtam.antonenkoid.test.utils.pagination.Page;

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

    public PrimeNumbersViewModel(@NonNull Application application) {
        super(application);

        primeNumbersRepository = new PrimeNumbersRepository(getApplication());
        primeNumbersGenerator = new ConcurrentNaivePrimeNumbersGenerator(); //fixme inject

        status = new MutableLiveData<>();
        primeNumbersChunk = new MutableLiveData<>();
    }

    void generatePrimeNumbers(int limit) {
        status.setValue(Status.onStart());
        new PrimeNumberThread(limit).start();
    }

    public MutableLiveData<PrimeNumbersChunk> getPrimeNumbersChunk() {
        return primeNumbersChunk;
    }

    void receivePrimeNumbers(Page page) {
        new GetNumbersThread(page).start();
    }

    MutableLiveData<Status> getStatus() {
        return status;
    }

    // fixme remove it
    private class PrimeNumberThread extends Thread {

        private int limit;

        PrimeNumberThread(int limit) {
            this.limit = limit;
        }

        @Override
        public void run() {
            primeNumbersGenerator.generate(limit, primeNumbersRepository);

            new Handler(Looper.getMainLooper()).post(() -> {
                status.setValue(Status.onSuccess());
            });
        }
    }

    private class GetNumbersThread extends Thread {

        private Page page;

        GetNumbersThread(Page page) {
            this.page = page;
        }

        @Override
        public void run() {
            List<NumberEntity> primeNumberEntities =
                primeNumbersRepository.getPrimeNumbers(page.getIndex(), page.getSize() + 1);

            boolean isNextPageAvailable = primeNumberEntities.size() == page.getSize() + 1;
            List<Integer> primeNumbers =
                convert(isNextPageAvailable ? primeNumberEntities.subList(0, page.getSize()) : primeNumberEntities);

            new Handler(Looper.getMainLooper()).post(() -> {
                primeNumbersChunk.setValue(new PrimeNumbersChunk(primeNumbers, page, isNextPageAvailable));
            });
        }

        private List<Integer> convert(List<NumberEntity> primeNumbers) {
            List<Integer> result = new ArrayList<>();
            for (NumberEntity numberEntity : primeNumbers) {
                result.add(numberEntity.getNumber());
            }

            return result;
        }
    }

}
