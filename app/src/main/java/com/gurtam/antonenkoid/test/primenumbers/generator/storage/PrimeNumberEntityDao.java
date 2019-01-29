package com.gurtam.antonenkoid.test.primenumbers.generator.storage;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface PrimeNumberEntityDao {

    @Insert
    void insert(PrimeNumberEntity primeNumber);

    @Query("DELETE FROM prime_numbers")
    void deleteAll();

    @Query("SELECT * from prime_numbers limit :index, :size")
    List<PrimeNumberEntity> getNumbers(int index, int size);

}
