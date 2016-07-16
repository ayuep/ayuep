package com.ws.apple.ayuep.dao;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.ws.apple.ayuep.entity.StoreInfoDBModel;

import java.sql.SQLException;
import java.util.List;

public class StoreInfoDBModelDao {

    private String TAG = StoreInfoDBModelDao.class.getName();
    private Dao<StoreInfoDBModel, Integer> mStoreInfoDao;

    public StoreInfoDBModelDao(Context context) {
        try {
            mStoreInfoDao = DatabaseHelper.getInstance(context).getLocationDao();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insert(List<StoreInfoDBModel> models) {
        for (StoreInfoDBModel item : models) {
            insert(item);
        }
    }

    public void insert(StoreInfoDBModel model) {
        try {
            mStoreInfoDao.create(model);
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public List<StoreInfoDBModel> query() throws SQLException {
        List<StoreInfoDBModel> result = mStoreInfoDao.queryForAll();
        return result;
    }

    public StoreInfoDBModel queryByStoreId(String storeId) throws SQLException {
        QueryBuilder<StoreInfoDBModel, ?> builder = mStoreInfoDao.queryBuilder();
        builder.where().eq("storeId", storeId);
        PreparedQuery<StoreInfoDBModel> preparedQuery = builder.prepare();
        StoreInfoDBModel result = mStoreInfoDao.queryForFirst(preparedQuery);
        return result;
    }
    public void delete(List<StoreInfoDBModel> items) throws SQLException {
        mStoreInfoDao.delete(items);
    }

    public void deleteAllStores() throws SQLException {
        List<StoreInfoDBModel> result = mStoreInfoDao.queryForAll();
        mStoreInfoDao.delete(result);
    }
}
