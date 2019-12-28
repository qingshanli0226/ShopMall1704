package com.example.dimensionleague;

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.RequiresApi;

import com.example.dimensionleague.activity.MainActivity;
import com.example.dimensionleague.activity.WelcomeActivity;

import org.jetbrains.annotations.NotNull;

import java.lang.ref.WeakReference;

public class JobService extends android.app.job.JobService {
    Handler mHandler=new MyHandler(this);
    @Override
    public void onCreate() {
        super.onCreate();

        //获取调用系统Service的接口API
        JobScheduler jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);//使用系统service
        //创建执行的一个任务
        JobInfo jobInfo = new JobInfo.Builder(1, new ComponentName(getPackageName(), JobService.class.getName()))
                .setPeriodic(3000)//该任务循环执行，每三秒执行一次
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)//任务网络情况下都执行
                .build();
        jobScheduler.schedule(jobInfo);//启动任务
    }

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Message message = new Message();
        message.what = 1;
        message.obj = jobParameters;//传递参数
        mHandler.sendMessage(message);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }

    private static class MyHandler extends Handler {
        private final WeakReference<JobService> mWeakReference;
        private final Context mContext;

        MyHandler(JobService service) {
            mWeakReference = new WeakReference<>(service);
            mContext = service;
        }

        @Override
        public void handleMessage(@NotNull Message msg) {
            super.handleMessage(msg);
            JobService service = mWeakReference.get();
            JobParameters jobParameters = (JobParameters)msg.obj;
            service.jobFinished(jobParameters, true);

            Intent intent = new Intent(service, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            service.startActivity(intent);
        }
    }
}
