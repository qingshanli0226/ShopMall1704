package com.example.common;

import android.util.Base64;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

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

    public static TreeMap<String, String> encryptParamsByBase64(Map<String, String> params) {
        TreeMap<String, String> encryptedParams;
        encryptedParams = new TreeMap<>(new Comparator<String>() {
            @Override
            public int compare(String ls, String rs) {
                return ls.compareTo(rs);
            }
        });
        for(String key:params.keySet()) {
            String value = params.get(key);
            byte[] encryptValue = Base64.encode(value.getBytes(), Base64.DEFAULT);
            String encryptStr = new String(encryptValue);
            encryptedParams.put(key, encryptStr);
        }
        Log.d("LHF encryptedParams: ", encryptedParams.toString());

        for(String key:encryptedParams.keySet()) {
            String value = new String(Base64.decode(encryptedParams.get(key), Base64.DEFAULT));
            Log.d("LHF: ", value);
        }

        return encryptedParams;
    }

    //对json数据的验签
    public static String generateJsonSign(JSONObject object) {
        TreeMap<String, String> params = getEmptyTreeMap();
        Iterator<String> keys = object.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            try {
                params.put(key, object.get(key).toString());
                Log.d("LHF = ", object.get(key).toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        StringBuilder str = new StringBuilder();
        for(String key:params.keySet()) {
            String value = params.get(key);
            str.append(key + "=" + value + "&");
        }
        str.append("encrypt=md5");
        String strNew = str.toString().replace("\"", "")
                .replace(":", "").replace("=", "")
                .replace(" ", "");
        String signValue = stringToMD5(strNew);
        Log.d("LHF str new = ", strNew);
        Log.d("LHF signvalue = ", signValue);

        return signValue;
    }

    public static String generateSign(Map<String, String> params) {
        TreeMap<String, String> emptyTreeMap = getEmptyTreeMap();
        Set<Map.Entry<String, String>> entries = params.entrySet();
        for (Map.Entry<String, String> map:entries) {
            emptyTreeMap.put(map.getKey(),map.getValue());
        }

        StringBuilder str = new StringBuilder();
        for(String key:emptyTreeMap.keySet()) {
            String value = emptyTreeMap.get(key);
            str.append(key + "=" + value + "&");
        }
        str.append("encrypt=md5");
        Log.d("LHF", str.toString().trim());
        String signValue = stringToMD5(str.toString().trim());
        Log.d("LHF signvalue = ", signValue);

        return signValue;
    }

    public static String stringToMD5(String string) {
        byte[] hash;

        try {
            hash = MessageDigest.getInstance("MD5").digest(
                    string.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10)
                hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }

        return hex.toString();
    }

}
