/*
 * Author: Brandon Evans
 * File: GymLogDAO.java
 * Date: 4/15/2025
 * Description: This file represents the queries we will run in the database.
 */

package com.example.gymlog.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.gymlog.Database.Entities.GymLog;

import java.util.ArrayList;

@Dao
public interface GymLogDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(GymLog gymlog);

    @Query("Select * from " + GymLogDatabase.GYMLOG_TABLE)
    ArrayList<GymLog> getAllRecords();
}
