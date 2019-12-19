package com.example.common.utils;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class GetAssetsJson {
    public static String getJsonString(Context context,String assetsName){
        InputStream inputStream =null;
        BufferedReader bufferedReader=null;
        StringBuffer stringBuffer=null;
        String str=null;
        try {
            inputStream = context.getAssets().open(assetsName);
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            stringBuffer=new StringBuffer();

            while ((str=bufferedReader.readLine())!=null){
                stringBuffer.append(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                inputStream.close();
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return stringBuffer.toString();
    }
}
