package com.zy.xxl.filedownloaderdemo.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.model.FileDownloadStatus;
import com.liulishuo.filedownloader.util.FileDownloadUtils;
import com.zy.xxl.filedownloaderdemo.R;
import com.zy.xxl.filedownloaderdemo.manager.DownloadManager;
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
    private BaseDownloadTask task;

    public AddTaskAdapter(int layoutResId, @Nullable List<AddTaskModel> data, Context context) {
        super(layoutResId, data);
        this.context = context;
    }

    public void setTask(BaseDownloadTask task){
        this.task  = task;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final AddTaskModel model) {
        Glide.with(context)
                .load(model.picUrl)
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round)
                .into((ImageView) helper.getView(R.id.icon));

        helper.setText(R.id.task_name_tv, model.name);

        final int id = FileDownloadUtils.generateId(model.url, TasksManager.getImpl().createPath(model.name));
        if (TasksManager.getImpl().isReady()) {
            final int status = TasksManager.getImpl().getStatus(id, TasksManager.getImpl().createPath(model.name));
            if (status == FileDownloadStatus.pending) {
                // start task, but file not created yet
                helper.setText(R.id.task_status_tv, "progress 等待中");
            } else if (status == FileDownloadStatus.connected){
                helper.setText(R.id.task_status_tv, "connected 连接中");
            } else if (status == FileDownloadStatus.started){
                helper.setText(R.id.task_status_tv, "start 开始");
            }else if (status == FileDownloadStatus.paused){
                helper.setText(R.id.task_status_tv, "pause 暂停");
            }else if (TasksManager.getImpl().isDownloaded(status)) {
                // already downloaded and exist
                helper.setText(R.id.task_status_tv, "completed 下载完成");
            } else if (status == FileDownloadStatus.progress) {
                // downloading
                long sofar = TasksManager.getImpl().getSoFar(id);
                long total = TasksManager.getImpl().getTotal(id);
                final float percent = sofar / (float) total;
                int progress = (int) (percent * 100);
                helper.setText(R.id.task_status_tv, "progress 下载中" + progress);
            } else if (status == FileDownloadStatus.error){
                // not start
                helper.setText(R.id.task_status_tv, "error 错误");
            }else {
                helper.setText(R.id.task_status_tv, "not start 未开始");
            }

        } else {

        }

        helper.getView(R.id.task_action_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                helper.getView(R.id.task_action_btn).setClickable(false);
                DownloadManager.getImpl().startDownload(model.url, TasksManager.getImpl().createPath(model.name));
                TasksManager.getImpl().addTask(model.url, model.name);
            }
        });

        DownloadManager.DownloadStatusUpdater aActivityUpdater = new DownloadManager.DownloadStatusUpdater(){

            @Override
            public void blockComplete(BaseDownloadTask task) {

            }
            @Override
            public void update(BaseDownloadTask task) {
                if (task != null){
                    if (task.getId() == id){
                        final int status = task.getStatus();
                        switch (status){
                            case FileDownloadStatus.pending:
                                helper.setText(R.id.task_status_tv, "pending 等待中");
                                break;
                            case FileDownloadStatus.connected:
                                helper.setText(R.id.task_status_tv, "connect 正在连接");
                                break;
                            case FileDownloadStatus.started:
                                helper.setText(R.id.task_status_tv, "start 开始下载");
                                break;
                            case FileDownloadStatus.progress:
                                int sofar = task.getSmallFileSoFarBytes();
                                int total = task.getSmallFileTotalBytes();
                                final float percent = sofar / (float) total;
                                int progress = (int) (percent * 100);
                                helper.setText(R.id.task_status_tv, "progress 下载中" + progress + "%");
                                break;
                            case FileDownloadStatus.paused:
                                helper.setText(R.id.task_status_tv, "pause 暂停");
                                break;
                            case FileDownloadStatus.error:
                                helper.setText(R.id.task_status_tv, "error 错误");
                                break;
                            case FileDownloadStatus.completed:
                                helper.setText(R.id.task_status_tv, "completed 完成");
                                break;
                            default:
                                break;
                        }
                    }
                }
            }
        };

        DownloadManager.getImpl().addUpdater(id, aActivityUpdater);

    }

}
