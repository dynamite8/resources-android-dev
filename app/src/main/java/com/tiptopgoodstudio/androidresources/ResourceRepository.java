package com.tiptopgoodstudio.androidresources;


import android.app.Application;
import android.arch.lifecycle.LiveData;

import com.tiptopgoodstudio.androidresources.db.AppDatabase;
import com.tiptopgoodstudio.androidresources.db.dao.ResourceDao;
import com.tiptopgoodstudio.androidresources.db.entity.Resource;

import java.util.List;

public class ResourceRepository {

    private ResourceDao mResourceDao;
    private LiveData<List<Resource>> mAllResources;

    ResourceRepository(Application application) {

    }




}
