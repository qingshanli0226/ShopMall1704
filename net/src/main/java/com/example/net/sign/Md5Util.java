package com.example.net.sign;

import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Util {
    public static String md5(String content){
        if (TextUtils.isEmpty(content)){
            return null;
        }
        try{
            byte[] b = content.getBytes(StandardCharsets.UTF_8);
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.reset();
            md.update(b);
            byte[] hash = md.digest();
            StringBuffer outStrBuf = new StringBuffer(32);
            for (int i = 0; i < hash.length; i++) {
                int v = hash[i] & 0xFF;
                if (v < 16){
                    outStrBuf.append('0');
                }

                outStrBuf.append(Integer.toString(v, 16).toLowerCase());
            }

            return outStrBuf.toString();


        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }


        return null;
    }
}
