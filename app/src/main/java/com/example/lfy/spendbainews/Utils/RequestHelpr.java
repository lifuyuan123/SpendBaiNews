package com.example.lfy.spendbainews.Utils;

import android.content.Context;
import android.util.Log;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * 请求数据封装
 */

public class RequestHelpr {
    public static final JSONObject job = new JSONObject();
    public static final String APPID = "10202820";
    public static final String APPIDKEY = "appid";
    public static final String KEYKEY = "key";
    public static final String SIGN = "sign";
    public static RequestHelpr instance;

    public String getKey() {
        return "SioXHEFuMVUPuJKLuHqZTlzWWFlfRQhe";
    }

    private RequestHelpr() {
    }

//    static {
//        System.loadLibrary("native-lib");
//    }


    public static synchronized RequestHelpr getInstance() {
        if (instance == null) {
            instance = new RequestHelpr();
            try {
                job.put(KEYKEY, instance.getKey());
                Log.e("ggband", "KEY:" + instance.getKey());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }


    public static Map<String, String> getRequestParms(Map<String, String> parms) {
        return null;
    }


    public Map<String, String> getRequestParm(Map<String, String> parms) {
        byte[] result = null;
        try {
            if (parms != null)
                for (String key : parms.keySet())
                    job.put(key, parms.get(key));
            result = Base64.encodeBase64(job.toString().getBytes());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        parms.clear();
        parms.put(APPIDKEY, APPID);
        parms.put(SIGN, new String(result));
        Log.e("parms",parms.toString());
        return parms;

    }


    public Map<String, Object> getRequestParmo(Map<String, Object> parms) {
        byte[] result = null;
        try {
            if (parms != null)
                for (String key : parms.keySet())
                    job.put(key, parms.get(key));
            result = Base64.encodeBase64(job.toString().getBytes());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        parms.clear();
        parms.put(APPIDKEY, APPID);
        parms.put(SIGN, new String(result));
        return parms;

    }

//    public native String keyFromJNI(Context context);


}
