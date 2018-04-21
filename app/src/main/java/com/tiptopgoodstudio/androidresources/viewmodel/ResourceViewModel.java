package com.tiptopgoodstudio.androidresources.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.tiptopgoodstudio.androidresources.ResourceRepository;
import com.tiptopgoodstudio.androidresources.db.entity.Resource;

import java.util.List;

/**
 * The following added by Divya on 4/20/2018
 * Added an AsyncTask method to call the Repository method. This is to ensure
 * that the database call is not being made on the main thread
 *
 */

public class ResourceViewModel extends AndroidViewModel {

    private ResourceRepository mResourceRepository;
    private MutableLiveData<List<Resource>> mResources = new MutableLiveData<List<Resource>>();
    private MutableLiveData<List<Resource>> mTopicResources = new MutableLiveData<List<Resource>>();

    public ResourceViewModel(@NonNull Application application) {
        super(application);
        mResourceRepository = new ResourceRepository(application);
        //upon creation, ViewModel caches list of all resources
        loadAllResources();
    }

    /**
     * Return the cached member variable for all resources
     */
    public LiveData<List<Resource>> getAllResources() {
        return mResources;
    }

    /**
     * When this method is observed from the View, the AsyncTask queries the
     * resource_table based on the given topic. LiveData ensures that after the
     * background task is completed, the data is propagated to the UI
     *
     */
    public LiveData<List<Resource>> getTopicResources(String topic) {
        new GetTopicResourcesAsyncTask(mResourceRepository).execute(topic);
        return mTopicResources;
    }

    private class GetTopicResourcesAsyncTask extends AsyncTask<String, Void, List<Resource>> {
        private ResourceRepository resRepo;

        GetTopicResourcesAsyncTask(ResourceRepository resourceRepository) {
            resRepo = resourceRepository;
        }

        @Override
        protected List<Resource> doInBackground(String  ... strings) {
            return resRepo.getTopicResourcesList(strings[0]);
        }

        @Override
        protected void onPostExecute(List<Resource> resources) {
            super.onPostExecute(resources);
            mTopicResources.setValue(resources);
        }
    }

    /**
     * Make an asynchronous call to load a list of all resources from the DB and save to member variable
     */
    private void loadAllResources() {
        new GetResourcesAsyncTask(mResourceRepository).execute();
    }

    private class GetResourcesAsyncTask extends AsyncTask<Void, Void, List<Resource>> {
        private ResourceRepository resRepo;

        GetResourcesAsyncTask(ResourceRepository resourceRepository) {
            resRepo = resourceRepository;
        }

        @Override
        protected List<Resource> doInBackground(Void  ... voids) {
            return resRepo.getAllResourcesList();
        }

        @Override
        protected void onPostExecute(List<Resource> resources) {
            super.onPostExecute(resources);
            mResources.setValue(resources);
        }
    }
}
