package com.stuhua.themechangedemo.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.orhanobut.logger.Logger;
import com.stuhua.themechangedemo.R;

public class MainActivity extends AppCompatActivity {
    public Button btn1, btn2, btn3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_Night);
        setContentView(R.layout.activity_main);
    }

    public void initView() {
        btn1 = getViewByID(R.id.btn1);
        btn2 = getViewByID(R.id.btn2);
        btn3 = getViewByID(R.id.btn3);

    }

    public <T extends View> T getViewByID(int id) {
        return (T) findViewById(id);
    }

    public void changeTheme1(View view) {
        Logger.d("view=" + view.getId());
        MainActivity.this.recreate();
    }

    public void changeTheme2(View view) {

    }

    public void changeTheme3(View view) {

    }

}
