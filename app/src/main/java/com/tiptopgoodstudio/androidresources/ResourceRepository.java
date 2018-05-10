package com.tiptopgoodstudio.androidresources;

import android.app.Application;
import android.os.AsyncTask;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tiptopgoodstudio.androidresources.db.AppDatabase;
import com.tiptopgoodstudio.androidresources.db.dao.ResourceDao;
import com.tiptopgoodstudio.androidresources.db.dao.TopicDao;
import com.tiptopgoodstudio.androidresources.db.entity.Resource;
import com.tiptopgoodstudio.androidresources.db.entity.Topic;

import java.util.List;

/**
 * The following added by Divya on 4/20/2018
 * Added a method to insert a single topic in the topic_table
 * Added a method to insert a single resource in the resource_table
 * Moved the firebase code from MainActivity to the Repository
 *
 */

public class ResourceRepository {

    private ResourceDao mResourceDao;
    private TopicDao mTopicDao;

    // Firebase references
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mResourcesDatabaseReference;
    private ChildEventListener mResourcesEventListener;

    /**
     *
     * @param application
     */
    public ResourceRepository(Application application) {

        // Get a handle to the local SQLite Database
        AppDatabase db = AppDatabase.getDatabase(application);
        mResourceDao = db.resourceDao();
        mTopicDao = db.topicDao();
    }

    /**
     * method returns all topic records in Room db topic_table
     * @return List<Topic>
     */
    public List<Topic> getTopicsList() {
        return mTopicDao.getAllTopicsList();
    }

    /**
     * method returns all resource records in Room db resource_table
     * @return List<Resource>
     */
    public List<Resource> getAllResourcesList() {
        return mResourceDao.getAllResourcesList();
    }

    /**
     * method returns all resource records which match topic parameter from
     * Room db resource_table \
     * @param topic
     * @return List<Resource>
     */
    public List<Resource> getTopicResourcesList(String topic) {
        return mResourceDao.getTopicResourcesList(topic);
    }

    /**
     * delete all records in Room db resource_table
     */
    private void deleteAllRecords() {
        mResourceDao.deleteAll();
    }

    /**
     * Method to insert multiple resource objects into Room db resource_table
     * Calls InssertResourceAsyncTask so as to perform work off main thread
     *
     * @param resources
     */
    private void insertResources(Resource... resources) {
        new InsertResourceAsyncTask(mResourceDao).execute(resources);
    }

    /**
     * Inner AsyncTask subclass to insert Resource objects into Room db resource_table
     */
    private static class InsertResourceAsyncTask extends AsyncTask<Resource, Void, Void> {
        private ResourceDao taskDao;

        InsertResourceAsyncTask(ResourceDao dao) {
            this.taskDao = dao;
        }

        @Override
        protected Void doInBackground(Resource... resources) {
            taskDao.insertResources(resources);
            return null;
        }
    }

    /**
     * Method to insert a single resource object into Room db resource_table
     * Calls InsertSingleResourceAsyncTask so as to perform work off main thread
     *
     * @param resource
     *
     * Added by Divya on 4/20/2018
     */
    public void insertSingleResource(Resource resource) {
        new InsertSingleResourceAsyncTask(mResourceDao).execute(resource);
    }

    /**
     * Inner AsyncTask subclass to insert a single Resource object into Room db resource_table
     *
     * Added by Divya on 4/20/2018
     */
    private static class InsertSingleResourceAsyncTask extends AsyncTask<Resource, Void, Void> {
        private ResourceDao taskDao;

        InsertSingleResourceAsyncTask(ResourceDao dao) {
            this.taskDao = dao;
        }

        @Override
        protected Void doInBackground(Resource... resources) {
            taskDao.insertResource(resources[0]);
            return null;
        }
    }

    /**
     * Method to insert a single Topic object into Room db topic_table
     * Calls InsertSingleTopicAsyncTask so as to perform work off main thread
     *
     * @param topic
     *
     * Added by Divya on 4/20/2018
     */
    public void insertSingleTopic(Topic topic) {
        new InsertSingleTopicAsyncTask(mTopicDao).execute(topic);
    }

    /**
     * Inner AsyncTask subclass to insert a single Topic object into Room db topic_table
     *
     * Added by Divya on 4/20/2018
     */
    private static class InsertSingleTopicAsyncTask extends AsyncTask<Topic, Void, Void> {
        private TopicDao taskDao;

        InsertSingleTopicAsyncTask(TopicDao dao) {
            this.taskDao = dao;
        }

        @Override
        protected Void doInBackground(Topic... topics) {
            taskDao.insertTopic(topics[0]);
            return null;
        }
    }

    /**
     * This method returns the connection to Firebase
     *
     * Updated by Divya on 4/29/2018
     *
     */
    public DatabaseReference connectToFirebase() {
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mResourcesDatabaseReference = mFirebaseDatabase.getReference().child("resources");

        mResourcesDatabaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                addToLocalDatabase(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return mResourcesDatabaseReference;
    }

    /**
     * This method adds the Firebase data into the Local SQLite Database
     *
     * Added by Divya on 4/29/2018
     */
    public void addToLocalDatabase(DataSnapshot dataSnapshot) {
        // get the value at point in time to our matching entity
        // data is deserialized into Resource class
        Resource resource = dataSnapshot.getValue(Resource.class);

        // Create a new Topic object and insert it into the Topic table
        // When a topic that already exists is inserted again, it is ignored
        Topic topic = new Topic(resource.getResourceTopic());
        insertSingleTopic(topic);

        //Insert it into the Resource table
        insertSingleResource(resource);
    }
}