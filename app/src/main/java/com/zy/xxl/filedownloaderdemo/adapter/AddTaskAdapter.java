package com.zy.xxl.filedownloaderdemo.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadSampleListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.model.FileDownloadStatus;
import com.liulishuo.filedownloader.util.FileDownloadUtils;
import com.zy.xxl.filedownloaderdemo.R;
import com.zy.xxl.filedownloaderdemo.manager.TasksManager;
import com.zy.xxl.filedownloaderdemo.model.AddTaskModel;

import java.util.List;

/**
 * Author ： zhangyang
 * Date   ： 2017/10/31
 * Email  :  18610942105@163.com
 * Description  :
 */

public class AddTaskAdapter extends BaseQuickAdapter<AddTaskModel, BaseViewHolder> {

    private Context context;

    public AddTaskAdapter(int layoutResId, @Nullable List<AddTaskModel> data, Context context) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final AddTaskModel model) {
        Glide.with(context)
                .load(model.picUrl)
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round)
                .into((ImageView) helper.getView(R.id.icon));

        helper.setText(R.id.task_name_tv, model.name);

        helper.getView(R.id.task_action_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TasksManager manager = TasksManager.getImpl();
                manager.addTask(model.url);
                final BaseDownloadTask task = FileDownloader.getImpl().create(model.url)
                        .setPath(manager.createPath(model.url))
                        .setCallbackProgressTimes(100)
                        .setListener(new FileDownloadSampleListener(){
                            @Override
                            protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                                super.pending(task, soFarBytes, totalBytes);
                                helper.setText(R.id.task_status_tv, "pending 等待中");
                            }

                            @Override
                            protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                                super.progress(task, soFarBytes, totalBytes);
                                helper.setText(R.id.task_status_tv, "progress 下载中");
                            }

                            @Override
                            protected void blockComplete(BaseDownloadTask task) {
                                super.blockComplete(task);
                            }

                            @Override
                            protected void completed(BaseDownloadTask task) {
                                super.completed(task);
                                helper.setText(R.id.task_status_tv, "completed 下载完成");
                            }

                            @Override
                            protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                                super.paused(task, soFarBytes, totalBytes);
                            }

                            @Override
                            protected void error(BaseDownloadTask task, Throwable e) {
                                super.error(task, e);
                                helper.setText(R.id.task_status_tv, "error 下载错误");
                            }

                            @Override
                            protected void warn(BaseDownloadTask task) {
                                super.warn(task);
                                helper.setText(R.id.task_status_tv, "warn 下载错误");
                            }
                        });
                TasksManager.getImpl().addTaskForViewHolder(task);
                task.start();
            }
        });

        if (TasksManager.getImpl().isReady()) {
            final int status = TasksManager.getImpl().getStatus(FileDownloadUtils.generateId(model.url, TasksManager.getImpl().createPath(model.url))
                    , TasksManager.getImpl().createPath(model.url));
            if (status == FileDownloadStatus.pending || status == FileDownloadStatus.started ||
                    status == FileDownloadStatus.connected) {
                // start task, but file not created yet
                helper.setText(R.id.task_status_tv, "progress 下载中");
            } else if (TasksManager.getImpl().isDownloaded(status)) {
                // already downloaded and exist
                helper.setText(R.id.task_status_tv, "completed 下载完成");
            } else if (status == FileDownloadStatus.progress) {
                // downloading
                helper.setText(R.id.task_status_tv, "progress 下载中");
            } else {
                // not start
                helper.setText(R.id.task_status_tv, "not start 未开始");
            }
        } else {

        }
    }

}
