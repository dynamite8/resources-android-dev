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
    private MutableLiveData<List<Resource>> mResources;


    public ResourceViewModel(@NonNull Application application) {
        super(application);
        mResourceRepository = new ResourceRepository(application);
        //upon creation, ViewModel caches list of all resources not filtered by topic
        mResources = mResourceRepository.getAllResouces();

    }

    /**
     * returns list of resources stored in ViewModel member variable
     * @return MutableLiveData<List<Resource>>
     */
    public MutableLiveData<List<Resource>> getResources() {
        return mResources;
    }

    /**
     * update mResources MutableLiveData<List<Resource>> with new
     * List<Resource> which contains all records in db resource_table
     */
    public void updateUiWithAllResources() {
        mResources.postValue(mResourceRepository.getAllResourcesList());
    }

    /**
     * update mResources MutableLiveData<List<Resource>> with new
     * List<Resource> which contains all records matching topic parameter
     */
    public void updateUiWithTopicResources(String topic) {
        mResources.postValue(mResourceRepository.getTopicResourcesList(topic));
    }
}

