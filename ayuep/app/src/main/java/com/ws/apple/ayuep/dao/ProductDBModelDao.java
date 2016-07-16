package com.ws.apple.ayuep.dao;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.ws.apple.ayuep.entity.ProductDBModel;

import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by apple on 16/7/14.
 */

public class ProductDBModelDao {
    private String TAG = ProductDBModelDao.class.getName();
    private Dao<ProductDBModel, Integer> mProductDbModelDao;

    public ProductDBModelDao(Context context) {
        try {
            mProductDbModelDao = DatabaseHelper.getInstance(context).getmProductDbModelDao();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insert(List<ProductDBModel> models) {
        for (ProductDBModel item : models) {
            insert(item);
        }
    }

    public void insert(ProductDBModel model) {
        try {
            mProductDbModelDao.create(model);
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public List<ProductDBModel> query() throws SQLException {
        List<ProductDBModel> result = mProductDbModelDao.queryForAll();
        return result;
    }

    public List<ProductDBModel> queryByProductType(String productType) throws SQLException {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("productType", productType);
        List<ProductDBModel> result = mProductDbModelDao.queryForFieldValuesArgs(map);
        return result;
    }

    public List<ProductDBModel> queryByStoreId(String storeId) throws SQLException {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("storeId", storeId);
        List<ProductDBModel> result = mProductDbModelDao.queryForFieldValuesArgs(map);
        return result;
    }

    public void delete(List<ProductDBModel> items) throws SQLException {
        mProductDbModelDao.delete(items);
    }

    public void deleteAllProducts() throws SQLException {
        List<ProductDBModel> result = mProductDbModelDao.queryForAll();
        mProductDbModelDao.delete(result);
    }
}
