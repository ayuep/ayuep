package com.ws.apple.ayuep.dao;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.ws.apple.ayuep.entity.SettingModel;
import com.ws.apple.ayuep.entity.StoreInfoDBModel;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by apple on 16/7/12.
 */

public class SettingModelDao {

    private String TAG = SettingModelDao.class.getName();
    private Dao<SettingModel, Integer> mSettingsDao;

    public SettingModelDao(Context context) {
        try {
            mSettingsDao = DatabaseHelper.getInstance(context).getmSettingsModelDao();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insert(List<SettingModel> models) {
        for (SettingModel item : models) {
            insert(item);
        }
    }

    public void insert(SettingModel model) {
        try {
            mSettingsDao.create(model);
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public SettingModel query(String key) throws SQLException {
        QueryBuilder<SettingModel, ?> builder = mSettingsDao.queryBuilder();
        builder.where().eq("key", key);
        PreparedQuery<SettingModel> preparedQuery = builder.prepare();
        SettingModel settingModel = mSettingsDao.queryForFirst(preparedQuery);

        return settingModel;
    }
}
