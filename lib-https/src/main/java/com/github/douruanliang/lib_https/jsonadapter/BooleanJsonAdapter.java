package com.github.douruanliang.lib_https.jsonadapter;


import android.text.TextUtils;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * author: dourl
 * created on: 2018/10/24 5:57 PM
 * description:
 */
public class BooleanJsonAdapter extends TypeAdapter<Boolean> {

    @Override
    public void write(JsonWriter out, Boolean value) throws IOException {
        int result = ((value != null && value) ? 1 : 0);
        out.value(result);
    }

    @Override
    public Boolean read(JsonReader in) throws IOException {
        String value = in.nextString();
        return TextUtils.isEmpty(value) && Integer.parseInt(value) != 0;
    }
}
