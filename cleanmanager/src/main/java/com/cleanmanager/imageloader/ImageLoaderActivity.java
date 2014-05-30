package com.cleanmanager.imageloader;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;

import com.cleanmanager.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class ImageLoaderActivity extends ActionBarActivity {

    private volatile static ImageLoaderActivity instance;

    @InjectView(R.id.btnassets) Button btnAssets;
    @InjectView(R.id.btnurl) Button btnUrl;
    @InjectView(R.id.btnres) Button btnRes;
    @InjectView(R.id.image)  ImageView imgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imager_loader);

        ButterKnife.inject(this);

    }

    /*
    TODO Acceptable URIS examples in ImageLoader.
    String imageUri = "http://site.com/image.png"; // from Web
    String imageUri = "file:///mnt/sdcard/image.png"; // from SD card
    String imageUri = "content://media/external/audio/albumart/13"; // from content provider
    String imageUri = "assets://image.png"; // from assets
    String imageUri = "drawable://" + R.drawable.image; // from drawables (only images, non-9patch)
    */

    @OnClick(R.id.btnres)
    public void getImageFromRes(){
        String imageUri = "drawable://" + R.drawable.bg; // from drawables (only images, non-9patch)
        ImageLoader.getInstance().displayImage(imageUri,imgView);
    }

    @OnClick(R.id.btnurl)
    public void getImageFromUrl(){
        String imageUri = "http://c.hiphotos.baidu.com/image/pic/item/dbb44aed2e738bd4713eca94a38b87d6277ff97c.jpg"; // from Web
        ImageLoader.getInstance().displayImage(imageUri,imgView);

    }

    @OnClick(R.id.btnassets)
    public void getImageFromAssets(){
        String imageUri = "assets://bg.jpg"; // from assets
        ImageLoader.getInstance().displayImage(imageUri,imgView);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
