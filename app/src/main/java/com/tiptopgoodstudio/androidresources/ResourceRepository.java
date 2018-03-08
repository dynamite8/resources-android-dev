package com.tiptopgoodstudio.androidresources;


import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;

import com.tiptopgoodstudio.androidresources.db.AppDatabase;
import com.tiptopgoodstudio.androidresources.db.dao.ResourceDao;
import com.tiptopgoodstudio.androidresources.db.entity.Resource;

import java.util.List;

public class ResourceRepository {

    private ResourceDao mResourceDao;
    private LiveData<List<Resource>> mAllResources;

    ResourceRepository(Application application) {
        AppDatabase db=AppDatabase.getDatabase(application);
        mResourceDao=db.resourceDao();
        mAllResources=mResourceDao.getResources();
    }

LiveData<List<Resource>> getResouces()
{
    return mAllResources;
}
public void insertResource(Resource resource){

}
}
