package com.stuhua.themechangedemo.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.orhanobut.logger.Logger;
import com.stuhua.themechangedemo.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void changeTheme1(View view) {
        Logger.d("view=" + view.getId());
    }

    public void changeTheme2(View view) {

    }

    public void changeTheme3(View view) {

    }

}
