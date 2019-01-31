package com.gurtam.antonenkoid.test.primenumbers.generation.generator.storage;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Describes entity of prime number in database.
 *
 * @author antonenkoid
 */
@Entity(tableName = "numbers")
public class NumberEntity {

    @PrimaryKey
    @ColumnInfo(name = "number")
    private int number;

    @ColumnInfo(name = "isPrime")
    private boolean isPrime;

    public NumberEntity(int number, boolean isPrime) {
        this.number = number;
        this.isPrime = isPrime;
    }

    public int getNumber() {
        return number;
    }

    public boolean isPrime() {
        return isPrime;
    }
}
