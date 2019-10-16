package com.fs.fitbitstreamr.test;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fs.fitbitstreamr.R;

import java.util.List;

public class WordsAdapter extends BaseQuickAdapter<WordsBean, BaseViewHolder> {
    private List<WordsBean> data;

    public WordsAdapter(int layoutResId, @Nullable List<WordsBean> data) {
        super(layoutResId, data);
        this.data = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, WordsBean item) {
        helper.setText(R.id.item_tx, item.getWords());
//        helper.setAlpha(R.id.item_tx,item.getAlpha());
        TextView tv = helper.getView(R.id.item_tx);
//        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(tv,"alpha",1f,0f);
//        objectAnimator.setDuration(10000);
//        objectAnimator.start();
    }
}
