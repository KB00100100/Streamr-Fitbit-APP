package com.fs.fitbitstreamr.service;

import android.Manifest;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.fitbit.authentication.AuthenticationManager;
import com.fs.fitbitstreamr.bean.UserInfoBean;
import com.fs.fitbitstreamr.domain.UploadBean;
import com.fs.fitbitstreamr.utils.OkHttpUtil;
import com.fs.fitbitstreamr.utils.SharedPreferencesUtil;
import com.fs.fitbitstreamr.utils.WalletDaoUtils;
import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.xdandroid.hellodaemon.AbsWorkService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class DeService extends AbsWorkService {
    //是否 任务完成, 不再需要服务运行?
    public static boolean sShouldStopService;
    public static Disposable sDisposable;
    private final String[] PERMISSIONS = new String[]{Manifest.permission.READ_PHONE_STATE
            , Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.CAMERA};
    public String userDevices;
    private String userActivities;

    public static void stopService() {
        //我们现在不再需要服务运行了, 将标志位置为 true
        sShouldStopService = true;
        //取消对任务的订阅
        if (sDisposable != null) sDisposable.dispose();
        //取消 Job / Alarm / Subscription
        cancelJobAlarmSub();
    }

    public static boolean isWorking() {
        return sDisposable != null && !sDisposable.isDisposed();
    }

    /**
     * 是否 任务完成, 不再需要服务运行?
     *
     * @return 应当停止服务, true; 应当启动服务, false; 无法判断, 什么也不做, null.
     */
    @Override
    public Boolean shouldStopService(Intent intent, int flags, int startId) {
        return sShouldStopService;
    }

    @Override
    public void startWork(Intent intent, int flags, int startId) {
        if (WalletDaoUtils.getCurrent() != null) {
            if (sDisposable != null){
                sDisposable.dispose();
                sDisposable =null;
            }
            sDisposable = Observable
                    .interval(10 * 60, TimeUnit.SECONDS)
                    //取消任务时取消定时唤醒
                    .doOnDispose(AbsWorkService::cancelJobAlarmSub)
//                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(count -> {
                        getData();
                    });
        }

    }

    protected void getData() {
        userActivities = null;
        userDevices = null;
        getUserDevices();
    }

    @Override
    public void stopWork(Intent intent, int flags, int startId) {
        stopService();
    }

    /**
     * 任务是否正在运行?
     *
     * @return 任务正在运行, true; 任务当前不在运行, false; 无法判断, 什么也不做, null.
     */
    @Override
    public Boolean isWorkRunning(Intent intent, int flags, int startId) {
        //若还没有取消订阅, 就说明任务仍在运行.
        return sDisposable != null && !sDisposable.isDisposed();
    }

    @Override
    public IBinder onBind(Intent intent, Void v) {
        return null;
    }

    @Override
    public void onServiceKilled(Intent rootIntent) {

    }

    protected void stream() {
        String header = "token " + "rc0L3E0YQRCpfa-gWVz0YQoStpnwE7QTmeN9x3y21Xig";
        String url = "https://www.streamr.com/api/v1/streams/" + "C93u3t13Tb6cBJUIO-O7nw" + "/data";
        if (userDevices == null)
            userDevices = "The Data not update!";
        if (userActivities == null)
            userActivities = "The Data not update!";
        Callback callback = new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
            }

            @Override
            public void onResponse(Response response) throws IOException {
            }
        };
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("User Devices", userDevices);
            jsonObject.put("User Activities", userActivities);
            if (WalletDaoUtils.getCurrent() != null) {
                jsonObject.put("EthAddress", WalletDaoUtils.getCurrent().getAddress());
            }
            UserInfoBean mUser = new UserInfoBean();
            mUser.setNickName(SharedPreferencesUtil.getInstance().getString("name"));
            mUser.setRegion(SharedPreferencesUtil.getInstance().getString("region"));
            mUser.setRace(SharedPreferencesUtil.getInstance().getString("race"));
            mUser.setAge(SharedPreferencesUtil.getInstance().getString("age"));
            mUser.setGender(SharedPreferencesUtil.getInstance().getString("gender"));
            Gson gson = new Gson();
            String userStr = gson.toJson(mUser);
            jsonObject.put("User Info", userStr);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        OkHttpUtil.httpPost(url, jsonObject.toString(), callback, header);
    }

    private void getUserDevices() {
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
                getUserActivities();
            }
        };
        sendRequest(url_devices, callback_devices);
    }

    protected String getUserActivities() {
        userActivities = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        String url_activities = "https://api.fitbit.com/1/user/-/activities/date/" + simpleDateFormat.format(date) + ".json";
        Callback callback_activities = new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
            }


            @Override
            public void onResponse(Response response) throws IOException {
                userActivities = response.body().string();
                stream();
            }
        };
        sendRequest(url_activities, callback_activities);
        return userActivities;
    }

    private void sendRequest(String url, Callback callback) {
        Request.Builder requestBuilder = new Request.Builder().url(url).addHeader("Authorization", "Bearer " + AuthenticationManager.getCurrentAccessToken().getAccessToken());
        final Request request = requestBuilder.build();
        OkHttpUtil.enqueue(request, callback);
    }
}
