package com.zy.xxl.filedownloaderdemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.zy.xxl.filedownloaderdemo.R;
import com.zy.xxl.filedownloaderdemo.adapter.AddTaskAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddTaskActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private AddTaskAdapter addTaskAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        ButterKnife.bind(this);

//        TasksManager.getImpl().onCreate(new WeakReference<>(this));
    }
}
