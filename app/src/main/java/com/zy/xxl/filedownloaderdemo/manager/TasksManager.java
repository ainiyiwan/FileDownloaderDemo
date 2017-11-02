package com.zy.xxl.filedownloaderdemo.manager;

import android.text.TextUtils;
import android.util.SparseArray;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadConnectListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.model.FileDownloadStatus;
import com.liulishuo.filedownloader.util.FileDownloadUtils;
import com.zy.xxl.filedownloaderdemo.activity.AddTaskActivity;
import com.zy.xxl.filedownloaderdemo.activity.TasksManagerDemoActivity;
import com.zy.xxl.filedownloaderdemo.db.TasksManagerDBController;
import com.zy.xxl.filedownloaderdemo.db.TasksManagerModel;

import java.io.File;
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

    }


    public List<TasksManagerModel> initData(){
        modelList = dbController.getAllTasks();//第五步
        return modelList;
    }

    private SparseArray<BaseDownloadTask> taskSparseArray = new SparseArray<>();


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
    public TasksManagerModel addTask(final String url, final String name) {
        return addTask(url, createPath(name), name);
    }

    /**
     * 第X步  这里只是想说 添加任务的时候 这里是不是必须的
     * @param url
     * @return
     */
    public TasksManagerModel addTask(final String url, final String path, final String name) {
        if (TextUtils.isEmpty(url) || TextUtils.isEmpty(path)) {
            return null;
        }
        final TasksManagerModel newModel = dbController.addTask(url, path, name);
        return newModel;
    }

    public boolean delTask(final String path){
        if (TextUtils.isEmpty(path)){
            return false;
        }
        return dbController.delTask(path);
    }

    public boolean delAll(){
        return dbController.delAll();
    }

    /**
     * 创造路径
     * @param name
     * @return
     */
    public String createPath(final String name) {
        if (TextUtils.isEmpty(name)) {
            return null;
        }

//        return FileDownloadUtils.getDefaultSaveFilePath(url);
        return FileDownloadUtils.getDefaultSaveRootPath() + File.separator + "MRXZMedia" + File.separator+ name + ".apk";
    }

}
