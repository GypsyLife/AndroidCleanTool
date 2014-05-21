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

/**
 *    Created by SadieYu
 */

public class AnimationActivity extends Activity {

    private RelativeLayout cleanInfo, cleanAnimation;
    private long avaliMemPre;
    private long avaliMemNow;
    private long totalMem;
    private android.os.Handler mHandler = new android.os.Handler();
    private static final long DELAY_TIME = 3000;
    ImageView roateImageView;
    TextView cleanInfoText ;
    TextView speedPercent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_animation);

        avaliMemPre = getAvailableMemory(this);
        totalMem = getTotalMemory(this);

        initViews();


        Animation animation = AnimationUtils.loadAnimation(this, R.anim.rotate_anim);
        DecelerateInterpolator di = new DecelerateInterpolator();// Speed--
//        LinearInterpolator lin = new LinearInterpolator();// Speed==
//        AccelerateInterpolator ai = new AccelerateInterpolator();// Speed++
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

    private void initViews() {
        cleanInfo = (RelativeLayout)findViewById(R.id.clean_info);
        cleanAnimation = (RelativeLayout)findViewById(R.id.clean_animation);
        roateImageView = (ImageView) findViewById(R.id.clean_rotate);
        cleanInfoText = (TextView) findViewById(R.id.clean_memory);
        speedPercent = (TextView)findViewById(R.id.speed_percent);
    }

    /**
     *  These code below are copied from Android ApiDemo
     *  if you want to know more about it you can alse see :
     *  http://developer.android.com/training/animation/cardflip.html
     */
    private Interpolator accelerator = new AccelerateInterpolator();
    private Interpolator decelerator = new DecelerateInterpolator();

    private void flipit() {
        ObjectAnimator visToInvis = ObjectAnimator.ofFloat(cleanAnimation, "rotationY", 0f, 90f);
        visToInvis.setDuration(500);
        visToInvis.setInterpolator(accelerator);
        final ObjectAnimator invisToVis = ObjectAnimator.ofFloat(cleanInfo, "rotationY",
                -90f, 0f);
        invisToVis.setDuration(500);
        invisToVis.setInterpolator(decelerator);
        visToInvis.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator anim) {
                cleanAnimation.setVisibility(View.GONE);
                cleanInfo.setVisibility(View.VISIBLE);
                invisToVis.start();

                avaliMemNow = getAvailableMemory(AnimationActivity.this);
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

    public static long getAvailableMemory(Context context) {
        //get current avaliable memory
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        return mi.availMem / (1024 * 1024);
    }

    public static long getTotalMemory(Context context) {
        String str1 = "/proc/meminfo";// 系统内存信息文件
        String str2;
        String[] arrayOfString;
        long initial_memory = 0;
        try {
            FileReader localFileReader = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(
                    localFileReader, 8192);
            str2 = localBufferedReader.readLine();// 读取meminfo第一行，系统总内存大小
            arrayOfString = str2.split("\\s+");
            initial_memory = Integer.valueOf(arrayOfString[1]).intValue() * 1024;// 获得系统总内存，单位是KB，乘以1024转换为Byte
            localBufferedReader.close();
        } catch (IOException e) {
        }
        //return Formatter.formatFileSize(context, initial_memory);// Byte转换为KB或者MB，内存大小规格化
        return initial_memory / (1024 * 1024);
    }

}

