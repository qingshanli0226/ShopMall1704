package com.example.dimensionleague.util;
import android.text.TextUtils;

import com.example.framework.manager.ErrorDisposeManager;

import java.security.MessageDigest;

public class Md5Util {

    public static String md5(String content) {
        if (TextUtils.isEmpty(content)) {
            return null;
        }
        try {
            byte[] b = content.getBytes("UTF-8");
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.reset();
            md.update(b);
            byte[] hash = md.digest();
            StringBuffer outStrBuf = new StringBuffer(32);
            for (int i = 0; i < hash.length; i++) {
                int v = hash[i] & 0xFF;
                if (v < 16) {
                    outStrBuf.append('0');
                }
                outStrBuf.append(Integer.toString(v, 16).toLowerCase());
            }
            return outStrBuf.toString();
        } catch (Exception e) {
            ErrorDisposeManager.HandlerError(e);
        }
        return null;
    }
}
