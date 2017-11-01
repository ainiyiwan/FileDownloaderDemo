package com.zy.xxl.filedownloaderdemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zy.xxl.filedownloaderdemo.R;
import com.zy.xxl.filedownloaderdemo.adapter.AddTaskAdapter;
import com.zy.xxl.filedownloaderdemo.constant.Constant;
import com.zy.xxl.filedownloaderdemo.manager.TasksManager;
import com.zy.xxl.filedownloaderdemo.model.AddTaskModel;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddTaskActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private AddTaskAdapter addTaskAdapter;
    private List<AddTaskModel> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        ButterKnife.bind(this);

        TasksManager.getImpl().onCreate(new WeakReference<>(this));

        getData();
        addTaskAdapter = new AddTaskAdapter(R.layout.task_manager_item, list, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(addTaskAdapter);

    }

    private void getData() {
        final int demoSize = Constant.BIG_FILE_URLS.length;
        for (int i = 0; i < demoSize; i++) {
            AddTaskModel model = new AddTaskModel(Constant.BING_FILE_NAME[i],
                    Constant.BIG_FILE_URLS[i],
                    Constant.BIG_ICON_URLS[i]);
            list.add(model);
        }
    }

    public void postNotifyDataChanged() {
        if (addTaskAdapter != null) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (addTaskAdapter != null) {
                        addTaskAdapter.notifyDataSetChanged();
                    }
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        FileDownloader.getImpl().pauseAll();
//        DownloadManager.getImpl().removeUpdater(aActivityUpdater);


    }
}
