package com.zy.xxl.filedownloaderdemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.util.FileDownloadUtils;
import com.zy.xxl.filedownloaderdemo.R;
import com.zy.xxl.filedownloaderdemo.adapter.TaskItemAdapter;
import com.zy.xxl.filedownloaderdemo.db.TasksManagerModel;
import com.zy.xxl.filedownloaderdemo.manager.DownloadManager;
import com.zy.xxl.filedownloaderdemo.manager.TasksManager;
import com.zy.xxl.filedownloaderdemo.util.CleanUtils;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TasksManagerDemoActivity extends AppCompatActivity {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.iv_control_download)
    ImageView mControlDownloadIv;
    @BindView(R.id.control_download)
    TextView mControlDownloadtv;
    @BindView(R.id.ll_control_download)
    LinearLayout mControlDownloadLl;
    @BindView(R.id.del_all)
    TextView delAll;
    private TaskItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks_manager_demo);
        ButterKnife.bind(this);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter = new TaskItemAdapter());


        TasksManager.getImpl().onCreate1(new WeakReference<>(this));
        TasksManager.getImpl().initData();

        FileDownloader.getImpl().pauseAll();
    }

    public void postNotifyDataChanged() {
        if (adapter != null) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (adapter != null) {
                        adapter.notifyDataSetChanged();
                    }
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DownloadManager.getImpl().clearUpdater();
    }

    @OnClick(R.id.ll_control_download)
    public void onViewClicked() {
        if (mControlDownloadtv.getText().toString().equals(getString(R.string.course_all_stop))) {
            mControlDownloadtv.setText(getString(R.string.course_all_start));
            mControlDownloadIv.setBackgroundResource(R.mipmap.ic_download_start);
            FileDownloader.getImpl().pauseAll();
        } else {
            mControlDownloadtv.setText(getString(R.string.course_all_stop));
            mControlDownloadIv.setBackgroundResource(R.mipmap.ic_download_stop);
            final List<TasksManagerModel> modelList = TasksManager.getImpl().initData();
            for (TasksManagerModel model : modelList) {
                DownloadManager.getImpl().startDownload(model.getUrl(), TasksManager.getImpl().createPath(model
                        .getName()));
            }
            adapter.notifyDataSetChanged();
        }
    }

    @OnClick(R.id.del_all)
    public void onDelClicked() {
        TasksManager.getImpl().initData();
        FileDownloader.getImpl().pauseAll();
        boolean delSuc = TasksManager.getImpl().delAll();
        if (delSuc){
            File file = new File(FileDownloadUtils.getDefaultSaveRootPath() + File.separator + "MRXZMedia");
            CleanUtils.cleanCustomCache(file);
            TasksManager.getImpl().initData();
            FileDownloader.getImpl().clearAllTaskData();
            adapter.notifyDataSetChanged();
        }
    }

}
