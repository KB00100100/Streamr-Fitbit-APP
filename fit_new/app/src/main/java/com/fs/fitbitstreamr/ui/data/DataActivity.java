package com.fs.fitbitstreamr.ui.data;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.fitbit.authentication.AuthenticationManager;
import com.fs.fitbitstreamr.R;
import com.fs.fitbitstreamr.base.BaseActivity;
import com.fs.fitbitstreamr.bean.ActivitysBean;
import com.fs.fitbitstreamr.utils.OkHttpUtil;
import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;

public class DataActivity extends BaseActivity {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.iv1)
    ImageView iv1;
    @BindView(R.id.iv2)
    ImageView iv2;
    @BindView(R.id.iv3)
    ImageView iv3;
    @BindView(R.id.iv4)
    ImageView iv4;

    @Override
    public int getLayoutId() {
        return R.layout.activity_data;
    }

    @Override
    public void initToolBar() {

    }

    @Override
    public void initDatas() {
        viewpager.setOffscreenPageLimit(4);
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                changeImg(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        String header = "Bearer " + AuthenticationManager.getCurrentAccessToken().getAccessToken();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        String url_activities = "https://api.fitbit.com/1/user/-/activities/date/" + simpleDateFormat.format(date) + ".json";
        new Thread(() -> stream(url_activities, header)).start();
    }

    @Override
    public void configViews() {

    }

    private void changeImg(int pos) {
        switch (pos) {
            case 0:
                iv1.setImageResource(R.mipmap.point1);
                iv2.setImageResource(R.mipmap.point0);
                iv3.setImageResource(R.mipmap.point0);
                iv4.setImageResource(R.mipmap.point0);
                break;
            case 1:
                iv1.setImageResource(R.mipmap.point0);
                iv2.setImageResource(R.mipmap.point1);
                iv3.setImageResource(R.mipmap.point0);
                iv4.setImageResource(R.mipmap.point0);
                break;
            case 2:
                iv1.setImageResource(R.mipmap.point0);
                iv2.setImageResource(R.mipmap.point0);
                iv3.setImageResource(R.mipmap.point1);
                iv4.setImageResource(R.mipmap.point0);
                break;
            case 3:
                iv1.setImageResource(R.mipmap.point0);
                iv2.setImageResource(R.mipmap.point0);
                iv3.setImageResource(R.mipmap.point0);
                iv4.setImageResource(R.mipmap.point1);
                break;
        }
    }

    protected void stream(String url, String header) {
        Callback callback = new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
            }

            @Override
            public void onResponse(com.squareup.okhttp.Response response) throws IOException {
                String data = response.body().string();
                ActivitysBean mActivitysBean = new Gson().fromJson(data, ActivitysBean.class);
                runOnUiThread(() -> {
                    if (viewpager != null)
                        viewpager.setAdapter(new DataPagerAdapter(getSupportFragmentManager(), 4, mActivitysBean));
                });
            }
        };
        OkHttpUtil.httpGet(url, callback, header);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
