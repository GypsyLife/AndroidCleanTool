package com.cleanmanager;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by sadieyu on 14-5-30.
 */
public class Utils {

    public static void makeToast(Context context, String toastText) {

        Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show();

    }

}
