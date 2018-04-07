package com.tiptopgoodstudio.androidresources.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.tiptopgoodstudio.androidresources.ResourceRepository;
import com.tiptopgoodstudio.androidresources.db.entity.Resource;

import java.util.List;


public class ResourceViewModel extends AndroidViewModel {

    private ResourceRepository mResourceRepository;
    private List<Resource> mResources;


    public ResourceViewModel(@NonNull Application application) {
        super(application);
        mResourceRepository = new ResourceRepository(application);
        //upon creation, ViewModel caches list of all resources not filtered by topic
        mResources = mResourceRepository.getAllResourcesList();
    }

    /**
     * returns list of resources stored in ViewModel member variable
     * @return List<Resource>
     */
    public List<Resource> getResources() {
        return mResources;
    }

    /**
     * retrieve, save to member variable and return list of all resources in db
     */
    public List<Resource> getAllResources() {
        mResources = mResourceRepository.getAllResourcesList();
        return mResources;
    }

    /**
     * retrieve, save to member variable and return list of all topic-specific resources in db
     */
    public List<Resource> getTopicResources(String topic) {
        mResources = mResourceRepository.getTopicResourcesList(topic);
        return mResources;
    }
}

