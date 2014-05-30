package com.cleanmanager.imageloader;

import android.app.Activity;
import android.app.Application;

import com.cleanmanager.R;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.memory.impl.FIFOLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.io.File;

/**
 * Created by sadieyu on 14-5-23.
 */
public class MyApplication extends Application {

    public OmniStorage mStorage;


    @Override
    public void onCreate() {
        super.onCreate();
        mStorage = new OmniStorage(getApplicationContext());
        mStorage.init();

        File cacheDir = mStorage.getCacheDir();

        DisplayImageOptions displayOptions = new DisplayImageOptions.Builder()
                .cacheOnDisk(true)
                .cacheInMemory(true)
                .displayer(new FadeInBitmapDisplayer(200, true, false, false))
                // always show this img before load real img from internet.
                .showImageOnLoading(R.drawable.logo)
                // show this img when the img is null
                .showImageForEmptyUri(R.drawable.logo)
                // show this img on error
                .showImageOnFail(R.drawable.logo)
                .resetViewBeforeLoading(true)
                .build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .threadPoolSize(2)
                .threadPriority(Thread.NORM_PRIORITY-1)
                .defaultDisplayImageOptions(displayOptions)
                .diskCache(new UnlimitedDiscCache(getCacheDir()))
                .memoryCache(new FIFOLimitedMemoryCache(3 * 1024 * 1024))
//                .writeDebugLogs()
                .build();

        ImageLoader.getInstance().init(config);

    }

    public static MyApplication getInstance(Activity context) {
        return (MyApplication) context.getApplication();
    }

}
