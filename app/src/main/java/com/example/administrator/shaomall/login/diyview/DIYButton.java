package com.example.administrator.shaomall.login.diyview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.widget.Button;

import com.example.administrator.shaomall.R;

public class DIYButton  extends android.support.v7.widget.AppCompatButton {
    Paint paint;
    String buttomtext="";

    public void setButtomtext(String buttomtext) {
        this.buttomtext = buttomtext;
    }

    LinearGradient linearGradient;
    int i=0;
    boolean type=true;

    public void setType(boolean type) {
        this.type = type;
    }

    public DIYButton(Context context) {
        super(context);

    }

    public DIYButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DIYButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();

        int colorStart = getResources().getColor(R.color.mediumspringgreen);

        int color = Color.parseColor("#00ced1");
        int colorEnd = getResources().getColor(R.color.skyblue);
        paint=new Paint();
        if (i==0){

            linearGradient= new LinearGradient(0, 0, width, height, new int[]{colorStart, color, colorEnd}, null, Shader.TileMode.CLAMP);
            i++;
        }
        if (type){
            linearGradient= new LinearGradient(0, 0, width, height, new int[]{colorStart, color, colorEnd}, null, Shader.TileMode.CLAMP);

        }else {
            linearGradient= new LinearGradient(0, 0, width, height, new int[]{colorEnd, color, colorStart}, null, Shader.TileMode.CLAMP);

        }
        paint.setShader(linearGradient);
        canvas.drawRect(0, 0, width, height, paint);
        //文字
        Rect bounds=new Rect();
        float offSet=(bounds.top+bounds.bottom)/2;
        Paint painttext=new Paint();
        painttext.setTextSize(50);
        painttext.setStrokeWidth(20);
        painttext.setColor(Color.parseColor("#FFFFFF"));
        painttext.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(buttomtext,getWidth()/2,getHeight()/2+15,painttext);


    }
}
