package com.zy.xxl.filedownloaderdemo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.model.FileDownloadStatus;
import com.liulishuo.filedownloader.util.FileDownloadUtils;
import com.zy.xxl.filedownloaderdemo.R;
import com.zy.xxl.filedownloaderdemo.db.TasksManagerModel;
import com.zy.xxl.filedownloaderdemo.manager.DownloadManager;
import com.zy.xxl.filedownloaderdemo.manager.TasksManager;

import java.io.File;

/**
 * Author ： zhangyang
 * Date   ： 2017/10/30
 * Email  :  18610942105@163.com
 * Description  :
 */

public class TaskItemAdapter extends RecyclerView.Adapter<TaskItemViewHolder> {


    @Override
    public TaskItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TaskItemViewHolder holder = new TaskItemViewHolder(
                LayoutInflater.from(
                        parent.getContext())
                        .inflate(R.layout.item_tasks_manager, parent, false));

        return holder;
    }

    @Override
    public void onBindViewHolder(final TaskItemViewHolder holder, final int position) {
        final TasksManagerModel model = TasksManager.getImpl().get(position);//第九步 实际上就是我们熟悉的操作了 窝草 已经没有这种操作了

        holder.update(model.getId(), position);//干嘛用的
        holder.taskActionBtn.setTag(holder);//干嘛用的
        holder.taskNameTv.setText(model.getName());

        // FIXME: 2017/11/1 delete 服务端有记不住 搞个毛啊
//        TasksManager.getImpl().updateViewHolder(holder.id, holder);//第十步

        holder.taskActionBtn.setEnabled(true);

        holder.taskActionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence action = ((TextView) v).getText();
                if (action.equals(v.getResources().getString(R.string.delete))) {
                    // to delete
                    boolean delSuc = TasksManager.getImpl().delTask(model.getPath());
                    if (delSuc){
                        FileDownloader.getImpl().clear(model.getId(),TasksManager.getImpl().get(holder.position).getPath());

                        TasksManager.getImpl().initData();
                        notifyItemRemoved(holder.getAdapterPosition());
                    }
                }
            }
        });
        final int id = FileDownloadUtils.generateId(model.getUrl(), TasksManager.getImpl().createPath(model.getName()));

        /**
         * 下面的状态要根据上面搞 所以这里先看上面
         */
        if (TasksManager.getImpl().isReady()) {
            final int status = TasksManager.getImpl().getStatus(model.getId(), model.getPath());
            if (status == FileDownloadStatus.pending || status == FileDownloadStatus.started ||
                    status == FileDownloadStatus.connected) {
                // start task, but file not created yet
                holder.updateDownloading(status, TasksManager.getImpl().getSoFar(model.getId())
                        , TasksManager.getImpl().getTotal(model.getId()));
            } else if (!new File(model.getPath()).exists() &&
                    !new File(FileDownloadUtils.getTempPath(model.getPath())).exists()) {
                // not exist file
                holder.updateNotDownloaded(status, 0, 0);
            } else if (TasksManager.getImpl().isDownloaded(status)) {
                // already downloaded and exist
                holder.updateDownloaded();
            } else if (status == FileDownloadStatus.progress) {
                // downloading
                holder.updateDownloading(status, TasksManager.getImpl().getSoFar(model.getId())
                        , TasksManager.getImpl().getTotal(model.getId()));
            } else {
                // not start
                holder.updateNotDownloaded(status, TasksManager.getImpl().getSoFar(model.getId())
                        , TasksManager.getImpl().getTotal(model.getId()));
            }
        } else {
            holder.taskStatusTv.setText(R.string.tasks_manager_demo_status_loading);
            holder.taskActionBtn.setEnabled(false);
        }

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
                                holder.updateDownloading(status, TasksManager.getImpl().getSoFar(model.getId())
                                        , TasksManager.getImpl().getTotal(model.getId()));
                                break;
                            case FileDownloadStatus.connected:
                                holder.updateDownloading(status, TasksManager.getImpl().getSoFar(model.getId())
                                        , TasksManager.getImpl().getTotal(model.getId()));
                                break;
                            case FileDownloadStatus.started:
                                holder.updateDownloading(status, TasksManager.getImpl().getSoFar(model.getId())
                                        , TasksManager.getImpl().getTotal(model.getId()));
                                break;
                            case FileDownloadStatus.progress:
                                holder.updateDownloading(status, TasksManager.getImpl().getSoFar(model.getId())
                                        , TasksManager.getImpl().getTotal(model.getId()));
                                break;
                            case FileDownloadStatus.paused:
                                holder.updateNotDownloaded(status, TasksManager.getImpl().getSoFar(model.getId())
                                        , TasksManager.getImpl().getTotal(model.getId()));
                                break;
                            case FileDownloadStatus.error:
                                holder.updateNotDownloaded(status, TasksManager.getImpl().getSoFar(model.getId())
                                        , TasksManager.getImpl().getTotal(model.getId()));
                                break;
                            case FileDownloadStatus.completed:
                                holder.updateDownloaded();
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

    //第八步
    @Override
    public int getItemCount() {
        return TasksManager.getImpl().getTaskCounts();
    }
}
