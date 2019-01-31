package com.gurtam.antonenkoid.test.primenumbers.history;

import java.util.List;
import java.util.concurrent.Executors;

import com.gurtam.antonenkoid.test.primenumbers.history.storage.GenerationEntity;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

/**
 * ViewModel for history of prime numbers generation screen.
 *
 * @author antonenkoid
 */
public class GenerationsHistoryViewModel extends AndroidViewModel {

    private GenerationsRepository generationsRepository;

    public GenerationsHistoryViewModel(@NonNull Application application) {
        super(application);

        generationsRepository = new GenerationsRepository(getApplication(), Executors.newSingleThreadExecutor());
    }

    public LiveData<List<GenerationEntity>> getHistory() {
        return generationsRepository.getGenerationsHistory();
    }
}
