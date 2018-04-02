package com.tiptopgoodstudio.androidresources;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;

import com.tiptopgoodstudio.androidresources.db.AppDatabase;
import com.tiptopgoodstudio.androidresources.db.dao.ResourceDao;
import com.tiptopgoodstudio.androidresources.db.entity.Resource;

import java.util.List;

public class ResourceRepository {

    private ResourceDao mResourceDao;
    private MutableLiveData<List<Resource>> mResources;

    /**
     * Constructor
     *
     * @param application
     */
    public ResourceRepository(Application application) {

        AppDatabase db = AppDatabase.getDatabase(application);
        mResourceDao = db.resourceDao();
        mResources = mResourceDao.getResources();
    }

    /**
     * method returns all resource records in Room db resource_table in LiveData wrapper
     *
     * @return LiveData<List   <   Resource>>
     */
    public MutableLiveData<List<Resource>> getAllResouces() {
        return mResources;
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
     * Room db resource_table MutableLiveData wrapper
     *
     * @param topic
     * @return MutableLiveData<List   <   Resource>>
     */
    public MutableLiveData<List<Resource>> getTopicResources(String topic) {
        return mResourceDao.getTopicResources(topic);
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



}