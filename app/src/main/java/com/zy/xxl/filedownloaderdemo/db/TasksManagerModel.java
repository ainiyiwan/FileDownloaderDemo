package com.zy.xxl.filedownloaderdemo.db;

import android.content.ContentValues;

/**
 * Author ： zhangyang
 * Date   ： 2017/10/30
 * Email  :  18610942105@163.com
 * Description  :
 */

public class TasksManagerModel {
    public final static String ID = "id";
    public final static String NAME = "name";
    public final static String URL = "url";
    public final static String PATH = "path";

    private int id;
    private String name;
    private String url;
    private String path;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

//    public ContentValues toContentValues() {
//        ContentValues cv = new ContentValues();
//        cv.put(ID, id);
//        cv.put(NAME, name);
//        cv.put(URL, url);
//        cv.put(PATH, path);
//        return cv;
//    }
    public ContentValues toContentValues(){
        ContentValues cv = new ContentValues();
        cv.put(ID, id);
        cv.put(NAME, name);
        cv.put(URL, url);
        cv.put(PATH, path);
        return cv;
    }
}
