package com.fs.fitbitstreamr.test;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.animation.BaseAnimation;
import com.fs.fitbitstreamr.R;
import com.fs.fitbitstreamr.base.BaseActivity;
import com.xdandroid.hellodaemon.AbsWorkService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class TestActivity extends BaseActivity {

    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.rl_all)
    RelativeLayout rlAll;


    @BindView(R.id.view)
    View view;
    private List<WordsBean> listData;
    private WordsAdapter adapter;
    public static Disposable sDisposable, sDisposable2;


    @Override
    public int getLayoutId() {
        return R.layout.activity_test;
    }

    @Override
    public void initToolBar() {

    }

    @Override
    public void initDatas() {
        listData = new ArrayList<>();
        listData.add(new WordsBean("", 1f));
        listData.add(new WordsBean("", 1f));
        listData.add(new WordsBean("", 1f));
        listData.add(new WordsBean("", 1f));
        Animation animation1 = AnimationUtils.makeInAnimation(this, true);

//        for (int i = 0;i<1000;i++){
//            listData.add(new WordsBean("弹幕条数" + i, 1f));
//        }
//        ObjectAnimator objectAnimator02 = ObjectAnimator.ofFloat(tvW1, "TranslationX", -120, 0, 100);

    }

    private void makeAn() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater!=null){
            final View v = inflater.inflate(R.layout.vvv, null);
            final TextView textView = (TextView) v.findViewById(R.id.tv);
            textView.setText("  hahahhaha");
            rlAll.addView(textView);
            ObjectAnimator objectAnimator01 = ObjectAnimator.ofFloat(textView, "TranslationX", -dp2px(mContext, 100), 0).setDuration(400);
            ObjectAnimator objectAnimator02 = ObjectAnimator.ofFloat(textView, "TranslationY", 0, -dp2px(mContext, 160)).setDuration(13000);
            ObjectAnimator objectAnimator03 = ObjectAnimator.ofFloat(textView, "alpha", 1, 0).setDuration(9000);
            objectAnimator03.setStartDelay(3000);
            objectAnimator02.setInterpolator(new LinearInterpolator());
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(objectAnimator01, objectAnimator02, objectAnimator03);
            animatorSet.start();

            animatorSet.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    animatorSet.cancel();
                    rlAll.removeView(v);
                }
            });
        }

    }

    public static int dp2px(Context context, int dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    @Override
    public void configViews() {
        ScrollSpeedLinearLayoutManger layoutManager = new ScrollSpeedLinearLayoutManger(this);
        rv.setLayoutManager(layoutManager);
        adapter = new WordsAdapter(R.layout.item_word, listData);
        adapter.openLoadAnimation();
        adapter.openLoadAnimation(new BaseAnimation() {
            @Override
            public Animator[] getAnimators(View view) {
                return new Animator[]{
                        ObjectAnimator.ofFloat(view, "translationX", -100, 0).setDuration(200)
                };
            }
        });
        rv.setAdapter(adapter);
        sDisposable = Observable
                .interval(3, TimeUnit.SECONDS)
                .doOnDispose(AbsWorkService::cancelJobAlarmSub)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(count -> {
                    listData.add(new WordsBean("弹幕条数哈哈哈哈" + listData.size(), 1f));
                    adapter.notifyDataSetChanged();
                    rv.smoothScrollToPosition(listData.size());
                    makeAn();
//                    ObjectAnimator objectAnimator01 = ObjectAnimator.ofFloat(tvW1, "TranslationX", -100, 0).setDuration(200);
//                    ObjectAnimator objectAnimator02 = ObjectAnimator.ofFloat(tvW1, "TranslationY", 0, -300).setDuration(5000);
//                    AnimatorSet animatorSet=new AnimatorSet();
//                    animatorSet.playTogether(objectAnimator01,objectAnimator02);
//                    animatorSet.start();
//                    Animation animation2 = AnimationUtils.loadAnimation(this,R.anim.push_up_out);
//                    tvW1.setAnimation(animation2);
//                    tvW1.setAnimation(AnimationUtils.makeOutAnimation(TestActivity.this,true));

                });
        sDisposable2 = Observable
                .interval(500, TimeUnit.MILLISECONDS)
                .doOnDispose(AbsWorkService::cancelJobAlarmSub)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(count -> {
//                    for (int i = listData.size() - 5 >= 0 ? listData.size() - 5 : 0; i < listData.size(); i++) {
//                        listData.get(i).setAlpha(listData.get(i).getAlpha() - 0.04f > 0 ? listData.get(i).getAlpha() - 0.04f : 0f);
//                    }
//                    adapter.notifyDataSetChanged();
                });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (sDisposable != null) sDisposable.dispose();
        if (sDisposable2 != null) sDisposable2.dispose();
    }
}
