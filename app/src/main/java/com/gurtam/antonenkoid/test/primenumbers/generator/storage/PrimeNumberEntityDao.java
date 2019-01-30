package com.gurtam.antonenkoid.test.primenumbers.generator.storage;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

/**
 * DAO for {@link NumberEntity}
 *
 * @author antonenkoid
 */
@Dao
public interface PrimeNumberEntityDao {

    @Insert
    void insert(NumberEntity primeNumber);

    @Query("DELETE FROM numbers")
    void deleteAll();

    @Update()
    void update(NumberEntity number);

    /**
     * Get page of numbers from database
     *
     * @param index index of first record
     * @param size count of needed records
     */
    @Query("SELECT * from numbers where isPrime = 1 limit :index, :size")
    List<NumberEntity> getNumbers(int index, int size);

}
