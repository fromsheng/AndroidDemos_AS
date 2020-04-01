package com.artion.androiddemos.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by caijinsheng on 2020/3/31.
 */

public abstract class BaseDao<T> {
	protected String TAG = getClass().getSimpleName();
	protected Context mContext = null;
	
	public BaseDao(Context context) {
	    mContext = context.getApplicationContext();
	}

    protected abstract Uri getContentUri();

	public abstract ContentValues getContentValues(T t);

	public abstract T fromCursor(Cursor cursor);

	public Cursor query(String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
	    try {
            return ProviderUtils.query(mContext, getContentUri(), projection, selection, selectionArgs, sortOrder);
        } catch (Exception e) {
//            Logger.e(TAG, "query " + e.getMessage());
        }
        return null;
    }

    public List<T> queryAll() {
        try {
            Cursor cursor = ProviderUtils.query(mContext, getContentUri(), null, null, null, null);
            if(cursor != null) {
                List<T> resultList = new ArrayList<>();
                T item = null;
                cursor.moveToFirst();
                do {
                    item = fromCursor(cursor);
                    if(item != null) {
                        resultList.add(item);
                    }
                } while (cursor.moveToNext());

                cursor.close();

                return resultList;
            }
        } catch (Exception e) {
//            Logger.e(TAG, "queryAll " + e.getMessage());
        }
        return null;
    }

    public void insert(T values) {
        insert(getContentValues(values));
    }

    public void insert(ContentValues values) {
        try {
            ProviderUtils.insert(mContext, getContentUri(), values);
        } catch (Exception e) {
//            Logger.e(TAG, "insert " + e.getMessage());
        }
    }

    private ContentValues[] getContentValueList(List<T> list) {
	    if(list == null || list.isEmpty()) {
	        return null;
        }
        List<ContentValues> contentValues = new ArrayList<ContentValues>();
        for (T t : list) {
            if(t != null){
                ContentValues values = getContentValues(t);
                contentValues.add(values);
            }
        }
        ContentValues[] valueArray = new ContentValues[contentValues.size()];
        return valueArray;
    }

    public int bulkInsert(List<T> list) {
        try {
            return ProviderUtils.bulkInsert(mContext, getContentUri(), getContentValueList(list));
        } catch (Exception e) {
//            Logger.e(TAG, "bulkInsert " + e.getMessage());
        }
        return -1;
    }

    public int delete(String selection, String[] selectionArgs) {
        try {
            return ProviderUtils.delete(mContext, getContentUri(), selection, selectionArgs);
        } catch (Exception e) {
//            Logger.e(TAG, "delete " + e.getMessage());
        }
        return -1;
    }

    public int deleteAll() {
        try {
            return ProviderUtils.delete(mContext, getContentUri(), null, null);
        } catch (Exception e) {
//            Logger.e(TAG, "deleteAll " + e.getMessage());
        }
        return -1;
    }

    public int update(ContentValues values, String selection, String[] selectionArgs) {
        try {
            return ProviderUtils.update(mContext, getContentUri(), values, selection, selectionArgs);
        } catch (Exception e) {
//            Logger.e(TAG, "update " + e.getMessage());
        }
        return -1;
    }

    // 加解密处理
    protected String getDBColumDecryptValue(Cursor resultSet, String columKey) {
//        return Tools.decryptAESNew(resultSet.getString(resultSet.getColumnIndex(columKey)));
        return resultSet.getString(resultSet.getColumnIndex(columKey));
    }

    protected String getEncryptData(String data) {
//        return Tools.encryptAESNew(data);
        return data;
    }

}
