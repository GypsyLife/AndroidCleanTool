package com.cleanmanager.picasso;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.cleanmanager.Miscellaneous;
import com.cleanmanager.R;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class PicassoActivity extends ActionBarActivity {

    @InjectView(R.id.image) ImageView imageView;

    @InjectView(R.id.btnurl) Button btnUrl;

    @InjectView(R.id.btnres) Button btnAssets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picasso);

        ButterKnife.inject(this);

}

    @OnClick(R.id.btnurl)
    public void loadImageFromUrl(Button btn){
        Picasso.with(this)
                .load("http://c.hiphotos.baidu.com/image/pic/item/dbb44aed2e738bd4713eca94a38b87d6277ff97c.jpg")
                .placeholder(R.drawable.logo)
                .error(R.drawable.logo)
                .into(imageView);
    }

    @OnClick(R.id.btnres)
    public void loadImageFromRes(Button btn){
        Picasso.with(this)
                .load(R.drawable.bg)
                .placeholder(R.drawable.logo)
                .error(R.drawable.logo)
                .into(imageView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.picasso, menu);
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
        } else if (id == R.id.action_selecetd){
            Miscellaneous.makeToast(this,"Menu selected ...");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
