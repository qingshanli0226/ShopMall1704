package com.shaomall.framework.manager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import com.example.commen.util.ErrorUtil;
import com.example.commen.util.ShopMailError;
import com.example.net.AppNetConfig;
import com.example.net.MVPObserver;
import com.example.net.ResEntity;
import com.example.net.RetrofitCreator;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shaomall.framework.bean.ShoppingCartBean;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.http.Url;

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


    public void downloadFileData() {
        //进行网络请求
        RetrofitCreator.getNetApiService().downloadFile(AppNetConfig.DOWN_LOAD_FILE_URL)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MVPObserver<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        super.onSubscribe(d);
                    }

                    @SuppressLint("SdCardPath")
                    @Override
                    public void onNext(ResponseBody responseBody) {
                        //获取sd卡的路径
                        File file = Environment.getExternalStorageDirectory();
                        InputStream inputStream;//网络连接的输入流
                        HttpURLConnection connection;//向SD卡写的输出流
                        FileOutputStream out;

                        try {
                            URL url=new URL(responseBody.string());
                            out=new FileOutputStream(new File(file,"shaohua.txt"));
                            inputStream = responseBody.byteStream();
                            connection= (HttpURLConnection) url.openConnection();
                            connection.setConnectTimeout(5*1000);
                            connection.setReadTimeout(5*1000);
                            if (connection.getResponseCode()==200){
                                inputStream = connection.getInputStream();
                                //TODO 获取SD卡的路径
                                if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {//是否挂载
                                    File file1 = Environment.getExternalStorageDirectory();
                                    out = new FileOutputStream(new File(file1,"/sdcard/shaohua.txt"));
                                    Toast.makeText(context, ""+out, Toast.LENGTH_SHORT).show();
                                    byte[] bytes=new byte[1024];
                                    int len=0;
                                    while((len=inputStream.read(bytes))!=-1){
                                        out.write(bytes,0,len);
                                    }
                                }
                            }
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(Throwable e) {

                        }
                });
    }

}
