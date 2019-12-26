package com.example.remindsteporgan.Util;

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
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.example.remindsteporgan.RemindActivity;

//android使用JobScheduler保活。应用版本必须在21之上,否则无法使用该方法保活
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class LiveJobService extends JobService {
    @Override
    public void onCreate() {
        super.onCreate();

        //获取调用系统Service的接口API
        JobScheduler jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);//使用系统service
        //创建执行的一个任务
        JobInfo jobInfo = new JobInfo.Builder(1, new ComponentName(getPackageName(), LiveJobService.class.getName()))
                .setPeriodic(3000)//该任务循环执行，每三秒执行一次
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)//任务网络情况下都执行
                .build();
        jobScheduler.schedule(jobInfo);//启动任务
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            JobParameters jobParameters = (JobParameters)msg.obj;
            jobFinished(jobParameters, true);

            Intent intent = new Intent(LiveJobService.this, RemindActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    };

    //当启动任务时回调该方法,当返回值为false时，代表任务执行完毕,否则，代表任务正在执行，必须手动调用jobFinished()方法
    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d("LQS:", "onStartJob.......");

        Message message = new Message();
        message.what = 1;
        message.obj = params;//传递参数
        handler.sendMessage(message);
        return true;
    }
    //当任务结束时回调该方法
    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Log.d("LQS:", "onStopJob.......");
        return false;
    }
}
