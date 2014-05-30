package com.cleanmanager;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.cleanmanager.customerdialog.ConfirmDialog;
import com.cleanmanager.imageloader.ImageLoaderActivity;
import com.cleanmanager.picasso.PicassoActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class MainActivity extends ActionBarActivity {

    @InjectView(R.id.btn_clean_manager)
    Button btnCleanManager;

    @InjectView(R.id.btn_image_loader)
    Button btnImageLoader;

    @InjectView(R.id.btn_picasso)
    Button btnPicasso;

    @InjectView(R.id.btn_confirm_dialog)
    Button btnConfirmDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ButterKnife.inject(this);

    }

    @OnClick(R.id.btn_clean_manager)
    public void invokeCleanManager(Button btn){

         Intent cleanManager = new Intent(this,AnimationActivity.class);
         startActivity(cleanManager);

    }

    @OnClick(R.id.btn_image_loader)
    public void invokeImageLoader(Button btn){

        Intent imageLoader = new Intent(this,ImageLoaderActivity.class);
        startActivity(imageLoader);

    }

    @OnClick(R.id.btn_picasso)
    public void invokePicasso(Button btn){

        Intent picasso = new Intent(this,PicassoActivity.class);
        startActivity(picasso);

    }

    @OnClick(R.id.btn_confirm_dialog)
    public void invokeConfirmDialog(Button btn){

        ConfirmDialog dialog = ConfirmDialog.newInstance("Customer Dialog","This is a customer dialog demo .","OK","Cancel");
        dialog.setOnDialogBtnClickListener(new ConfirmDialog.OnDialogActionListener() {
            @Override
            public void onOKClick() {
                Utils.makeToast(MainActivity.this,"OK Button Clicked .");
            }

            @Override
            public void onCancel() {
                Utils.makeToast(MainActivity.this,"Cancel Button Clicked .");
            }
        });
        dialog.show(getSupportFragmentManager(),"confirm_dialog");

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.splash, menu);
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
