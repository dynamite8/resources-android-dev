package com.tiptopgoodstudio.androidresources.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.tiptopgoodstudio.androidresources.db.entity.Topic;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

/**
 * Dao (Data access object) is an interface that defines the database interactions
 * relating to the topic_table Room db table.
 **/

@Dao
public interface TopicDao {

    /**
     * insert single Topic object into topic_table
     * @param topic
     */
    @Insert(onConflict = IGNORE)
    void insertTopic(Topic topic);

    /**
     * insert multiple Topic objects into topic_table
     * @param topics
     */
    @Insert(onConflict = IGNORE)
    void insertTopics(Topic... topics);

    /**
     * query db for all topic records from topic_table
     * @return List<Topic> of all topics in db
     */
    @Query("SELECT * FROM topic_table ORDER BY topicName ASC")
    List<Topic> getAllTopicsList();

    /**
     * delete all records from db topic_table
     */
    @Query("DELETE FROM topic_table")
    void deleteAllTopics();

    /**
     * delete single topic
     * @param topic
     */
    @Delete
    void delete(Topic topic);

}

