package com.fs.fitbitstreamr.ui.data;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.fs.fitbitstreamr.base.BaseFragment;
import com.fs.fitbitstreamr.bean.ActivitysBean;


public class DataPagerAdapter extends FragmentPagerAdapter {
    private BaseFragment fragment;
    private  int size;
    private ActivitysBean activitysBean;

    public DataPagerAdapter(@NonNull FragmentManager fm, int behavior, ActivitysBean activitysBean) {
        super(fm, behavior);
        this.size = behavior;
        this.activitysBean = activitysBean;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return PaiHangFragmentFactory.createFragment(position,activitysBean);
    }

    @Override
    public int getCount() {
        return size;
    }
}
