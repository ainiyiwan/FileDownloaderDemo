package com.zy.xxl.filedownloaderdemo.manager;

import android.text.TextUtils;
import android.util.SparseArray;
import android.widget.Toast;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadConnectListener;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloadSampleListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.model.FileDownloadStatus;
import com.liulishuo.filedownloader.util.FileDownloadUtils;
import com.zy.xxl.filedownloaderdemo.MyApplication;
import com.zy.xxl.filedownloaderdemo.activity.AddTaskActivity;
import com.zy.xxl.filedownloaderdemo.activity.TasksManagerDemoActivity;
import com.zy.xxl.filedownloaderdemo.adapter.TaskItemViewHolder;
import com.zy.xxl.filedownloaderdemo.constant.Constant;
import com.zy.xxl.filedownloaderdemo.db.TasksManagerDBController;
import com.zy.xxl.filedownloaderdemo.db.TasksManagerModel;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Author ： zhangyang
 * Date   ： 2017/10/30
 * Email  :  18610942105@163.com
 * Description  :
 */

public class TasksManager {
    public final static class HolderClass {
        private final static TasksManager INSTANCE
                = new TasksManager();
    }

    public static TasksManager getImpl() {
        return TasksManager.HolderClass.INSTANCE;
    }

    private TasksManagerDBController dbController;
    private List<TasksManagerModel> modelList;

    //第一步 构造数据源
    private TasksManager() {
        dbController = new TasksManagerDBController();//第三步 第四步
        modelList = dbController.getAllTasks();//第五步

        // TODO: 2017/11/1 delete 
//        initDemo();
    }

    //第二步 不能用这里的数据源所以注释掉这里的代码 或者后期删除
    // TODO: 2017/11/1 delete 
    private void initDemo() {
        if (modelList.size() <= 0) {
            final int demoSize = Constant.BIG_FILE_URLS.length;
            for (int i = 0; i < demoSize; i++) {
                final String url = Constant.BIG_FILE_URLS[i];
                addTask(url);
            }
        }
    }

    public void initData(){
        modelList = dbController.getAllTasks();//第五步
    }

    private SparseArray<BaseDownloadTask> taskSparseArray = new SparseArray<>();

    //实际上都是为了viewholder 我觉得这种操作没有多大用处 而且服务器也不会去存储 那么 就去掉所有的关于存储viewHolder的操作吧 窝草 已经没有做这种操作了
    // FIXME: 2017/11/1 delete
    public void addTaskForViewHolder(final BaseDownloadTask task) {
        taskSparseArray.put(task.getId(), task);
    }

    public void removeTaskForViewHolder(final int id) {
        taskSparseArray.remove(id);
    }

    /**
     * 第十步
     * 这里服务端记住也是没用的 所以这里也要删除
     * @param id
     * @param holder
     */
    // FIXME: 2017/11/1 delete
    public void updateViewHolder(final int id, final TaskItemViewHolder holder) {
        final BaseDownloadTask task = taskSparseArray.get(id);
        if (task == null) {
            return;
        }

        task.setTag(holder);
    }

    public void releaseTask() {
        taskSparseArray.clear();
    }

    private FileDownloadConnectListener listener;

    //第七步
    private void registerServiceConnectionListener1(final WeakReference<TasksManagerDemoActivity> activityWeakReference) {
        if (listener != null) {
            FileDownloader.getImpl().removeServiceConnectListener(listener);
        }

        listener = new FileDownloadConnectListener() {

            @Override
            public void connected() {
                if (activityWeakReference == null
                        || activityWeakReference.get() == null) {
                    return;
                }

                activityWeakReference.get().postNotifyDataChanged();
            }

            @Override
            public void disconnected() {
                if (activityWeakReference == null
                        || activityWeakReference.get() == null) {
                    return;
                }

                activityWeakReference.get().postNotifyDataChanged();
            }
        };

        FileDownloader.getImpl().addServiceConnectListener(listener);
    }

    private void registerServiceConnectionListener(final WeakReference<AddTaskActivity>
                                                           activityWeakReference) {
        if (listener != null) {
            FileDownloader.getImpl().removeServiceConnectListener(listener);
        }

        listener = new FileDownloadConnectListener() {

            @Override
            public void connected() {
                if (activityWeakReference == null
                        || activityWeakReference.get() == null) {
                    return;
                }

                activityWeakReference.get().postNotifyDataChanged();
            }

            @Override
            public void disconnected() {
                if (activityWeakReference == null
                        || activityWeakReference.get() == null) {
                    return;
                }

                activityWeakReference.get().postNotifyDataChanged();
            }
        };

        FileDownloader.getImpl().addServiceConnectListener(listener);
    }

    private void unregisterServiceConnectionListener() {
        FileDownloader.getImpl().removeServiceConnectListener(listener);
        listener = null;
    }

    //第六步
    public void onCreate1(final WeakReference<TasksManagerDemoActivity> activityWeakReference) {
        if (!FileDownloader.getImpl().isServiceConnected()) {
            FileDownloader.getImpl().bindService();
            registerServiceConnectionListener1(activityWeakReference);//第七步
        }
    }

    public void onCreate(final WeakReference<AddTaskActivity> activityWeakReference) {
        if (!FileDownloader.getImpl().isServiceConnected()) {
            FileDownloader.getImpl().bindService();
            registerServiceConnectionListener(activityWeakReference);
        }
    }

    public void onDestroy() {
        unregisterServiceConnectionListener();
        releaseTask();
    }

    public boolean isReady() {
        return FileDownloader.getImpl().isServiceConnected();
    }

    /**
     * 第九步
     * @param position
     * @return
     */
    public TasksManagerModel get(final int position) {
        return modelList.get(position);
    }

    /**
     * 这个实际上也是没用的
     * @param id
     * @return
     */
    public TasksManagerModel getById(final int id) {
        for (TasksManagerModel model : modelList) {
            if (model.getId() == id) {
                return model;
            }
        }

        return null;
    }

    /**
     * @param status Download Status
     * @return has already downloaded
     * @see FileDownloadStatus
     */
    public boolean isDownloaded(final int status) {
        return status == FileDownloadStatus.completed;
    }

    public int getStatus(final int id, String path) {
        return FileDownloader.getImpl().getStatus(id, path);
    }

    public long getTotal(final int id) {
        return FileDownloader.getImpl().getTotal(id);
    }

    public long getSoFar(final int id) {
        return FileDownloader.getImpl().getSoFar(id);
    }

    //第八步 等于是从服务器请求下来数据后 本地的操作 那么这里就很熟悉了
    public int getTaskCounts() {
        return modelList.size();
    }


    /**
     * 第X步  这里只是想说 添加任务的时候 这里是不是必须的
     * @param url
     * @return
     */
    public TasksManagerModel addTask(final String url) {
        return addTask(url, createPath(url));
    }

    /**
     * 第X步  这里只是想说 添加任务的时候 这里是不是必须的
     * @param url
     * @return
     */
    public TasksManagerModel addTask(final String url, final String path) {
        if (TextUtils.isEmpty(url) || TextUtils.isEmpty(path)) {
            return null;
        }

        /**
         * 这一块我看不到有任何用处  必须明白一点 现在上传服务的操作 只能有一个地方 那就是 addTask这个类 其他的类只是查询
         */
//        final int id = FileDownloadUtils.generateId(url, path);
//        TasksManagerModel model = getById(id);//这个应该是没用的
//        if (model != null) {
//            return model;
//        }
        final TasksManagerModel newModel = dbController.addTask(url, path);
        // FIXME: 2017/11/1 那么我认为 这里也是没用的 所以这个方法其实可以改为void
//        if (newModel != null) {
//            modelList.add(newModel);//这里的用处是？
//        }

        return newModel;
    }

    /**
     * 创造路径
     * @param url
     * @return
     */
    public String createPath(final String url) {
        if (TextUtils.isEmpty(url)) {
            return null;
        }

        return FileDownloadUtils.getDefaultSaveFilePath(url);
    }


    public FileDownloadListener getListener(){
         FileDownloadListener taskDownloadListener = new FileDownloadSampleListener() {


            @Override
            protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                super.pending(task, soFarBytes, totalBytes);

//                tag.updateDownloading(FileDownloadStatus.pending, soFarBytes
//                        , totalBytes);
//                tag.taskStatusTv.setText(R.string.tasks_manager_demo_status_pending);
                Toast.makeText(MyApplication.CONTEXT,"等待",Toast.LENGTH_LONG).show();
            }

            @Override
            protected void started(BaseDownloadTask task) {
                super.started(task);
                Toast.makeText(MyApplication.CONTEXT,"开始",Toast.LENGTH_LONG).show();
//                tag.taskStatusTv.setText(R.string.tasks_manager_demo_status_started);
            }

            @Override
            protected void connected(BaseDownloadTask task, String etag, boolean isContinue, int soFarBytes, int totalBytes) {
                super.connected(task, etag, isContinue, soFarBytes, totalBytes);

//                tag.updateDownloading(FileDownloadStatus.connected, soFarBytes
//                        , totalBytes);
//                tag.taskStatusTv.setText(R.string.tasks_manager_demo_status_connected);
            }

            @Override
            protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                super.progress(task, soFarBytes, totalBytes);
                final float percent = soFarBytes / (float) totalBytes;

                Toast.makeText(MyApplication.CONTEXT,"进行中" + (int) (percent * 100),Toast.LENGTH_LONG).show();
//                tag.updateDownloading(FileDownloadStatus.progress, soFarBytes
//                        , totalBytes);
            }

            @Override
            protected void error(BaseDownloadTask task, Throwable e) {
                super.error(task, e);

//                tag.updateNotDownloaded(FileDownloadStatus.error, task.getLargeFileSoFarBytes()
//                        , task.getLargeFileTotalBytes());
//                TasksManager.getImpl().removeTaskForViewHolder(task.getId());
            }

            @Override
            protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                super.paused(task, soFarBytes, totalBytes);
                Toast.makeText(MyApplication.CONTEXT,"暂停",Toast.LENGTH_LONG).show();
//                tag.updateNotDownloaded(FileDownloadStatus.paused, soFarBytes, totalBytes);
//                tag.taskStatusTv.setText(R.string.tasks_manager_demo_status_paused);
//                TasksManager.getImpl().removeTaskForViewHolder(task.getId());
            }

            @Override
            protected void completed(BaseDownloadTask task) {
                super.completed(task);
                Toast.makeText(MyApplication.CONTEXT,"完成",Toast.LENGTH_LONG).show();
//                tag.updateDownloaded();
//                TasksManager.getImpl().removeTaskForViewHolder(task.getId());
            }
        };

         return taskDownloadListener;
    }

}
