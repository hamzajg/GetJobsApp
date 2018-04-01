package com.thephoenixit.getjobsapp.utils;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class JsonUtils
{

    public JsonUtils()
    {
    }

    public static String getJSONString(String s)
    {
        HttpURLConnection httpurlconnection;
        String s1 = null;
        int i;
        try {
            httpurlconnection = (HttpURLConnection)(new URL(s)).openConnection();
            i = httpurlconnection.getResponseCode();
            InputStream inputstream;
            ByteArrayOutputStream bytearrayoutputstream;
            if(i != 200) {
                httpurlconnection.disconnect();
                return s1;
            } else {
                inputstream = httpurlconnection.getInputStream();
                bytearrayoutputstream = new ByteArrayOutputStream();
                int j = inputstream.read();
                if(j == -1) {
                    String s2 = new String(bytearrayoutputstream.toByteArray());
                    s1 = s2;
                } else {
                    bytearrayoutputstream.write(j);
                    s1 = null;
                    httpurlconnection.disconnect();
                }
                httpurlconnection.disconnect();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s1;
    }

    public static boolean isNetworkAvailable(Activity activity)
    {
        ConnectivityManager connectivitymanager = (ConnectivityManager)activity.getSystemService("connectivity");

        NetworkInfo anetworkinfo[];
        if(connectivitymanager != null)
        {
            if((anetworkinfo = connectivitymanager.getAllNetworkInfo()) != null)
            {
                int i = 0;
                while(i < anetworkinfo.length)
                {
                    if(anetworkinfo[i].getState() == android.net.NetworkInfo.State.CONNECTED)
                        return true;
                    i++;
                }
            }
        }
        return false;
    }
}
