package com.tiptopgoodstudio.androidresources.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.tiptopgoodstudio.androidresources.ResourceRepository;
import com.tiptopgoodstudio.androidresources.db.entity.Resource;

import java.util.List;

public class ResourceViewModel extends AndroidViewModel {

    private ResourceRepository mResourceRepository;
    private LiveData<List<Resource>> mAllResources;
    private LiveData<List<Resource>> mTopicResources;

    public ResourceViewModel(@NonNull Application application) {
        super(application);
        mResourceRepository = ResourceRepository.getRepository(application);
        mAllResources = mResourceRepository.getAllResourcesList();
    }

    /**
     * Return the cached member variable for all resources
     */
    public LiveData<List<Resource>> getAllResources() {
        return mAllResources;
    }

    /**
     * @param topic
     * @return LiveData<List < Resource>> filtered by topic
     */
    public LiveData<List<Resource>> getTopicResources(String topic) {
        if (mTopicResources == null) {
            mTopicResources = mResourceRepository.getTopicResourcesList(topic);
        }
        return mTopicResources;
    }
}
