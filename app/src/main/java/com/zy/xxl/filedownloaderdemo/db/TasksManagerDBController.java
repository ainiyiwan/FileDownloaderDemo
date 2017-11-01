package com.zy.xxl.filedownloaderdemo.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.liulishuo.filedownloader.util.FileDownloadUtils;
import com.zy.xxl.filedownloaderdemo.MyApplication;
import com.zy.xxl.filedownloaderdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Author ： zhangyang
 * Date   ： 2017/10/30
 * Email  :  18610942105@163.com
 * Description  :
 */

public class TasksManagerDBController {
    public final static String TABLE_NAME = "tasksmanger";
    private final SQLiteDatabase db;

    //第三步 到了这个构造方法
    public TasksManagerDBController() {
        TasksManagerDBOpenHelper openHelper = new TasksManagerDBOpenHelper(MyApplication.CONTEXT);

        db = openHelper.getWritableDatabase();
    }

    /**
     * 第五步 获取任务 每次进来都要获取任务  任务只能从这里获取 TasksManager已经不管作用了
     * 实际上 这个页面完全是本地的 所以 这里可以看做是服务器 改变思维吧 骚年
     * 把原来要向服务器进行的操作 都放到这里 这么来说TasksManager倒像是一个本地 数据放在那里自然是不靠谱的
     * @return
     */
    public List<TasksManagerModel> getAllTasks() {
        final Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        final List<TasksManagerModel> list = new ArrayList<>();
        try {
            if (!c.moveToLast()) {
                return list;
            }

            do {
                TasksManagerModel model = new TasksManagerModel();
                model.setId(c.getInt(c.getColumnIndex(TasksManagerModel.ID)));
                model.setName(c.getString(c.getColumnIndex(TasksManagerModel.NAME)));
                model.setUrl(c.getString(c.getColumnIndex(TasksManagerModel.URL)));
                model.setPath(c.getString(c.getColumnIndex(TasksManagerModel.PATH)));
                list.add(model);
            } while (c.moveToPrevious());
        } finally {
            if (c != null) {
                c.close();
            }
        }

        return list;
    }

    //这里只有addTask一个类可以调 等于是上传服务器
    public TasksManagerModel addTask(final String url, final String path) {
        if (TextUtils.isEmpty(url) || TextUtils.isEmpty(path)) {
            return null;
        }

        // have to use FileDownloadUtils.generateId to associate TasksManagerModel with FileDownloader
        final int id = FileDownloadUtils.generateId(url, path);

        TasksManagerModel model = new TasksManagerModel();
        model.setId(id);
        model.setName(MyApplication.CONTEXT.getString(R.string.tasks_manager_demo_name, id));
        model.setUrl(url);
        model.setPath(path);

        final boolean succeed = db.insert(TABLE_NAME, null, model.toContentValues()) != -1;
        return succeed ? model : null;
    }




}