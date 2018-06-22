package com.artion.androiddemos.db;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/**
 * Created by caijinsheng on 18/6/18.
 */

public abstract class BaseProvider extends ContentProvider {

    private Context context;

    private SQLiteDatabase sqLiteDatabase;

//    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
//
//    static {
//        uriMatcher.addURI(AUTHORITY, PATH_VCODE, V_CODE);
//    }

//    private UriMatcher uriMatcher = null;

    protected abstract String getTableName(Uri uri);

    protected abstract void addURIs();

    protected abstract SQLiteDatabase getSQLiteDatabase();

    @Override
    public boolean onCreate() {
        context = getContext();
        try {
            addURIs();
//            uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
//            String authority = PaProviderUtils.getAuthorityPath(context.getPackageName(), PaProviderUtils.AUTHORITY_NAME);
//            uriMatcher.addURI(authority, PaProviderUtils.PATH_VCODE, PaProviderUtils.V_CODE);

            sqLiteDatabase = getSQLiteDatabase();
            return true;
        } catch (Exception e) {
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
            return sqLiteDatabase.query(tableName, projection, selection, selectionArgs, null, null, sortOrder, null);
        } catch (Exception e) {
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
            throw new IllegalArgumentException("Unsupported URI:" + uri);
        }
        try {
            sqLiteDatabase.beginTransaction();
            long row = sqLiteDatabase.insert(tableName, null, values);
            sqLiteDatabase.setTransactionSuccessful();
            if(row > 0) {
                context.getContentResolver().notifyChange(uri, null);
            }
            return uri;
        } catch (Exception e) {

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
        try {
            sqLiteDatabase.beginTransaction();
            int count = sqLiteDatabase.delete(tableName, selection, selectionArgs);
            sqLiteDatabase.setTransactionSuccessful();
            if (count > 0) {
                context.getContentResolver().notifyChange(uri, null);
            }
            return count;
        } catch (Exception e) {

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
        try {
            sqLiteDatabase.beginTransaction();
            int row = sqLiteDatabase.update(tableName, values, selection, selectionArgs);
            sqLiteDatabase.setTransactionSuccessful();
            if (row > 0) {
                context.getContentResolver().notifyChange(uri, null);
            }
            return row;
        } catch (Exception e) {

        } finally {
            if(sqLiteDatabase != null) {
                sqLiteDatabase.endTransaction();
            }
        }
        return -1;
    }
}
