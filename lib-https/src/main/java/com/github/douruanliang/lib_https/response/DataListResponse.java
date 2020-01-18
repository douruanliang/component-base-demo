package com.github.douruanliang.lib_https.response;

import android.text.TextUtils;

import com.github.douruanliang.lib_https.model.IDataList;
import com.github.douruanliang.lib_https.response.BaseResponse;

import java.util.List;

public class DataListResponse<T> extends BaseResponse implements IDataList<T> {
    public List<T> data;
    public String next;

    @Override
    public List<T> getList() {
        return data;
    }

    public boolean hasMore() {
        return !"0".equals(next) || !TextUtils.isEmpty(next);
    }

    @Override
    public String getNext() {
        return next;
    }

}
