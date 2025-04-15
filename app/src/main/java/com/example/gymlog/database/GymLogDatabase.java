/*
 * Author: Brandon Evans
 * File: GymLogDatabase.java
 * Date: 4/15/2025
 * Description:
 */

package com.example.gymlog.database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.gymlog.MainActivity;
import com.example.gymlog.database.entities.GymLog;
import com.example.gymlog.database.entities.User;
import com.example.gymlog.database.typeConverter.LocalDateTypeConverter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@TypeConverters(LocalDateTypeConverter.class)
@Database(entities = {GymLog.class, User.class}, version = 1, exportSchema = false)
public abstract class GymLogDatabase extends RoomDatabase {
    public static final String USER_TABLE = "usertable";
    private static final String DATABASE_NAME = "GymLogDatabase";
    public static final String GYMLOG_TABLE = "gymLogTable";

    private static volatile GymLogDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;

    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static GymLogDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (GymLogDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            GymLogDatabase.class,
                            DATABASE_NAME)
                            .fallbackToDestructiveMigration().addCallback(addDefaultValues).build();
                }
            }
        }

        return INSTANCE;
    }

    private static final RoomDatabase.Callback addDefaultValues = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            Log.i(MainActivity.TAG, "DATABASE CREATED!");
            databaseWriteExecutor.execute(() -> {
                UserDAO userDao = INSTANCE.userDAO();
                userDao.deleteAll();
                User admin = new User("admin1", "admin1");
                admin.setAdmin(true);
                userDao.insert(admin);

                User testUser1 = new User("testuser1","testuser1");
                userDao.insert(testUser1);
            });
        }
    };

    public abstract GymLogDAO gymLogDAO();

    public abstract UserDAO userDAO();
}
