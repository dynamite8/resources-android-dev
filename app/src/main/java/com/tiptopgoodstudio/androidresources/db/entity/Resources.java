package com.tiptopgoodstudio.androidresources.db.entity;

/**
 * Created by jchangho on 3/24/18.
 */

public class Resources {
    String description;
    String fullName;
    String resourceTopic;
    String resourceURL;
    String timestamp;

    // constructors

    public Resources() {
    }

    public Resources(String description, String fullName, String resourceTopic, String resourceURL, String timestamp) {
        this.description = description;
        this.fullName = fullName;
        this.resourceTopic = resourceTopic;
        this.resourceURL = resourceURL;
        this.timestamp = timestamp;
    }

    // getters

    public String getDescription() {
        return description;
    }

    public String getFullName() {
        return fullName;
    }

    public String getResourceTopic() {
        return resourceTopic;
    }

    public String getResourceURL() {
        return resourceURL;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
