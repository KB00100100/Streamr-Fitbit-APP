package com.fs.fitbitstreamr;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import androidx.multidex.MultiDexApplication;

import com.fitbit.authentication.AuthenticationConfiguration;
import com.fitbit.authentication.AuthenticationConfigurationBuilder;
import com.fitbit.authentication.AuthenticationManager;
import com.fitbit.authentication.ClientCredentials;
import com.fitbit.authentication.Scope;
import com.fs.fitbitstreamr.domain.DaoMaster;
import com.fs.fitbitstreamr.domain.DaoSession;
import com.fs.fitbitstreamr.service.DeService;
import com.fs.fitbitstreamr.ui.login.LoginActivity;
import com.fs.fitbitstreamr.utils.AppFilePath;
import com.fs.fitbitstreamr.utils.AppUtils;
import com.fs.fitbitstreamr.utils.SharedPreferencesUtil;
import com.lzy.okgo.OkGo;
import com.xdandroid.hellodaemon.DaemonEnv;

import static com.fitbit.authentication.Scope.activity;

public class FlyApplication extends MultiDexApplication {
    private static FlyApplication sInstance;

    private DaoSession daoSession;

    public DaoSession getDaoSession() {
        return daoSession;
    }

    private static final String CLIENT_SECRET = "53e15fa319e9def0b67b4242837e8f9a";
    private static final String SECURE_KEY = "CVPdQNAT6fBI4rrPLEn9x0+UV84DoqLFiNHpKOPLRW0=";

    public static AuthenticationConfiguration generateAuthenticationConfiguration(Context context, Class<? extends Activity> mainActivityClass) {

        try {
            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle = ai.metaData;

            String clientId = bundle.getString("com.fs.fitbitstreamr.CLIENT_ID");
            String redirectUrl = bundle.getString("com.fs.fitbitstreamr.REDIRECT_URL");


            ClientCredentials CLIENT_CREDENTIALS = new ClientCredentials(clientId, CLIENT_SECRET, redirectUrl);

            return new AuthenticationConfigurationBuilder()

                    .setClientCredentials(CLIENT_CREDENTIALS)
                    .setEncryptionKey(SECURE_KEY)
                    .setTokenExpiresIn(2592000L) // 30 days
                    .setBeforeLoginActivity(new Intent(context, mainActivityClass))
                    .addRequiredScopes( Scope.settings)
                    .addOptionalScopes(activity, Scope.weight)
                    .setLogoutOnAuthFailure(true)

                    .build();

        } catch (Exception e) {
            Log.e("sdfsdf",e.toString());
            throw new RuntimeException(e);
        }
    }
    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        AppUtils.init(this);
        AppFilePath.init(this);
        initPrefs();
        initGreenDao();
        OkGo.getInstance().init(this);
        DaemonEnv.initialize(this, DeService.class, DaemonEnv.DEFAULT_WAKE_UP_INTERVAL);
        AuthenticationManager.configure(this, generateAuthenticationConfiguration(this, LoginActivity.class));
    }

    private void initGreenDao() {
        //创建数据库表
        DaoMaster.DevOpenHelper mHelper = new DaoMaster.DevOpenHelper(this, "wallet", null);
        SQLiteDatabase db = mHelper.getWritableDatabase();
        daoSession = new DaoMaster(db).newSession();
    }

    public static FlyApplication getsInstance() {
        return sInstance;
    }

    /**
     * 初始化SharedPreference
     */
    protected void initPrefs() {
        SharedPreferencesUtil.init(getApplicationContext(), getPackageName() + "_preference", Context.MODE_MULTI_PROCESS);
    }
}
