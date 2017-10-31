package com.zy.xxl.filedownloaderdemo.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.zy.xxl.filedownloaderdemo.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //添加下载
    public void addTask(View view) {

    }

    //任务管理器
    public void taskManager(View view) {
        startActivity(new Intent(MainActivity.this, TasksManagerDemoActivity.class));
    }
}
