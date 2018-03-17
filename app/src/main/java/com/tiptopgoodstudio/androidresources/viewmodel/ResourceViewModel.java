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


    public ResourceViewModel(@NonNull Application application) {
        super(application);
        mResourceRepository = new ResourceRepository(application);
        mAllResources = mResourceRepository.getAllResouces();

    }

    public LiveData<List<Resource>> getAllResources() {
        return mAllResources;
    }

    public void insert(Resource resource) {
        mResourceRepository.insert(resource);
    }

    public void update(Resource resource) {
        mResourceRepository.updateResource(resource);
    }

    public LiveData<List<Resource>> getTopicResources(String topic) {
        return mResourceRepository.getTopicResources(topic);
    }

    public Resource getResourceById(int id) {
        return mResourceRepository.getResourceById(id);
    }

    public String getResourceFormat(int id) {
        return mResourceRepository.getResourceFormat(id);

    }
}
