package com.fs.fitbitstreamr.ui.data;

import com.fs.fitbitstreamr.base.BaseFragment;
import com.fs.fitbitstreamr.bean.ActivitysBean;

public class PaiHangFragmentFactory {
    private static final int FIRST_FRAGMENT = 0;
    private static final int SECOND_FRAGMENT = 1;
    private static final int Third_FRAGMENT = 2;
    private static final int FORTH_FRAGMENT = 3;

    public static BaseFragment createFragment(int position, ActivitysBean bean) {
        return new FirstFragment(position, bean);
    }
}
