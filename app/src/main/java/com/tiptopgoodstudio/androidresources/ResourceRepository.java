package com.tiptopgoodstudio.androidresources;


import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.tiptopgoodstudio.androidresources.db.AppDatabase;
import com.tiptopgoodstudio.androidresources.db.dao.ResourceDao;
import com.tiptopgoodstudio.androidresources.db.entity.Resource;

import java.util.List;

public class ResourceRepository {

    private ResourceDao mResourceDao;
    private LiveData<List<Resource>> mAllResources;

    public ResourceRepository(Application application) {

        AppDatabase db = AppDatabase.getDatabase(application);
        mResourceDao = db.resourceDao();
        mAllResources = mResourceDao.getResources();
    }


    public LiveData<List<Resource>> getAllResouces() {
        return mAllResources;
    }

    public LiveData<List<Resource>> getTopicResources(String topic) {
        return mResourceDao.getTopicResources(topic);
    }

    public Resource getResourceById(int id) {
        return mResourceDao.getResourceById(id);
    }

    public void insert(Resource resource) {

        new InsertAsyncTask(mResourceDao).execute(resource);
    }

    private static class InsertAsyncTask extends AsyncTask<Resource, Void, Void> {
        private ResourceDao mAsyncTaskDao;

        InsertAsyncTask(ResourceDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Resource... resources) {

            mAsyncTaskDao.insertResource(resources[0]);
            return null;


        }
    }

    public String getResourceFormat(int id) {
        return mResourceDao.getResourceFormat(id);
    }

    public void updateResource(Resource resource) {
        new UpdateAsyncTask(mResourceDao).execute(resource);
    }


    private static class UpdateAsyncTask extends AsyncTask<Resource, Void, Void> {
        private ResourceDao mAsyncTaskDao;

        UpdateAsyncTask(ResourceDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Resource... resource) {

            mAsyncTaskDao.updateResource(resource[0]);
            return null;


        }
    }
}
