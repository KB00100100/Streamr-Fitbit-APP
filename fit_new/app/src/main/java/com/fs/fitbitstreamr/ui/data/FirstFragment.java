package com.fs.fitbitstreamr.ui.data;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.fs.fitbitstreamr.R;
import com.fs.fitbitstreamr.base.BaseFragment;
import com.fs.fitbitstreamr.bean.ActivitysBean;
import com.fs.fitbitstreamr.ui.view.CirclePercentView;

import butterknife.BindView;

public class FirstFragment extends BaseFragment {
    @BindView(R.id.cp)
    CirclePercentView cp;
    @BindView(R.id.tv_1)
    TextView tv1;
    @BindView(R.id.tv_2)
    TextView tv2;
    private int position;
    private ActivitysBean bean;

    public FirstFragment(int position, ActivitysBean bean) {
        this.position = position;
        this.bean = bean;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_layout;
    }

    @Override
    protected void initView(View convertView) {
        Log.e("asdfsdfdf", bean.toString());
        switch (position) {
            case 0:
                cp.setBgColor(0xFFF2F2F2);
                cp.setProgressColor(0xFF1CD760);
                if (bean != null && bean.getSummary() != null && bean.getGoals() != null) {
                    tv1.setText(bean.getSummary().getSteps() + "");
                    tv2.setText("STEPS");
                    cp.setPercentage(bean.getGoals().getSteps() == 0 ? 0 : bean.getSummary().getSteps() / bean.getGoals().getSteps() * 100);
                } else {
                    tv1.setText("0");
                    tv2.setText("STEPS");
                    cp.setPercentage(0);
                }
                break;
            case 1:
                cp.setBgColor(0xFFF2F2F2);
                cp.setProgressColor(0xFF38E4B1);
                if (bean != null && bean.getSummary() != null && bean.getGoals() != null) {
                    tv1.setText(bean.getSummary().getDistances().get(0).getDistance() + "");
                    tv2.setText("KM");
                    double temp = bean.getGoals().getDistance() == 0 ? 0 : bean.getSummary().getDistances().get(0).getDistance() / bean.getGoals().getDistance() * 100;
                    cp.setPercentage(Float.parseFloat(Double.toString(temp)));
                } else {
                    tv1.setText("0");
                    tv2.setText("KM");
                    cp.setPercentage(0);
                }

                break;
            case 2:
                cp.setBgColor(0xFFF2F2F2);
                cp.setProgressColor(0xFFD7731D);
                if (bean != null && bean.getSummary() != null && bean.getGoals() != null) {
                    tv1.setText(bean.getSummary().getCaloriesOut() + "");
                    tv2.setText("CALORIES");
                    cp.setPercentage(bean.getGoals().getCaloriesOut() == 0 ? 0 : bean.getSummary().getCaloriesOut() / bean.getGoals().getCaloriesOut() * 100);
                } else {
                    tv1.setText("0");
                    tv2.setText("CALORIES");
                    cp.setPercentage(0);
                }
                break;
            case 3:
                cp.setBgColor(0xFFF2F2F2);
                cp.setProgressColor(0xFF3F92EB);
                if (bean != null && bean.getSummary() != null && bean.getGoals() != null) {
                    tv1.setText(bean.getSummary().getFloors() + "");
                    tv2.setText("FLOORS");
                    cp.setPercentage(bean.getGoals().getFloors() == 0 ? 0 : bean.getSummary().getFloors() / bean.getGoals().getFloors() * 100);
                } else {
                    tv1.setText("0");
                    tv2.setText("FLOORS");
                    cp.setPercentage(0);
                }
                break;
        }


    }

    @Override
    protected void initData() {

    }
}
