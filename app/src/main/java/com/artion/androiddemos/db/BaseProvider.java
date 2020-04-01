package com.artion.androiddemos.db;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/**
 * Created by caijinsheng on 18/6/18.
 */

public abstract class BaseProvider extends ContentProvider {

    protected final String TAG = getClass().getSimpleName();
    private Context context;

    protected String AUTHORITY = "";

    protected UriMatcher mUriMatcher = null;

    protected abstract String getTableName(Uri uri);

    protected abstract void addURIs();

    protected abstract SQLiteDatabase getReadableDatabase();
    protected abstract SQLiteDatabase getWritableDatabase();

    protected abstract String getAuthority(Context context);

    protected void addURI(String path, int code) {
        if(mUriMatcher != null && AUTHORITY != null && path != null) {
            mUriMatcher.addURI(AUTHORITY, path, code);
        }
    }

    @Override
    public boolean onCreate() {
        context = getContext();
        try {
            AUTHORITY = getAuthority(context);
            mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
            addURIs();

            return true;
        } catch (Exception e) {
//            Logger.e(TAG, "onCreate " + e.getMessage());
        }
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        try {
            String tableName = getTableName(uri);
            if (tableName == null) {
                throw new IllegalArgumentException("Unsupported URI:" + uri);
            }
            return getReadableDatabase().query(tableName, projection, selection, selectionArgs, null, null, sortOrder, null);
        } catch (Exception e) {
//            Logger.e(TAG, "query " + e.getMessage());
        }
        return null;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        String tableName = getTableName(uri);
        if (tableName == null) {
            return null;
        }
        SQLiteDatabase sqLiteDatabase = null;
        try {
            sqLiteDatabase = getWritableDatabase();
            sqLiteDatabase.beginTransaction();
            long row = sqLiteDatabase.insert(tableName, null, values);
            sqLiteDatabase.setTransactionSuccessful();
            if(row > 0) {
                context.getContentResolver().notifyChange(uri, null);
            }
            return uri;
        } catch (Exception e) {
//            Logger.e(TAG, "insert " + e.getMessage());
        } finally {
            if(sqLiteDatabase != null) {
                sqLiteDatabase.endTransaction();
            }
        }
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        String tableName = getTableName(uri);
        if (tableName == null) {
            throw new IllegalArgumentException("Unsupported URI:" + uri);
        }
        SQLiteDatabase sqLiteDatabase = null;
        try {
            sqLiteDatabase = getWritableDatabase();
            sqLiteDatabase.beginTransaction();
            int count = sqLiteDatabase.delete(tableName, selection, selectionArgs);
            sqLiteDatabase.setTransactionSuccessful();
            if (count > 0) {
                context.getContentResolver().notifyChange(uri, null);
            }
            return count;
        } catch (Exception e) {
//            Logger.e(TAG, "delete " + e.getMessage());
        } finally {
            if(sqLiteDatabase != null) {
                sqLiteDatabase.endTransaction();
            }
        }
        return -1;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        String tableName = getTableName(uri);
        if (tableName == null) {
            throw new IllegalArgumentException("Unsupported URI:" + uri);
        }
        SQLiteDatabase sqLiteDatabase = null;
        try {
            sqLiteDatabase = getWritableDatabase();
            sqLiteDatabase.beginTransaction();
            int row = sqLiteDatabase.update(tableName, values, selection, selectionArgs);
            sqLiteDatabase.setTransactionSuccessful();
            if (row > 0) {
                context.getContentResolver().notifyChange(uri, null);
            }
            return row;
        } catch (Exception e) {
//            Logger.e(TAG, "update " + e.getMessage());
        } finally {
            if(sqLiteDatabase != null) {
                sqLiteDatabase.endTransaction();
            }
        }
        return -1;
    }
}
