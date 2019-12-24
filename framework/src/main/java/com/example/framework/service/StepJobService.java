package com.example.framework.service;

import android.annotation.SuppressLint;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class StepJobService extends JobService {

    @SuppressLint("HandlerLeak")
    Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            JobParameters jobParameters = (JobParameters) msg.obj;
            jobFinished(jobParameters,true);


//            new Intent(StepJobService.this,)


        }
    };
    @Override
    public void onCreate() {
        super.onCreate();
        JobScheduler jobScheduler =(JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        JobInfo.Builder jobInfo = new JobInfo.Builder(1, new ComponentName(getPackageName(), StepJobService.class.getName()));
        JobInfo jobBuild = jobInfo.setPeriodic(3000)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .build();
        jobScheduler.schedule(jobBuild);
    }

    @Override
    public boolean onStartJob(JobParameters jobParameters) {

        Message message = new Message();
        message.what=1;
        message.obj=jobParameters;
        handler.sendMessage(message);

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Log.e("start","stop");
        return false;
    }
}
