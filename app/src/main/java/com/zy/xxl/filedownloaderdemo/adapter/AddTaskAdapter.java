package com.zy.xxl.filedownloaderdemo.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zy.xxl.filedownloaderdemo.db.TasksManagerModel;

import java.util.List;

/**
 * Author ： zhangyang
 * Date   ： 2017/10/31
 * Email  :  18610942105@163.com
 * Description  :
 */

public class AddTaskAdapter extends BaseQuickAdapter<TasksManagerModel, BaseViewHolder> {

    public AddTaskAdapter(int layoutResId, @Nullable List<TasksManagerModel> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TasksManagerModel item) {

    }
}
