package com.tiptopgoodstudio.androidresources.db.entity;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Entity component required to create Room db
 * resource_table will be created automatically
 */

@Entity(tableName = "resource_table")
public class Resource {




    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int resourceId;
    private String resourceTopic;
    private String resourceDescription;
    private String resourceUrl;

    public String getResourceFormat() {
        return resourceFormat;
    }

    public void setResourceFormat(String resourceFormat) {
        this.resourceFormat = resourceFormat;
    }

    private String resourceFormat;

    public Resource(String resourceTopic, String resourceDescription, String resourceUrl) {
        this.resourceTopic = resourceTopic;
        this.resourceDescription = resourceDescription;
        this.resourceUrl = resourceUrl;
    }

    @NonNull
    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(@NonNull int resourceId) {
        this.resourceId = resourceId;
    }

    public String getResourceTopic() {
        return resourceTopic;
    }

    public void setResourceTopic(String resourceTopic) {
        this.resourceTopic = resourceTopic;
    }

    public String getResourceDescription() {
        return resourceDescription;
    }

    public void setResourceDescription(String resourceDescription) {
        this.resourceDescription = resourceDescription;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }
}
