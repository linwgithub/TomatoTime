package com.linw.tomatotime.util;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by pollysoft on 16/3/18.
 */
public class ToastUtil {

    public static void showToast(Context context, String str) {
        Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(Context context, int str) {
        String toastStr = context.getResources().getString(str);
        Toast.makeText(context, toastStr, Toast.LENGTH_SHORT).show();
    }

    public static void showBottomToast(Context context, String str) {
        Toast toast = Toast.makeText(context, str, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM, 0, 0);
        toast.show();
    }
}
