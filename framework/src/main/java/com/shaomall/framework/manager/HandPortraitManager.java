package com.shaomall.framework.manager;

import android.content.Context;

import java.io.File;

public class HandPortraitManager {
    private Context context;
    private static HandPortraitManager instance;

    private HandPortraitManager() {
    }

    public static HandPortraitManager getInstance() {
        if (instance==null){
            synchronized (HandPortraitManager.class){
                if (instance==null){
                    instance=new HandPortraitManager();
                }
            }
        }
        return instance;
    }
    public void init(Context context){
        this.context=context;

    }





}
