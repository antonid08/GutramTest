package com.gurtam.antonenkoid.test;

import com.gurtam.antonenkoid.test.primenumbers.generation.generator.storage.NumberEntity;
import com.gurtam.antonenkoid.test.primenumbers.generation.generator.storage.PrimeNumberEntityDao;
import com.gurtam.antonenkoid.test.primenumbers.history.storage.GenerationEntity;
import com.gurtam.antonenkoid.test.primenumbers.history.storage.GenerationEntityDao;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {NumberEntity.class, GenerationEntity.class}, version = 1)
public abstract class AppRoomDatabase extends RoomDatabase {

    private static volatile AppRoomDatabase INSTANCE;

    public static AppRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        AppRoomDatabase.class, "app_database")
                        .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract PrimeNumberEntityDao primeNumberEntityDao();

    public abstract GenerationEntityDao generationEntityDao();

}
