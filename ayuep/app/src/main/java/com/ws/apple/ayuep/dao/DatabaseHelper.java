package com.ws.apple.ayuep.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.ws.apple.ayuep.entity.SettingModel;
import com.ws.apple.ayuep.entity.StoreInfoDBModel;

import java.sql.SQLException;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "ayuep.db";
    private static DatabaseHelper mDataBaseHelper;

    private Dao<StoreInfoDBModel, Integer> mStoreInfoDBModelDao;
    private Dao<SettingModel, Integer> mSettingsModelDao;

    private DatabaseHelper(Context context) {
        super(context.getApplicationContext(), DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static DatabaseHelper getInstance(Context context) {
        if (mDataBaseHelper == null) {
            mDataBaseHelper = new DatabaseHelper(context);
        }

        return mDataBaseHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, StoreInfoDBModel.class);
            TableUtils.createTable(connectionSource, SettingModel.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {

    }

    @Override
    public void close() {
        super.close();
        mStoreInfoDBModelDao = null;
        mSettingsModelDao = null;
    }

    public Dao<StoreInfoDBModel, Integer> getLocationDao() throws SQLException {
        if (mStoreInfoDBModelDao == null) {
            mStoreInfoDBModelDao = getDao(StoreInfoDBModel.class);
        }

        return mStoreInfoDBModelDao;
    }

    public Dao<SettingModel, Integer> getmSettingsModelDao() throws SQLException {
        if (mSettingsModelDao == null) {
            mSettingsModelDao = getDao(SettingModel.class);
        }
        return mSettingsModelDao;
    }
}
