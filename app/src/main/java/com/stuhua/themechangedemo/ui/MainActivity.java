package com.stuhua.themechangedemo.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.orhanobut.logger.Logger;
import com.stuhua.themechangedemo.R;
import com.stuhua.themechangedemo.utils.SPUtils;

import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

public class MainActivity extends AppCompatActivity {
    public Button btn1, btn2, btn3;
    public static final String KEY_MODE1 = "mode1";
    public static final String KEY_MODE2 = "mode2";
    public static final String KEY_MODE3 = "mode3";
    private DexClassLoader mDexClassLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean mode = (boolean) SPUtils.get(this, KEY_MODE1, true);
        if (mode) {
            setTheme(R.style.MyTheme);
        } else {
            setTheme(R.style.MyThemeNight);
        }
        setContentView(R.layout.activity_main);
        initView();
    }

    public void initView() {
        btn1 = getViewByID(R.id.btn1);
        btn2 = getViewByID(R.id.btn2);
        btn3 = getViewByID(R.id.btn3);

    }

    public <T extends View> T getViewByID(int id) {
        return (T) findViewById(id);
    }

    /**
     * 通过DexClassLoader加载dex中的xx方法
     */
    private void setContent() {
        try {
            Class clazz = mDexClassLoader.loadClass("com.stuhua.resource.UIUtil");
            Method method = clazz.getMethod("getTextString", Context.class);
            String str = (String) method.invoke(null, this);
            btn2.setText(str.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void changeTheme1(View view) {
        Logger.d("view=" + view.getId());
        boolean mode = (boolean) SPUtils.get(this, KEY_MODE1, true);
        SPUtils.put(this, KEY_MODE1, !mode);
        MainActivity.this.recreate();
    }

    public void changeTheme2(View view) {
        startActivity(new Intent());
    }

    public void changeTheme3(View view) {

    }

}
