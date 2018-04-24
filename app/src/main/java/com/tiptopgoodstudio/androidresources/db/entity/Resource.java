package com.tiptopgoodstudio.androidresources.db.entity;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Entity component required to create Room db
 * resource_table will be created automatically
 */

@Entity(tableName = "resource_table", indices = {@Index(value = "resourceUrl", unique = true)})
public class Resource {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int resourceId;
    private String resourceTopic;
    private String resourceDescription;
    private String resourceUrl;
    private String resourceFormat;

    // Adding an empty constructor in order to work with Firebase importing data into this POJO
    // By Divya on 4/24/2018
    public Resource() {

    }

    public Resource(String resourceTopic, String resourceDescription, String resourceUrl, String resourceFormat) {
        this.resourceTopic = resourceTopic;
        this.resourceDescription = resourceDescription;
        this.resourceUrl = resourceUrl;
        this.resourceFormat = resourceFormat;
    }

    @Ignore
    public Resource(String resourceTopic, String resourceDescription, String resourceUrl) {
        this(resourceTopic, resourceDescription, resourceUrl, null);
    }

    @NonNull
    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(@NonNull int resourceId) {
        //this method intentionally left blank
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

    public String getResourceFormat() {
        return resourceFormat;
    }

    public void setResourceFormat(String resourceFormat) {
        this.resourceFormat = resourceFormat;
    }

}
