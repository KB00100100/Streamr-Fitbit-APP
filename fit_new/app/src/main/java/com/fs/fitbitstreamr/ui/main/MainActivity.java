package com.fs.fitbitstreamr.ui.main;

import android.content.Intent;

import com.fs.fitbitstreamr.R;
import com.fs.fitbitstreamr.base.BaseActivity;
import com.fs.fitbitstreamr.test.TestActivity;

public class MainActivity extends BaseActivity {


    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initToolBar() {

    }

    @Override
    public void initDatas() {
//        startActivity(new Intent(mContext, TestActivity.class));
    }

    @Override
    public void configViews() {

    }
}
