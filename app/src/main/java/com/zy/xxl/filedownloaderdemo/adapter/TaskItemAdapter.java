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
import com.zy.xxl.filedownloaderdemo.manager.TasksManager;

import java.io.File;

/**
 * Author ： zhangyang
 * Date   ： 2017/10/30
 * Email  :  18610942105@163.com
 * Description  :
 */

public class TaskItemAdapter extends RecyclerView.Adapter<TaskItemViewHolder> {

    /**
     * 这个listener 应该用于全部开始
     */
//    private FileDownloadListener taskDownloadListener = new FileDownloadSampleListener() {
//
//        /**
//         * 第十一步 还检查个毛啊 已经没有存储了 不用又当爹又当娘了
//         * @param task
//         * @param soFarBytes
//         * @param totalBytes
//         */
////        private TaskItemViewHolder checkCurrentHolder(final BaseDownloadTask task) {
////            final TaskItemViewHolder tag = (TaskItemViewHolder) task.getTag();
////            if (tag.id != task.getId()) {
////                return null;
////            }
////
////            return tag;
////        }
//
//        @Override
//        protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
//            super.pending(task, soFarBytes, totalBytes);
//            final TaskItemViewHolder tag = checkCurrentHolder(task);
//            if (tag == null) {
//                return;
//            }
//
//            tag.updateDownloading(FileDownloadStatus.pending, soFarBytes
//                    , totalBytes);
//            tag.taskStatusTv.setText(R.string.tasks_manager_demo_status_pending);
//        }
//
//        @Override
//        protected void started(BaseDownloadTask task) {
//            super.started(task);
//            final TaskItemViewHolder tag = checkCurrentHolder(task);
//            if (tag == null) {
//                return;
//            }
//
//            tag.taskStatusTv.setText(R.string.tasks_manager_demo_status_started);
//        }
//
//        @Override
//        protected void connected(BaseDownloadTask task, String etag, boolean isContinue, int soFarBytes, int
//                totalBytes) {
//            super.connected(task, etag, isContinue, soFarBytes, totalBytes);
//            final TaskItemViewHolder tag = checkCurrentHolder(task);
//            if (tag == null) {
//                return;
//            }
//
//            tag.updateDownloading(FileDownloadStatus.connected, soFarBytes
//                    , totalBytes);
//            tag.taskStatusTv.setText(R.string.tasks_manager_demo_status_connected);
//        }
//
//        @Override
//        protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
//            super.progress(task, soFarBytes, totalBytes);
//            final TaskItemViewHolder tag = checkCurrentHolder(task);
//            if (tag == null) {
//                return;
//            }
//
//            tag.updateDownloading(FileDownloadStatus.progress, soFarBytes
//                    , totalBytes);
//        }
//
//        @Override
//        protected void error(BaseDownloadTask task, Throwable e) {
//            super.error(task, e);
//            final TaskItemViewHolder tag = checkCurrentHolder(task);
//            if (tag == null) {
//                return;
//            }
//
//            tag.updateNotDownloaded(FileDownloadStatus.error, task.getLargeFileSoFarBytes()
//                    , task.getLargeFileTotalBytes());
//            TasksManager.getImpl().removeTaskForViewHolder(task.getId());
//        }
//
//        @Override
//        protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
//            super.paused(task, soFarBytes, totalBytes);
//            final TaskItemViewHolder tag = checkCurrentHolder(task);
//            if (tag == null) {
//                return;
//            }
//
//            tag.updateNotDownloaded(FileDownloadStatus.paused, soFarBytes, totalBytes);
//            tag.taskStatusTv.setText(R.string.tasks_manager_demo_status_paused);
//            TasksManager.getImpl().removeTaskForViewHolder(task.getId());
//        }
//
//        @Override
//        protected void completed(BaseDownloadTask task) {
//            super.completed(task);
//            final TaskItemViewHolder tag = checkCurrentHolder(task);
//            if (tag == null) {
//                return;
//            }
//
//            tag.updateDownloaded();
//            TasksManager.getImpl().removeTaskForViewHolder(task.getId());
//        }
//    };

    /**
     * 如此说来 也没有下面的这个 操作 不过这个
     *
     * 这个也是用去全部开始
     */
//    private View.OnClickListener taskActionOnClickListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
////            if (v.getTag() == null) {
////                return;
////            }
//
////            TaskItemViewHolder holder = (TaskItemViewHolder) v.getTag();
//
//            CharSequence action = ((TextView) v).getText();
//            if (action.equals(v.getResources().getString(R.string.pause))) {
//                // to pause
//                FileDownloader.getImpl().pause(holder.id);
//            } else if (action.equals(v.getResources().getString(R.string.start))) {
//                // to start
//                // to start
//                final TasksManagerModel model = TasksManager.getImpl().get(holder.position);
//                final BaseDownloadTask task = FileDownloader.getImpl().create(model.getUrl())
//                        .setPath(model.getPath())
//                        .setCallbackProgressTimes(100)
//                        .setListener(taskDownloadListener);
//
//                // TODO: 2017/11/1 感觉没用先注释掉
////                TasksManager.getImpl().addTaskForViewHolder(task);
////
////                TasksManager.getImpl().updateViewHolder(holder.id, holder);
//
//                task.start();
//            } else if (action.equals(v.getResources().getString(R.string.delete))) {
//                // to delete
//                new File(TasksManager.getImpl().get(holder.position).getPath()).delete();
//                holder.taskActionBtn.setEnabled(true);
//                holder.updateNotDownloaded(FileDownloadStatus.INVALID_STATUS, 0, 0);
//            }
//        }
//    };

    @Override
    public TaskItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TaskItemViewHolder holder = new TaskItemViewHolder(
                LayoutInflater.from(
                        parent.getContext())
                        .inflate(R.layout.item_tasks_manager, parent, false));

//        holder.taskActionBtn.setOnClickListener(taskActionOnClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(final TaskItemViewHolder holder, int position) {
        final TasksManagerModel model = TasksManager.getImpl().get(position);//第九步 实际上就是我们熟悉的操作了 窝草 已经没有这种操作了

        holder.update(model.getId(), position);//干嘛用的
        holder.taskActionBtn.setTag(holder);//干嘛用的
        holder.taskNameTv.setText(model.getName());

        // FIXME: 2017/11/1 delete 服务端有记不住 搞个毛啊
//        TasksManager.getImpl().updateViewHolder(holder.id, holder);//第十步

        holder.taskActionBtn.setEnabled(true);

        final BaseDownloadTask task = FileDownloader.getImpl().create(model.getUrl())
                .setPath(model.getPath())
                .setCallbackProgressTimes(100)
                .setListener(TasksManager.getImpl().getListener());
        // TODO: 2017/11/1 感觉没用注释掉
//                TasksManager.getImpl().addTaskForViewHolder(task);
        task.start();


        holder.taskActionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence action = ((TextView) v).getText();
                if (action.equals(v.getResources().getString(R.string.pause))) {
                    // to pause
                    FileDownloader.getImpl().pause(model.getId());
                } else if (action.equals(v.getResources().getString(R.string.start))) {
                    // to start
                    // to start
//                    final TasksManagerModel model = TasksManager.getImpl().get(holder.position);
//                    final BaseDownloadTask task = FileDownloader.getImpl().create(model.getUrl())
//                            .setPath(model.getPath())
//                            .setCallbackProgressTimes(100)
//                            .setListener(new FileDownloadSampleListener(){
//
//                                @Override
//                                protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
//                                    super.pending(task, soFarBytes, totalBytes);
//                                    holder.updateDownloading(FileDownloadStatus.pending, soFarBytes
//                                            , totalBytes);
//                                    holder.taskStatusTv.setText(R.string.tasks_manager_demo_status_pending);
//                                }
//
//                                @Override
//                                protected void started(BaseDownloadTask task) {
//                                    super.started(task);
//                                    holder.taskStatusTv.setText(R.string.tasks_manager_demo_status_started);
//                                }
//
//                                @Override
//                                protected void connected(BaseDownloadTask task, String etag, boolean isContinue, int soFarBytes, int totalBytes) {
//                                    super.connected(task, etag, isContinue, soFarBytes, totalBytes);
//                                    holder.updateDownloading(FileDownloadStatus.connected, soFarBytes
//                                            , totalBytes);
//                                    holder.taskStatusTv.setText(R.string.tasks_manager_demo_status_connected);
//                                }
//
//                                @Override
//                                protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
//                                    super.progress(task, soFarBytes, totalBytes);
//                                    holder.updateDownloading(FileDownloadStatus.progress, soFarBytes
//                                            , totalBytes);
//                                }
//
//                                @Override
//                                protected void completed(BaseDownloadTask task) {
//                                    super.completed(task);
//                                    holder.updateDownloaded();
//                                    TasksManager.getImpl().removeTaskForViewHolder(task.getId());
//                                }
//
//                                @Override
//                                protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
//                                    super.paused(task, soFarBytes, totalBytes);
//                                    holder.updateNotDownloaded(FileDownloadStatus.paused, soFarBytes, totalBytes);
//                                    holder.taskStatusTv.setText(R.string.tasks_manager_demo_status_paused);
//                                    TasksManager.getImpl().removeTaskForViewHolder(task.getId());
//                                }
//
//                                @Override
//                                protected void error(BaseDownloadTask task, Throwable e) {
//                                    super.error(task, e);
//                                    holder.updateNotDownloaded(FileDownloadStatus.error, task.getLargeFileSoFarBytes()
//                                            , task.getLargeFileTotalBytes());
//                                    TasksManager.getImpl().removeTaskForViewHolder(task.getId());
//                                }
//
//                            });
//
//                    // TODO: 2017/11/1 感觉没用先注释掉
////                TasksManager.getImpl().addTaskForViewHolder(task);
////
////                TasksManager.getImpl().updateViewHolder(holder.id, holder);
//
//                    task.start();
                } else if (action.equals(v.getResources().getString(R.string.delete))) {
                    // to delete
//                    new File(TasksManager.getImpl().get(position).getPath()).delete();
//                    holder.taskActionBtn.setEnabled(true);
//                    holder.updateNotDownloaded(FileDownloadStatus.INVALID_STATUS, 0, 0);
                }
            }
        });

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
    }

    //第八步
    @Override
    public int getItemCount() {
        return TasksManager.getImpl().getTaskCounts();
    }
}
