package com.oshorn.appfeedback;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;

import com.oshorn.appfeedback.service.FeedbackService;

/**
 * Created by 廖嘉 on 2016/7/8.
 */
public class FeedbackManager {

    private Activity activity;
    private static FeedbackManager instance;
    private boolean init = false;

    private Activity first;
    private Intent intent;
    private Application.ActivityLifecycleCallbacks callbacks;

    public static FeedbackManager getInstance() {
        if(instance == null){
            instance = new FeedbackManager();
        }
        return instance;
    }

    public void onAppStart(Activity activity){
        init = true;
        this.first = activity;
        this.activity = activity;
        callbacks = new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            }

            @Override
            public void onActivityStarted(Activity activity) {
            }

            @Override
            public void onActivityResumed(Activity activity) {
                FeedbackManager.this.activity = activity;
            }

            @Override
            public void onActivityPaused(Activity activity) {
            }

            @Override
            public void onActivityStopped(Activity activity) {
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
            }
        };
        activity.getApplication().registerActivityLifecycleCallbacks(callbacks);
        intent = new Intent(activity, FeedbackService.class);
        activity.startService(intent);
    }
    private FeedbackManager(){
    }

    public Activity getActivity() {
        if(init){
            return activity;
        }
        throw new RuntimeException("please invoke onAppStart() method while the first activity create");
    }

    public void onAppExist(){
        first.getApplication().unregisterActivityLifecycleCallbacks(callbacks);
        first.stopService(intent);
    }
}
