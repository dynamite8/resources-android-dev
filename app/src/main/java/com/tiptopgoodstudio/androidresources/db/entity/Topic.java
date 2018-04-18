package com.tiptopgoodstudio.androidresources.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Entity component required to create Room db
 * topic_table will be created automatically
 * topic_table contains list of possible Resource topics
 */

@Entity(tableName = "topic_table")
public class Topic {

    @PrimaryKey
    @NonNull
    private String topicName;

    public Topic(String topicName) {
        this.topicName = topicName;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }
}
