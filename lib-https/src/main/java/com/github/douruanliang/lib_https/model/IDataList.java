package com.github.douruanliang.lib_https.model;


import java.util.List;



public interface IDataList<T> extends BaseObject {

    public List<T> getList();

    public String getNext();
}
