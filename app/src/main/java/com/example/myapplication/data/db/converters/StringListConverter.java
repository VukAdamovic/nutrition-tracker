package com.example.myapplication.data.db.converters;

import androidx.room.TypeConverter;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class StringListConverter {

    private final JsonAdapter<List<String>> jsonAdapter;

    public StringListConverter() {
        Type type = Types.newParameterizedType(List.class, String.class);
        Moshi moshi = new Moshi.Builder().build();
        jsonAdapter = moshi.adapter(type);
    }

    @TypeConverter
    public String fromList(List<String> list) {
        if (list == null) {
            return null;
        }
        return jsonAdapter.toJson(list);
    }

    @TypeConverter
    public List<String> toList(String json) {
        if (json == null) {
            return null;
        }
        try {
            return jsonAdapter.fromJson(json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
