package com.gurtam.antonenkoid.test.primenumbers.history.storage;

import java.util.Date;

import com.gurtam.antonenkoid.test.utils.converters.room.RoomDateConverter;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

/**
 * Entity that represents information about generation of sequence of prime numbers.
 *
 * @author antonenkoid
 */
@Entity(tableName = "generationsHistory")
@TypeConverters(RoomDateConverter.class)
public class GenerationEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "timestamp")
    private Date timestamp;

    @ColumnInfo(name = "limit")
    private int limit;

    @ColumnInfo(name = "elapsedTimeSeconds")
    private float elapsedTimeSeconds;

    public GenerationEntity(Date timestamp, int limit, float elapsedTimeSeconds) {
        this.timestamp = timestamp;
        this.limit = limit;
        this.elapsedTimeSeconds = elapsedTimeSeconds;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public int getLimit() {
        return limit;
    }

    public float getElapsedTimeSeconds() {
        return elapsedTimeSeconds;
    }
}
