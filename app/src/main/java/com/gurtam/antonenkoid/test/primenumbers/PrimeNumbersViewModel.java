package com.gurtam.antonenkoid.test.primenumbers;

import android.os.Handler;
import android.os.Looper;

import com.gurtam.antonenkoid.test.primenumbers.generator.PrimeNumbersGenerator;
import com.gurtam.antonenkoid.test.primenumbers.generator.StubPrimeNumbersGenerator;
import com.gurtam.antonenkoid.test.utils.Status;

import java.math.BigInteger;
import java.util.List;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * ViewModel for prime numbers generation screen {@link PrimeNumbersActivity}.
 *
 * @author antonenkoid
 */
public class PrimeNumbersViewModel extends ViewModel {

    private MutableLiveData<List<BigInteger>> primeNumbers;

    private MutableLiveData<Status> status;

    private PrimeNumbersGenerator primeNumbersGenerator;

    public PrimeNumbersViewModel() {
        primeNumbersGenerator = new StubPrimeNumbersGenerator(); //fixme inject

        primeNumbers = new MutableLiveData<>();
        status = new MutableLiveData<>();
    }

    MutableLiveData<List<BigInteger>> getPrimeNumbers() {
        return primeNumbers;
    }

    void generatePrimeNumbers(BigInteger limit) {
        status.setValue(Status.onStart());
        new PrimeNumberThread(limit).start();
    }

    MutableLiveData<Status> getStatus() {
        return status;
    }

    // fixme remove it
    private class PrimeNumberThread extends Thread {

        private BigInteger limit;

        PrimeNumberThread(BigInteger limit) {
            this.limit = limit;
        }

        @Override
        public void run() {
            List<BigInteger> result = primeNumbersGenerator.generate(limit);

            new Handler(Looper.getMainLooper()).post(() -> {
                primeNumbers.setValue(result);
                status.setValue(Status.onSuccess());
            });
        }
    }

}
