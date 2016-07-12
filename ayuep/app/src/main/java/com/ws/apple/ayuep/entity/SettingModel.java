package com.ws.apple.ayuep.entity;

/**
 * Created by apple on 16/7/12.
 */

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "setting")
public class SettingModel {

    private static final long serialVersionUID = 1L;

    @DatabaseField(columnName = "key")
    private String key;

    @DatabaseField(columnName = "value")
    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
