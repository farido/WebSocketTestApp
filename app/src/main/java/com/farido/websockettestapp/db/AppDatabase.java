package com.farido.websockettestapp.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.farido.websockettestapp.db.dao.CompanyDao;
import com.farido.websockettestapp.db.entity.CompanyDBEntity;

@Database(entities = {CompanyDBEntity.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "AppDatabase.db";
    private static volatile AppDatabase instance;
    private static final Object LOCK = new Object();

    public abstract CompanyDao companyDao();

    public static AppDatabase getInstance(Context context) {
        if(instance == null){
            synchronized (LOCK){
                if(instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME)
                            //.addMigrations(MIGRATION_1_2)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return instance;
    }
}
