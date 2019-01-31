package com.gurtam.antonenkoid.test.primenumbers.generation.generator.storage;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

/**
 * DAO for {@link NumberEntity}
 *
 * @author antonenkoid
 */
@Dao
public interface PrimeNumberEntityDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(NumberEntity primeNumber);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    void update(NumberEntity number);

    @Query("DELETE FROM numbers")
    void deleteAll();

    /**
     * Get page of numbers from database
     *
     * @param index index of first record
     * @param size count of needed records
     */
    @Query("SELECT * from numbers where isPrime = 1 and number < :upperLimit limit :index, :size")
    List<NumberEntity> getNumbers(int index, int size, int upperLimit);

    @Query("SELECT COUNT(number) FROM numbers")
    int getNumbersCount();

}
