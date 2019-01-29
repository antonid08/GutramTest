package com.gurtam.antonenkoid.test.primenumbers;

import android.content.Context;

import com.gurtam.antonenkoid.test.AppRoomDatabase;
import com.gurtam.antonenkoid.test.primenumbers.generator.storage.PrimeNumberEntity;
import com.gurtam.antonenkoid.test.primenumbers.generator.storage.PrimeNumberEntityDao;

import java.util.List;

public class PrimeNumbersRepository {

    private PrimeNumberEntityDao primeNumberDao;

    public PrimeNumbersRepository(Context context) {
        primeNumberDao = AppRoomDatabase.getDatabase(context).primeNumberEntityDao();
    }

    public void insertPrimeNumber(PrimeNumberEntity number) {
        primeNumberDao.insert(number);
    }

    public List<PrimeNumberEntity> getPrimeNumbers(int index, int size) {
        return primeNumberDao.getNumbers(index, size);
    }

    public void clearPrimeNumbers() {
        primeNumberDao.deleteAll();
    }

}
