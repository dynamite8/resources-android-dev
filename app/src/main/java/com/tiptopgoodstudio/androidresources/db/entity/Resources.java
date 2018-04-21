package com.tiptopgoodstudio.androidresources.db.entity;

/**
 * Created by jchangho on 3/24/18.
 *
 * Added a variable format and added a getter method for it by Divya on 4/20/2018
 */

public class Resources {
    String description;
    String fullName;
    String resourceTopic;
    String resourceURL;
    String timestamp;

    // This variable denotes what kind of resource it is - url, pdf, image or video
    private String format;


    // constructors

    public Resources() {
    }

    public Resources(String description, String fullName, String resourceTopic, String resourceURL, String timestamp, String format) {
        this.description = description;
        this.fullName = fullName;
        this.resourceTopic = resourceTopic;
        this.resourceURL = resourceURL;
        this.timestamp = timestamp;
        this.format = format;
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

    public String getFormat() {
        return format;
    }
}
