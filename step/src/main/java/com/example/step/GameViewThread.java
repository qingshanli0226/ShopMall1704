package com.example.step;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameViewThread extends Thread {

    RunView runView;
    private boolean isRunning=true;
    SurfaceHolder surfaceHolder;

    public void setRunView(boolean bRun){
        isRunning=bRun;
    }
    public GameViewThread(RunView runView){
        this.runView=runView;
        this.surfaceHolder=runView.getHolder();

    }

    @Override
    public void run() {
        super.run();
        Canvas c;
        while (isRunning==true){
            c=null;
            try {
                c=this.surfaceHolder.lockCanvas();
                synchronized (this.surfaceHolder){
                    runView.draw(c);

                }
            }finally {
                if(c!=null){
                    this.surfaceHolder.unlockCanvasAndPost(c);
                }
            }
                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

        }
    }
}
