package com.gurtam.antonenkoid.test.primenumbers.generator.storage;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Describes entity of prime number in database.
 *
 * @author antonenkoid
 */
@Entity(tableName = "prime_numbers")
public class PrimeNumberEntity {

    @PrimaryKey
    @ColumnInfo(name = "number")
    private int number;

    public PrimeNumberEntity(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }
}
