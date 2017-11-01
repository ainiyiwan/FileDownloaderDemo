package com.zy.xxl.filedownloaderdemo.model;

/**
 * Author ： zhangyang
 * Date   ： 2017/10/30
 * Email  :  18610942105@163.com
 * Description  :
 */

public class AddTaskModel {
    public String name;
    public String url;
    public String picUrl;

    public AddTaskModel(String name, String url, String picUrl) {
        this.name = name;
        this.url = url;
        this.picUrl = picUrl;
    }
}
