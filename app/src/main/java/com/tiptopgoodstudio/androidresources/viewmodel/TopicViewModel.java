package com.tiptopgoodstudio.androidresources.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.tiptopgoodstudio.androidresources.ResourceRepository;
import com.tiptopgoodstudio.androidresources.db.entity.Topic;

import java.util.List;

/**
 * Created by Divya on 4/20/2018.
 * For GrowWithGoogleChallengeScholarship Women In Technology Learning Project
 * Android Dev Resources
 * <p>
 * This ViewModel class is associated with MainActivity that displays
 * all topics
 */

public class TopicViewModel extends AndroidViewModel {

    private ResourceRepository mResourceRepository;
    private LiveData<List<Topic>> mTopics;

    public TopicViewModel(@NonNull Application application) {
        super(application);
        mResourceRepository = ResourceRepository.getRepository(application);
        mTopics = mResourceRepository.getTopicsList();
    }

    /**
     * returns list of topics stored in ViewModel member variable
     *
     * @return List<Topic>
     */
    public LiveData<List<Topic>> getTopicsList() {
        return mTopics;
    }

}

