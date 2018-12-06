package com.example.bjtu.fitbit_steamr;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;

public class TaskService extends Service {

    public String userDevices;
    public String userActivities = null;
    String token = null;
    String streamID = null;
    String key = null;
    boolean both = false;
    AlarmManager am = null;

    @Override
    public void onCreate() {
        //super.onCreate();
        System.out.println("Task Service created!");
        if (Build.VERSION.SDK_INT >= 26) {
            startForeground(1, new Notification());

        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("Task Service on start!");

        if(intent!=null){
            System.out.println(intent.getClass());
            Bundle bundle = intent.getExtras();
            key = bundle.getString("key");
            token = bundle.getString("token");
            streamID = bundle.getString("streamID");
        }else{

        }
        getData();
        am = (AlarmManager)getSystemService(ALARM_SERVICE);
        //8分钟请求一次更新
        int elapseTime = 8*60*1000;
        long triggerTime = SystemClock.elapsedRealtime() + elapseTime;
        Intent i = new Intent(this, TaskReceiver.class);
        Bundle bundle = new Bundle();
        bundle.putString("key", key);
        bundle.putString("token", token);
        bundle.putString("streamID", streamID);
        i.putExtras(bundle);
        PendingIntent pi = PendingIntent.getBroadcast(this,0,i,FLAG_UPDATE_CURRENT);
        am.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerTime, pi);
        flags = START_STICKY;
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        System.out.println("Service:onDestroy");
        Intent i = new Intent(this, TaskReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(this,0,i,FLAG_UPDATE_CURRENT);
        am.cancel(pi);
        /*Intent localIntent = new Intent();
        localIntent.setClass(this, TaskService.class); //销毁时重新启动Service
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N_MR1)
            startForegroundService(localIntent);
        else
            startService(localIntent);*/
    }

    protected void stream() {
        System.out.println("method stream is invoked!");

        String header = "token " + key;
        String url = "https://www.streamr.com/api/v1/streams/"+streamID+"/data";

        if (userDevices == null)
            userDevices = "The Data not update!";
        if (userActivities == null)
            userActivities = "The Data not update!";
        Callback callback = new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                System.out.println("Failed to upload data to Streamr!");
            }

            @Override
            public void onResponse(Response response) throws IOException {
                System.out.println("Stream success!");
            }
        };
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("User Devices", userDevices);
            jsonObject.put("User Activities", userActivities);
        }catch (JSONException e) {
            e.printStackTrace();
        }
        OkHttpUtil.httpPost(url, jsonObject.toString(), callback, header);
    }

    protected String getUserDevices() {
        String url_devices = "https://api.fitbit.com/1/user/-/devices.json";
        userDevices = null;
        Callback callback_devices = new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                System.out.println("Get User Devices failed!");
            }

            @Override
            public void onResponse(Response response) throws IOException {
                userDevices = response.body().string();
                System.out.println("data devices:::"+userDevices);
                getUserActivities();
            }
        };
        sendRequest(url_devices, callback_devices);
        return userDevices;
    }

    protected String getUserActivities() {
        userActivities = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        String url_activities = "https://api.fitbit.com/1/user/-/activities/date/"+simpleDateFormat.format(date)+".json";
        Callback callback_activities = new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                System.out.println("Get UserActivities failed!");
            }


            @Override
            public void onResponse(Response response) throws IOException {
                userActivities = response.body().string();
                System.out.println("data activities:::"+userActivities);
                /*if (userDevices != null && userActivities != null)
                    stream();*/
                stream();
            }
        };
        sendRequest(url_activities, callback_activities);
        return userActivities;
    }

    protected void getData() {
        userActivities = null;
        userDevices = null;
        both = false;

        getUserDevices();

    }

    protected void sendRequest(String url, Callback callback) {
        Request.Builder requestBuilder = new Request.Builder().url(url).addHeader("Authorization","Bearer "+token);
        final Request request = requestBuilder.build();
        OkHttpUtil.enqueue(request, callback);
    }

}
