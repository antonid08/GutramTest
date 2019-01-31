package com.gurtam.antonenkoid.test.primenumbers.generation;

import java.util.List;

import com.gurtam.antonenkoid.test.AppRoomDatabase;
import com.gurtam.antonenkoid.test.primenumbers.generation.generator.storage.NumberEntity;
import com.gurtam.antonenkoid.test.primenumbers.generation.generator.storage.PrimeNumberEntityDao;

import android.content.Context;

/**
 * Repository for prime n
 */
public class PrimeNumbersRepository {

    private PrimeNumberEntityDao primeNumberDao;

    public PrimeNumbersRepository(Context context) {
        primeNumberDao = AppRoomDatabase.getDatabase(context).primeNumberEntityDao();
    }

    public void updateOrInsertNumber(NumberEntity number) {
        if (primeNumberDao.insert(number) == -1) {
            primeNumberDao.update(number);
        }
    }

    public void updateNumber(NumberEntity number) {
        primeNumberDao.update(number);
    }

    public int getNumbersCount() {
        return primeNumberDao.getNumbersCount();
    }


    public List<NumberEntity> getPrimeNumbers(int index, int size, int upperLimit) {
        return primeNumberDao.getNumbers(index, size, upperLimit);
    }

    public void clearPrimeNumbers() {
        primeNumberDao.deleteAll();
    }

}
