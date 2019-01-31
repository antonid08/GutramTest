package com.gurtam.antonenkoid.test.primenumbers.history.storage;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

/**
 * DAO for {@link GenerationEntity}
 *
 * @author antonenkoid
 */
@Dao
public interface GenerationEntityDao {

    @Insert()
    void insert(GenerationEntity primeNumber);

    @Query("SELECT * from generationsHistory")
    LiveData<List<GenerationEntity>> getAll();

}
