package com.fs.fitbitstreamr.ui.personal;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fs.fitbitstreamr.R;
import com.fs.fitbitstreamr.base.BaseActivity;
import com.fs.fitbitstreamr.loadding.ListPopup;
import com.fs.fitbitstreamr.ui.data.DataActivity;
import com.fs.fitbitstreamr.utils.SharedPreferencesUtil;
import com.fs.fitbitstreamr.utils.ToastUtils;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PersonalActivity extends BaseActivity {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_name)
    TextInputEditText tvName;
    @BindView(R.id.tv_gender)
    TextInputEditText tvGender;
    @BindView(R.id.tv_age)
    TextInputEditText tvAge;
    @BindView(R.id.tv_race)
    TextInputEditText tvRace;
    @BindView(R.id.tv_region)
    TextInputEditText tvRegion;
    @BindView(R.id.btn_view_data)
    Button btnViewData;
    @BindView(R.id.btn_done)
    Button btnDone;
    @BindView(R.id.input1)
    RelativeLayout input1;
    @BindView(R.id.input2)
    RelativeLayout input2;
    private ListPopup mListPopup;

    @Override
    public int getLayoutId() {
        return R.layout.activity_personal;
    }

    @Override
    public void initToolBar() {
        tvTitle.setText("Personal Information");
    }

    @Override
    public void initDatas() {
        tvName.setText(SharedPreferencesUtil.getInstance().getString("name"));
        tvGender.setText(SharedPreferencesUtil.getInstance().getString("gender"));
        tvAge.setText(SharedPreferencesUtil.getInstance().getString("age"));
        tvRace.setText(SharedPreferencesUtil.getInstance().getString("race"));
        tvRegion.setText(SharedPreferencesUtil.getInstance().getString("region"));

        tvName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                SharedPreferencesUtil.getInstance().putString("name", editable.toString());
            }
        });
        tvGender.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                SharedPreferencesUtil.getInstance().putString("gender", editable.toString());
            }
        });
        tvAge.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                SharedPreferencesUtil.getInstance().putString("age", editable.toString());
            }
        });
        tvRace.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                SharedPreferencesUtil.getInstance().putString("race", editable.toString());
            }
        });
        tvRegion.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                SharedPreferencesUtil.getInstance().putString("region", editable.toString());
            }
        });

        input1.setOnClickListener(v -> {
            ListPopup.Builder builder = new ListPopup.Builder(PersonalActivity.this);
            builder.addItem("male");
            builder.addItem("female");
            mListPopup = builder.build();
            mListPopup.showPopupWindow();
            mListPopup.setOnListPopupItemClickListener(what -> {
                SharedPreferencesUtil.getInstance().putString("gender", what + "");
                tvGender.setText(what + "");
                mListPopup.dismiss();
            });
        });

        input2.setOnClickListener(v -> {
            ListPopup.Builder builder = new ListPopup.Builder(PersonalActivity.this);
            for (int i = 0; i < 120 / 5; i++) {
                builder.addItem(i * 5 + 1 + "~" + (i + 1) * 5);
            }
            mListPopup = builder.build();
            mListPopup.showPopupWindow();
            mListPopup.setOnListPopupItemClickListener(what -> {
                SharedPreferencesUtil.getInstance().putString("age", what + "");
                mListPopup.dismiss();
                tvAge.setText(what + "");
            });
        });
    }

    @Override
    public void configViews() {

    }


    @OnClick({R.id.btn_view_data, R.id.btn_done})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_view_data:
                startActivity(new Intent(mContext, DataActivity.class));
                break;
            case R.id.btn_done:
                ToastUtils.showLongToast("all saved");
                break;
        }
    }


}
