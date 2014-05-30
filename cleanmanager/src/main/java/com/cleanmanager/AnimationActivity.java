package com.cleanmanager;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 *    Created by SadieYu
 */

public class AnimationActivity extends Activity {

    //inject views
    //see more infomation about Butter Knife at
    //http://jakewharton.github.io/butterknife/
    @InjectView(R.id.clean_info) RelativeLayout cleanInfo;
    @InjectView(R.id.clean_animation) RelativeLayout cleanAnimation;
    @InjectView(R.id.clean_rotate) ImageView roateImageView;
    @InjectView(R.id.clean_memory) TextView cleanInfoText;
    @InjectView(R.id.speed_percent) TextView speedPercent;

    private long avaliMemPre;
    private long avaliMemNow;
    private long totalMem;
    private android.os.Handler mHandler = new android.os.Handler();

    private static final long DELAY_TIME = 3000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_animation);

        //inject views
        ButterKnife.inject(this);
        avaliMemPre = Miscellaneous.getAvailableMemory(this);
        totalMem = Miscellaneous.getTotalMemory(this);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.rotate_anim);
        DecelerateInterpolator di = new DecelerateInterpolator();//animation Speed--
//        LinearInterpolator lin = new LinearInterpolator();//animation Speed==
//        AccelerateInterpolator ai = new AccelerateInterpolator();//animation Speed++
        if(animation == null){
            return;
        }
        animation.setInterpolator(di);
        roateImageView.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                Miscellaneous.clearMemory(AnimationActivity.this);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                flipit();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
	}

    /**
     *  These code below are copied from Android ApiDemo
     *  if you want to know more about it you can also see :
     *  http://developer.android.com/training/animation/cardflip.html
     */
    private Interpolator accelerator = new AccelerateInterpolator();
    private Interpolator decelerator = new DecelerateInterpolator();

    private void flipit() {
        ObjectAnimator visToInvis = ObjectAnimator.ofFloat(cleanAnimation, "rotationY", 0f, 90f);
        visToInvis.setDuration(500);
        visToInvis.setInterpolator(accelerator);
        final ObjectAnimator invisToVis = ObjectAnimator.ofFloat(cleanInfo, "rotationY",-90f, 0f);
        invisToVis.setDuration(500);
        invisToVis.setInterpolator(decelerator);
        visToInvis.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator anim) {
                cleanAnimation.setVisibility(View.GONE);
                cleanInfo.setVisibility(View.VISIBLE);
                invisToVis.start();

                avaliMemNow = Miscellaneous.getAvailableMemory(AnimationActivity.this);
                long speed = avaliMemNow - avaliMemPre;
                float percent = ((float)speed / (float)totalMem) * 100;

                if(speed < 0l ){
                    cleanInfoText.setText("0Mb");
                    speedPercent.setText("0%");
                }else{
                    DecimalFormat df= new DecimalFormat("##0.0");
                    String speedInfoText = df.format(speed);
                    String percentInfoText = df.format(percent);

                    cleanInfoText.setText(speedInfoText + "Mb");
                    speedPercent.setText(percentInfoText + "%");
                }
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        AnimationActivity.this.finish();
                    }
                },DELAY_TIME);
            }
        });
        visToInvis.start();
    }
}

