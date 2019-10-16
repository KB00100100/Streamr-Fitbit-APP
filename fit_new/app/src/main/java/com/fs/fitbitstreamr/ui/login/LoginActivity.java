package com.fs.fitbitstreamr.ui.login;

import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;

import com.fitbit.authentication.AuthenticationHandler;
import com.fitbit.authentication.AuthenticationManager;
import com.fitbit.authentication.AuthenticationResult;
import com.fitbit.authentication.Scope;
import com.fs.fitbitstreamr.ui.main.MainActivity;
import com.fs.fitbitstreamr.R;
import com.fs.fitbitstreamr.base.BaseActivity;
import com.fs.fitbitstreamr.ui.setting.SettingActivity;

import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements AuthenticationHandler {


    @BindView(R.id.login_button)
    Button loginButton;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login_main;
    }

    @Override
    public void initToolBar() {

    }

    @Override
    public void initDatas() {

    }

    @Override
    public void configViews() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (AuthenticationManager.isLoggedIn()) {
            onLoggedIn();
        }
    }


    @OnClick(R.id.login_button)
    public void onViewClicked() {
        AuthenticationManager.login(this);
    }
    public void onLoggedIn() {

        Intent intent = new Intent(mContext, SettingActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (!AuthenticationManager.onActivityResult(requestCode, resultCode, data, this)) {
        }

    }

    public void onAuthFinished(AuthenticationResult authenticationResult) {
        if (authenticationResult.isSuccessful()) {
            onLoggedIn();
        } else {
            displayAuthError(authenticationResult);
        }
    }

    private void displayAuthError(AuthenticationResult authenticationResult) {
        String message = "";

        switch (authenticationResult.getStatus()) {
            case dismissed:
                message = "取消";
                break;
            case error:
                message = authenticationResult.getErrorMessage();
                break;
            case missing_required_scopes:
                Set<Scope> missingScopes = authenticationResult.getMissingScopes();
                message = TextUtils.join(", ", missingScopes);
                break;
        }

        new AlertDialog.Builder(this)
                .setTitle("登录")
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                })
                .create()
                .show();
    }
}
