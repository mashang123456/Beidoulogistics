package com.ue.beidoulogistics;

import android.app.Activity;
import android.view.Gravity;
import android.widget.Toast;

/**
 * 提示信息很常用，单独列出来
 */

public class BaseActivity extends Activity {
    /*
    * 在屏幕中央显示一个Toast
    * */
    public void showToast(CharSequence text){
        Toast toast = Toast.makeText(this, text, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
