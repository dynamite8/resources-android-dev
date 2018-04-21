package com.tiptopgoodstudio.androidresources.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.tiptopgoodstudio.androidresources.ResourceRepository;
import com.tiptopgoodstudio.androidresources.db.entity.Topic;

import java.util.List;

/**
 * Created by Divya on 4/20/2018.
 * For GrowWithGoogleChallengeScholarship Women In Technology Learning Project
 * Android Dev Resources
 *
 * This ViewModel class is associated with MainActivity that displays
 * all topics
 *
 */

public class TopicViewModel extends AndroidViewModel {

    private ResourceRepository mResourceRepository;
    private MutableLiveData<List<Topic>> mTopics = new MutableLiveData<List<Topic>>();

    public TopicViewModel(@NonNull Application application) {
        super(application);
        mResourceRepository = new ResourceRepository(application);
        // upon creation, ViewModel caches list of all topics
        loadAllTopics();
    }

    /**
     * returns list of topics stored in ViewModel member variable
     * @return List<Topic>
     */
    public LiveData<List<Topic>> getTopicsList() {
        return mTopics;
    }

    /**
     * Make an asynchronous call to load a list of topics from the DB and save to member variable
     */
    private void loadAllTopics() {
        new GetTopicsAsyncTask(mResourceRepository).execute();
    }

    private class GetTopicsAsyncTask extends AsyncTask<Void, Void, List<Topic>> {
        private ResourceRepository resRepo;

        GetTopicsAsyncTask(ResourceRepository resourceRepository) {
            resRepo = resourceRepository;
        }

        @Override
        protected List<Topic> doInBackground(Void  ... voids) {
            return resRepo.getTopicsList();
        }

        @Override
        protected void onPostExecute(List<Topic> topicList) {
            super.onPostExecute(topicList);
            mTopics.setValue(topicList);
        }
    }
}

