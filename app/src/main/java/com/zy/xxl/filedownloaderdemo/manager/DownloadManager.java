package com.zy.xxl.filedownloaderdemo.manager;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;

import java.util.ArrayList;
import java.util.List;

/**
 * Author ： zhangyang
 * Date   ： 2017/11/1
 * Email  :  18610942105@163.com
 * Description  :
 */

public class DownloadManager {

    private final static class HolderClass{
        private final static DownloadManager INSTANCE = new DownloadManager();
    }

    public static DownloadManager getImpl(){
        return HolderClass.INSTANCE;
    }

    private ArrayList<DownloadStatusUpdater> updaterList = new ArrayList<>();
    public ArrayList<Integer> idList = new ArrayList<>();

    public void startDownload(final String url, final String path){
        FileDownloader.getImpl().create(url)
                .setPath(path)
                .setListener(lis)
                .start();
    }

    public void addUpdater(final DownloadStatusUpdater updater, int id) {
        if (!updaterList.contains(updater) ) {
            int size = updaterList.size();
            int size01 = idList.size();
//            idList.add(id);
            updaterList.add(updater);
        }
    }

    public boolean removeUpdater(final DownloadStatusUpdater updater) {
        return updaterList.remove(updater);
    }

    public void clearUpdater(){
        updaterList.clear();
        idList.clear();
    }


    private FileDownloadListener lis = new FileDownloadListener() {
        @Override
        protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
            update(task);
        }

        @Override
        protected void started(BaseDownloadTask task) {
            super.started(task);
            update(task);
        }

        @Override
        protected void connected(BaseDownloadTask task, String etag, boolean isContinue, int soFarBytes, int totalBytes) {
            super.connected(task, etag, isContinue, soFarBytes, totalBytes);
            update(task);
        }

        @Override
        protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
            update(task);
        }

        @Override
        protected void blockComplete(BaseDownloadTask task) {
            blockComplete1(task);
        }

        @Override
        protected void completed(BaseDownloadTask task) {
            update(task);
        }

        @Override
        protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
            update(task);
        }

        @Override
        protected void error(BaseDownloadTask task, Throwable e) {
            update(task);
        }

        @Override
        protected void warn(BaseDownloadTask task) {
            update(task);
        }
    };

    private void blockComplete1(final BaseDownloadTask task){
        final List<DownloadStatusUpdater> updaterListCopy = (List<DownloadStatusUpdater>) updaterList.clone();
        for (DownloadStatusUpdater downloadStatusUpdater : updaterListCopy) {
            downloadStatusUpdater.blockComplete(task);
        }
    }

    private void update(final BaseDownloadTask task){
        final List<DownloadStatusUpdater> updaterListCopy = (List<DownloadStatusUpdater>) updaterList.clone();
        for (DownloadStatusUpdater downloadStatusUpdater : updaterListCopy) {
            downloadStatusUpdater.update(task);
        }
    }

    public interface DownloadStatusUpdater{
        void blockComplete(BaseDownloadTask task);
        void update(BaseDownloadTask task);
    }

}