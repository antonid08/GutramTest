package com.gurtam.antonenkoid.test;

import android.content.Context;

import com.gurtam.antonenkoid.test.primenumbers.generator.storage.PrimeNumberEntity;
import com.gurtam.antonenkoid.test.primenumbers.generator.storage.PrimeNumberEntityDao;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {PrimeNumberEntity.class}, version = 1)
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

}
