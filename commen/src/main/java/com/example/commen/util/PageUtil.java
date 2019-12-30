package com.example.commen.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.example.commen.R;


public class PageUtil {
    private boolean isLoading = false;
    //TODO 找到加载页面的布局
    private View inflate;
    //TODO 设置布局为Match_Parent
    private RelativeLayout.LayoutParams params;
    //TODO 传回fragment里的RelativeLayout
    private RelativeLayout review;
    private Context mContext;

    @SuppressLint("StaticFieldLeak")
    private static PageUtil instance;

    private PageUtil() {
    }

    private PageUtil(Context context) {
        this.mContext = context.getApplicationContext();
        init();
    }

    public static PageUtil getInstance(Context context) {
        if (instance == null) {
            synchronized (PageUtil.class) {
                if (instance == null) {
                    instance = new PageUtil(context);
                }
            }
        }
        return instance;
    }

    @SuppressLint("InflateParams")
    private void init() {
        //加载页面
        inflate = LayoutInflater.from(mContext).inflate(R.layout.loadphoto, null);
        ImageView imageView = inflate.findViewById(R.id.loadPhotoImageView);
        Glide.with(mContext).load(R.mipmap.tu1).into(imageView);

        params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
    }


    //TODO get方法set方法
    private RelativeLayout getReview() {
        return review;
    }

    public PageUtil setReview(RelativeLayout review) {
        this.review = review;
        return this;
    }

    public void showLoad() {
        if (!isLoading) {
            getReview().addView(inflate, params);
            isLoading = !isLoading;
        }
    }

    public void hideLoad() {
        if (isLoading) {
            getReview().removeView(inflate);
            isLoading = !isLoading;
        }
    }


//    public void ceshi() {
//        new Thread() {
//            @Override
//            public void run() {
//                super.run();
//                for (File folder : folders) {
//                    File[] files = folder.listFiles();
//
//                    for (File file : files) {
//                        if (file.getName().endsWith(".png")) {
//                            final Bitmap bitmap = getBitmapFromFile(file);
//                            getActivity().runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    imageCollectorView.addImage(bitmap);
//                                }
//                            });
//                        }
//                    }
//                }
//            }
//        }.start();
//
//
//        Observable.from(folders).flatMap(new Func1<File, Observable<File>>() {
//            @Override
//            public Observable<File> call(File file) {
//                return Observable.from(file.listFiles());
//            }
//        }).filter(new Func1<File, Boolean>() {
//            @Override
//            public Boolean call(File file) {
//                return file.getName().endsWith(".png");
//            }
//        }).map(new Func1<File, Bitmap>() {
//            @Override
//            public Bitmap call(File file) {
//                return getBitmapFromFile(file);
//            }
//        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Bitmap>() {
//            @Override
//            public void call(Bitmap bitmap) {
//                imageCollectorView.addImage(bitmap);
//            }
//        });
//
//
//    }


}
