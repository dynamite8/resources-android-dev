package com.tiptopgoodstudio.androidresources.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.tiptopgoodstudio.androidresources.db.dao.ResourceDao;
import com.tiptopgoodstudio.androidresources.db.dao.TopicDao;
import com.tiptopgoodstudio.androidresources.db.entity.Resource;
import com.tiptopgoodstudio.androidresources.db.entity.Topic;

@Database(entities = {Resource.class, Topic.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static final String RESOURCE_DATABASE = "resource_database";
    private static AppDatabase INSTANCE;

    public abstract ResourceDao resourceDao();
    public abstract TopicDao topicDao();

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, RESOURCE_DATABASE)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
