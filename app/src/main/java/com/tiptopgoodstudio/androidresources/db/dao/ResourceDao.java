package com.tiptopgoodstudio.androidresources.db.dao;

/*
 * Dao (Data access object) is an interface that defines the database interactions
 * relating to the resource_table Room db table.
 **/

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
     * insert single Reseource object into resource_table
     *
     * @param resource
     */
    @Insert(onConflict = IGNORE)
    void insertResource(Resource resource);

    /**
     * insert multiple Resource objects into resource_table
     *
     * @param resources
     */
    @Insert(onConflict = IGNORE)
    void insertResources(Resource... resources);

    /**
     * query db for all resourcce records from resource_table
     *
     * @return all resource records in LiveData wrapper
     */
    @Query("SELECT * FROM resource_table")
    LiveData<List<Resource>> getResources();

    /**
     * query db for all resource records wihich match topic parameter from resource_table
     *
     * @param topic
     * @return all resource records wihich match topic parameter
     */
    @Query("SELECT * FROM resource_table WHERE resourceTopic LIKE :topic")
    LiveData<List<Resource>> getTopicResources(String topic);

    /**
     * query resource_table for all resource record which matches resourceId
     *
     * @param id
     * @return Resource
     */
    @Query("SELECT * FROM resource_table WHERE resourceId= :id")
    Resource getResourceById(int id);

    /**
     * query resource_table for resource record which matches resourceId
     *
     * @param id
     * @return String resource format to which the resource refers
     */
    @Query("SELECT resourceFormat FROM resource_table WHERE resourceId=:id ")
    String getResourceFormat(int id);

    /**
     * replace existing resource record in resource_table with new data
     *
     * @param resource
     */
    @Update(onConflict = REPLACE)
    void updateResource(Resource resource);

    /**
     * delete all records from db resource_table
     */
    @Query("DELETE FROM resource_table")
    void deleteAll();

    /**
     * delete single resource
     *
     * @param resource
     */
    @Delete
    void delete(Resource resource);

}
