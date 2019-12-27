package com.example.net.sign;

import android.os.Build;

import android.support.annotation.RequiresApi;
import android.util.Base64;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import static java.nio.charset.StandardCharsets.*;

public class SignUtil {
    public static TreeMap<String, String> getEmptyTreeMap() {
        TreeMap<String, String> treeMap = new TreeMap<>(new Comparator<String>() {
            @Override
            public int compare(String ls, String rs) {
                return ls.compareTo(rs);
            }
        });

        return treeMap;
    }

    /**
     * 通过Base 64加密参数
     *
     * @param params
     * @return
     */
    public static TreeMap<String, String> encryptParamsByBase64(Map<String, String> params) {
        TreeMap<String, String> encryptedParams;
        encryptedParams = new TreeMap<>(new Comparator<String>() {
            @Override
            public int compare(String ls, String rs) {
                return ls.compareTo(rs);
            }
        });
        for (String key : params.keySet()) {
            String value = params.get(key);
            byte[] encryptValue = Base64.encode(value.getBytes(), Base64.DEFAULT);
            String encryptStr = new String(encryptValue);
            encryptedParams.put(key, encryptStr);
        }
        Log.d("LQS encryptedParams: ", encryptedParams.toString());

        for (String key : encryptedParams.keySet()) {
            String value = new String(Base64.decode(encryptedParams.get(key), Base64.DEFAULT));
            Log.d("LQS: ", value);
        }

        return encryptedParams;
    }


    /**
     * 对json数据的验签
     *
     * @param object
     * @return
     */
    public static String generateJsonSign(JSONObject object) {
        TreeMap<String, String> params = getEmptyTreeMap();
        Iterator<String> keys = object.keySet().iterator();

        while (keys.hasNext()) {
            String key = keys.next();
            params.put(key, object.get(key).toString());
        }

        StringBuilder str = new StringBuilder();
        for (String key : params.keySet()) {
            String value = params.get(key);
            str.append(key + "=" + value + "&");
        }

        str.append("encrypt=md5");
        String strNew = str.toString();/*.replace("\"", "")
                .replace(":", "").replace("=", "")
                .replace(" ", "");*/
        String signValue = stringToMD5(strNew);
        return signValue;
    }

    public static void encryptJsonParamsByBase64(JSONObject object) {
        Log.d("LQS str = ", object.toString());

        Iterator<String> iterator = object.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();

            String value = object.get(key).toString();
            String encryptValue = new String(Base64.encode(value.getBytes(), Base64.DEFAULT));
            object.put(key, encryptValue);
        }
    }


    /**
     * 生成签名
     *
     * @param params
     * @return
     */
    public static String generateSign(Map<String, String> params) {

        StringBuilder str = new StringBuilder();
        for (String key : params.keySet()) {
            String value = params.get(key);
            str.append(key + "=" + value + "&");
        }

        str.append("encrypt=md5");
        Log.d("LQS", str.toString().trim());
        String signValue = stringToMD5(str.toString().trim());
        Log.d("LQS signvalue = ", signValue);
        return signValue;
    }

    public static String stringToMD5(String string) {
        byte[] hash = new byte[0];
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                hash = MessageDigest.getInstance("MD5").digest(string.getBytes(StandardCharsets.UTF_8));
            }

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();

            return null;
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) {
                hex.append("0");
            }
            hex.append(Integer.toHexString(b & 0xFF));
        }

        return hex.toString();
    }
}
