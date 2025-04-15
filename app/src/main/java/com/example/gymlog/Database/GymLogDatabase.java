/*
 * Author: Brandon Evans
 * File: GymLogDatabase.java
 * Date: 4/15/2025
 * Description:
 */

package com.example.gymlog.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.gymlog.Database.Entities.GymLog;

@Database(entities = {GymLog.class}, version = 1, exportSchema = false)
public abstract class GymLogDatabase extends RoomDatabase {

}
