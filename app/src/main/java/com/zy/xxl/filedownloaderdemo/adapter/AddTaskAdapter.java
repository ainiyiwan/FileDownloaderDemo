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

        final int id = FileDownloadUtils.generateId(model.url, TasksManager.getImpl().createPath(model.url));
        if (TasksManager.getImpl().isReady()) {
            final int status = TasksManager.getImpl().getStatus(id
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

        helper.getView(R.id.task_action_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                helper.getView(R.id.task_action_btn).setClickable(false);
                DownloadManager.getImpl().startDownload(model.url, TasksManager.getImpl().createPath(model.url));
            }
        });

//        if (!DownloadManager.getImpl().idList.contains(id)){
            DownloadManager.DownloadStatusUpdater aActivityUpdater = new DownloadManager.DownloadStatusUpdater(){

                @Override
                public void blockComplete(BaseDownloadTask task) {

                }
                @Override
                public void update(BaseDownloadTask task) {
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
                                helper.setText(R.id.task_status_tv, "start 开始");
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
            };

            DownloadManager.getImpl().addUpdater(aActivityUpdater, id);
//        }

    }

}


//helper.getView(R.id.task_action_btn).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                TasksManager manager = TasksManager.getImpl();
//                manager.addTask(model.url);//向数据库写入任务
//                final BaseDownloadTask task = FileDownloader.getImpl().create(model.url)
//                        .setPath(manager.createPath(model.url))
//                        .setCallbackProgressTimes(100)
//                        .setListener(TasksManager.getImpl().getListener());
//
////                new FileDownloadSampleListener(){
////                    @Override
////                    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
////                        super.pending(task, soFarBytes, totalBytes);
////                        helper.setText(R.id.task_status_tv, "pending 等待中");
////                    }
////
////                    @Override
////                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
////                        super.progress(task, soFarBytes, totalBytes);
////                        helper.setText(R.id.task_status_tv, "progress 下载中");
////                    }
////
////                    @Override
////                    protected void blockComplete(BaseDownloadTask task) {
////                        super.blockComplete(task);
////                    }
////
////                    @Override
////                    protected void completed(BaseDownloadTask task) {
////                        super.completed(task);
////                        helper.setText(R.id.task_status_tv, "completed 下载完成");
////                    }
////
////                    @Override
////                    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
////                        super.paused(task, soFarBytes, totalBytes);
////                    }
////
////                    @Override
////                    protected void error(BaseDownloadTask task, Throwable e) {
////                        super.error(task, e);
////                        helper.setText(R.id.task_status_tv, "error 下载错误");
////                    }
////
////                    @Override
////                    protected void warn(BaseDownloadTask task) {
////                        super.warn(task);
////                        helper.setText(R.id.task_status_tv, "warn 下载错误");
////                    }
////                };
//                // TODO: 2017/11/1 感觉没用注释掉
////                TasksManager.getImpl().addTaskForViewHolder(task);
//                task.start();
//            }
//        });

