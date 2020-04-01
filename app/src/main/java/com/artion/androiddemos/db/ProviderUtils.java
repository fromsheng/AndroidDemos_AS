package com.artion.androiddemos.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

/**
 * Created by caijinsheng on 2020/3/31.
 */

public class ProviderUtils {
    public static void notifyChange(Context context, Uri uri) {
        if(context == null || uri == null) {
            return;
        }
        context.getContentResolver().notifyChange(uri, null);
    }

    public static Cursor query(Context context, Uri uri, String[] projection, String selection,
                               String[] selectionArgs, String sortOrder) {
        if(context == null || uri == null) {
            return null;
        }
        return context.getContentResolver().query(uri, projection, selection, selectionArgs,
                sortOrder);
    }

    public static Uri insert(Context context, Uri uri, ContentValues values) {
        if(context == null || uri == null) {
            return null;
        }
        return context.getContentResolver().insert(uri, values);
    }

    public static int bulkInsert(Context context, Uri uri, ContentValues[] values) {
        if(context == null || uri == null) {
            return -1;
        }
        return context.getContentResolver().bulkInsert(uri, values);
    }

    public static int update(Context context, Uri uri, ContentValues values, String where, String[] whereArgs) {
        if(context == null || uri == null) {
            return -1;
        }
        return context.getContentResolver().update(uri, values, where, whereArgs);
    }

    public static int delete(Context context, Uri uri, String selection, String[] selectionArgs) {
        if(context == null || uri == null) {
            return -1;
        }
        return context.getContentResolver().delete(uri, selection, selectionArgs);
    }

    public static Cursor getList(Context context, Uri uri, String[] projection, String selection, String[] selectionArgs,
                                 String sortOrder) {
        if(context == null || uri == null) {
            return null;
        }
        return context.getContentResolver().query(uri, projection, selection,
                selectionArgs, sortOrder);
    }
}
