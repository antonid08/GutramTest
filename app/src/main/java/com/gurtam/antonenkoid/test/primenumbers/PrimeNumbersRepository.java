package com.gurtam.antonenkoid.test.primenumbers;

import android.content.Context;

import com.gurtam.antonenkoid.test.AppRoomDatabase;
import com.gurtam.antonenkoid.test.primenumbers.generator.storage.NumberEntity;
import com.gurtam.antonenkoid.test.primenumbers.generator.storage.PrimeNumberEntityDao;

import java.util.List;

public class PrimeNumbersRepository {

    private PrimeNumberEntityDao primeNumberDao;

    public PrimeNumbersRepository(Context context) {
        primeNumberDao = AppRoomDatabase.getDatabase(context).primeNumberEntityDao();
    }

    public void insertNumber(NumberEntity number) {
        primeNumberDao.insert(number);
    }

    public void updateNumber(NumberEntity number) {
        primeNumberDao.update(number);
    }

    public List<NumberEntity> getPrimeNumbers(int index, int size) {
        return primeNumberDao.getNumbers(index, size);
    }

    public void clearPrimeNumbers() {
        primeNumberDao.deleteAll();
    }

}
