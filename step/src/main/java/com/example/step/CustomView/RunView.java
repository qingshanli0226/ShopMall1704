package com.example.step.CustomView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.step.R;

public class RunView extends SurfaceView {

    private SurfaceHolder holder;
    private Canvas canvas;
    private Paint paint;
    private int mWidth;
    private int mHight;

    private Bitmap background;
    private Bitmap mPeople;
    private  int Time=50;
    private int picPostion=0;


    private boolean isRunning=true;





    private Runnable runnable=new Runnable() {
        @Override
        public void run() {
            MyDraw();
        }
    };


    public void removeSurfaceView(){
        holder.removeCallback(callback);
    }

    public RunView(Context context, AttributeSet attrs) {
        super(context, attrs);

        holder=this.getHolder();
        holder.addCallback(callback);

        paint=new Paint();
        paint.setColor(Color.BLACK);

        BitmapFactory.Options options = new BitmapFactory.Options();
        background=BitmapFactory.decodeResource(getResources(), R.mipmap.bluewim,options);
        mPeople=changeBitmapSize();

    }


    private Bitmap changeBitmapSize(){
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ren);
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        int newWidth=480;
        int newHeight=180;


        float scaleWidth =(float) newWidth / width;
        float scaleHeigh = (float)newHeight/height;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth,scaleHeigh);

        Bitmap bitmap1 = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        return bitmap1;
    }

    SurfaceHolder.Callback callback=new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder surfaceHolder) {
            new Thread(runnable).start();
            isRunning=true;



        }

        @Override
        public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int width, int hight) {

            mWidth=width;
            mHight=hight;

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

            isRunning=false;


        }
    };


    //..
    private void MyDraw(){
        int toleft=300;
        int toTop=mHight-50;
        int postionLeft=0;
        if(toleft>mWidth){
            toleft=0;
        }

        while (isRunning==true){
            try {
                Rect rect = new Rect(toleft, toTop, toleft + mPeople.getWidth() / 10, toTop - mPeople.getHeight());


                canvas=holder.lockCanvas();
                synchronized (RunView.class){
                    if(canvas!=null){

                        canvas.drawBitmap(background,null,new Rect(0,0,mWidth,mHight),paint);

                        canvas.save();
                        canvas.clipRect(rect);

                        postionLeft= toleft-picPostion* mPeople.getWidth()/10;

                        if(postionLeft<=mWidth){
                            toleft+=40;
                            Thread.sleep(30);

                        }else {
                            toleft=10;
                            canvas.drawRect(rect,paint);
                        }

                        canvas.drawBitmap(mPeople,postionLeft,toTop-mPeople.getHeight(),paint);
                        canvas.restore();


                        picPostion++;
                        if(picPostion>9) {
                            picPostion = 0;
                        }
                }

                }
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                if(canvas!=null){
                    holder.unlockCanvasAndPost(canvas);
                }
            }
            try {
                Thread.sleep(Time);
            }catch (Exception e){

            }

        }
    }





}
