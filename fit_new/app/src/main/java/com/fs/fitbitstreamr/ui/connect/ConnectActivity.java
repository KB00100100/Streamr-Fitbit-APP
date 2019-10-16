package com.fs.fitbitstreamr.ui.connect;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.fitbit.authentication.AuthenticationManager;
import com.fs.fitbitstreamr.R;
import com.fs.fitbitstreamr.base.BaseActivity;
import com.fs.fitbitstreamr.test.TestActivity;
import com.fs.fitbitstreamr.ui.login.LoginActivity;
import com.fs.fitbitstreamr.ui.main.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ConnectActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.lly_back)
    LinearLayout llyBack;
    @BindView(R.id.iv_btn)
    ImageView ivBtn;
    @BindView(R.id.rl_btn)
    LinearLayout rlBtn;
    @BindView(R.id.common_toolbar)
    Toolbar commonToolbar;
    @BindView(R.id.btn_authorize)
    Button btnAuthorize;

    @Override
    public int getLayoutId() {
        return R.layout.activity_connect;
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (AuthenticationManager.isLoggedIn()) {
            Intent intent = new Intent(mContext, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    public void initToolBar() {
        llyBack.setVisibility(View.GONE);
        tvTitle.setText("Connect to Streamr");
        tvTitle.setTextColor(0xffffffff);
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void configViews() {

    }


    @OnClick(R.id.btn_authorize)
    public void onViewClicked() {
        Intent intent = new Intent(mContext, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
