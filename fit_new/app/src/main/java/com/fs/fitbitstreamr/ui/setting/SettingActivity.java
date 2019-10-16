package com.fs.fitbitstreamr.ui.setting;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fs.fitbitstreamr.R;
import com.fs.fitbitstreamr.base.BaseActivity;
import com.fs.fitbitstreamr.service.DeService;
import com.fs.fitbitstreamr.ui.personal.PersonalActivity;
import com.fs.fitbitstreamr.ui.wallet.CreateWalletActivity;
import com.fs.fitbitstreamr.ui.wallet.WalletDetailActivity;
import com.fs.fitbitstreamr.utils.ETHMnemonic;
import com.fs.fitbitstreamr.utils.SharedPreferencesUtil;
import com.fs.fitbitstreamr.utils.WalletDaoUtils;
import com.google.android.material.textfield.TextInputEditText;
import com.xdandroid.hellodaemon.DaemonEnv;
import com.xdandroid.hellodaemon.IntentWrapper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jnr.ffi.annotations.In;

public class SettingActivity extends BaseActivity {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.lly_back)
    LinearLayout llyBack;
    @BindView(R.id.tv_stream_id)
    TextInputEditText tvStreamId;
    @BindView(R.id.tv_key)
    TextInputEditText tvKey;
    @BindView(R.id.btn_sure)
    Button btnSure;
    @BindView(R.id.btn_wallet)
    Button btnWallet;
    @BindView(R.id.btn_personal)
    Button btnPersonal;

    @Override
    public int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    public void initToolBar() {
        tvTitle.setText("Settings");
        llyBack.setVisibility(View.GONE);
        tvTitle.setTextColor(0xffffffff);
    }

    @Override
    public void initDatas() {
//        tvStreamId.setText(SharedPreferencesUtil.getInstance().getString("streamId"));
//        tvKey.setText(SharedPreferencesUtil.getInstance().getString("key"));
//        tvStreamId.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                SharedPreferencesUtil.getInstance().putString("streamId", editable.toString());
//            }
//        });
//        tvKey.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                SharedPreferencesUtil.getInstance().putString("key", editable.toString());
//            }
//        });
        if (DeService.isWorking()){
            btnSure.setText("Working...");
        }else{
            btnSure.setText("STREAM TO MARKETPLACE");
        }
    }

    @Override
    public void configViews() {

    }


    @OnClick({R.id.btn_sure, R.id.btn_wallet, R.id.btn_personal})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_sure:
                if (!TextUtils.isEmpty(tvStreamId.getText())&&!TextUtils.isEmpty(tvKey.getText())){
                    if (DeService.isWorking()){
                        DeService.stopService();
                        btnSure.setText("STREAM TO MARKETPLACE");
                    }else{
                        DeService.sShouldStopService = false;
                        DaemonEnv.startServiceMayBind(DeService.class);
                        btnSure.setText("Working...");
                    }
                }
                break;
            case R.id.btn_wallet:
                if (WalletDaoUtils.getCurrent()==null){
                    startActivity(new Intent(mContext, CreateWalletActivity.class));
                }else {
                    startActivity(new Intent(mContext, WalletDetailActivity.class));
                }
                break;
            case R.id.btn_personal:
                startActivity(new Intent(mContext, PersonalActivity.class));
                break;
        }
    }
    public void onBackPressed() {
        IntentWrapper.onBackPressed(this);
    }
}
