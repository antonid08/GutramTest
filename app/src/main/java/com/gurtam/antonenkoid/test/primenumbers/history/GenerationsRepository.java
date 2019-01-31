package com.gurtam.antonenkoid.test.primenumbers.history;

import java.util.List;
import java.util.concurrent.Executor;

import com.gurtam.antonenkoid.test.AppRoomDatabase;
import com.gurtam.antonenkoid.test.primenumbers.history.storage.GenerationEntity;
import com.gurtam.antonenkoid.test.primenumbers.history.storage.GenerationEntityDao;

import android.content.Context;
import androidx.lifecycle.LiveData;

/**
 * Repository for history of prime numbers generations.
 *
 * @author antonenkoid
 */
public class GenerationsRepository {

    private GenerationEntityDao generationsDao;

    private Executor executor;

    public GenerationsRepository(Context context, Executor executor) {
        generationsDao = AppRoomDatabase.getDatabase(context).generationEntityDao();
        this.executor = executor;
    }

    public void addItem(GenerationEntity entity) {
        executor.execute(() -> generationsDao.insert(entity));
    }

    public LiveData<List<GenerationEntity>> getGenerationsHistory() {
        return generationsDao.getAll();
    }

}
