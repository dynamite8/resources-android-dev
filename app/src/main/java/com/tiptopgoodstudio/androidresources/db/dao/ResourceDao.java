package com.tiptopgoodstudio.androidresources.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.tiptopgoodstudio.androidresources.db.entity.Resource;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;
import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface ResourceDao {

    /**
     * Dao (Data access object) is an interface that define the database interactions.
     * Room will generate an implementation during the run time.
     **/
    @Insert(onConflict = IGNORE)
    void insertResource(Resource resource);

    @Insert(onConflict = IGNORE)
    void insertResources(Resource... resources);


    @Query("SELECT * FROM resource_table")
    LiveData<List<Resource>> getResources();

    @Query("SELECT * FROM resource_table WHERE resourceTopic LIKE :topic")
    LiveData<List<Resource>> getTopicResources(String topic);

    @Query("SELECT * FROM resource_table WHERE resourceId= :id")
    Resource getResourceById(int id);


    @Update(onConflict = REPLACE)
    void updateResource(Resource resource);

    @Query("DELETE FROM resource_table")
    void deleteAll();

    @Delete
    void delete(Resource resource);

    @Query("SELECT resourceFormat FROM resource_table WHERE resourceId=:id ")
    String getResourceFormat(int id);

}
