package com.artion.androiddemos.common;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Context;
import android.text.TextUtils;

import com.artion.androiddemos.utils.DebugTool;

/**
 * Created by caijinsheng on 2019/8/23.
 */

public class ClipUtils {

    /**
     * 实现文本复制功能
     *
     * @param content 复制的文本
     */
    public static void copy(Context context, String content) {
        if (!TextUtils.isEmpty(content)) {
            // 得到剪贴板管理器
            ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            if(cmb == null) {
                return;
            }
//            cmb.setText(content.trim());
            // 创建一个剪贴数据集，包含一个普通文本数据条目（需要复制的数据）
            ClipData clipData = ClipData.newPlainText(null, content);
            // 把数据集设置（复制）到剪贴板
            cmb.setPrimaryClip(clipData);
        }
    }

    /**
     * 获取系统剪贴板内容
     */
    public static String getClipContent(Context context, String label) {
        ClipboardManager manager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        if (manager != null) {
            if(!manager.hasPrimaryClip()) {
                return "";
            }

            ClipDescription description = manager.getPrimaryClipDescription();
            if(description == null ) {//|| !description.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)
                return "";
            }

            DebugTool.info("ClipUtils", "description = " + description.toString());

            CharSequence scLabel = description.getLabel();
            if(scLabel != null) {
                DebugTool.info("ClipUtils", "scLabel = " + scLabel.toString());
            }

            ClipData clipData = manager.getPrimaryClip();
            if(clipData == null) {
                return "";
            }
            int count = clipData.getItemCount();
            if(count <= 0) {
                return "";
            }
            ClipData.Item item = null;
            for (int i = 0; i < count; i++) {
                item = clipData.getItemAt(i);
                if(item != null) {
                    CharSequence csText = item.getText();
                    if(csText != null) {
                        String clipValue = csText.toString();
                        if(!TextUtils.isEmpty(clipValue)) {
                            DebugTool.info("ClipUtils", "clipValue = " + clipValue);
                        }
                    }
                }
            }

        }
        return "";
    }

    /**
     * 清空剪贴板内容
     */
    public static void clearClipboard(Context context) {
        ClipboardManager manager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        if (manager != null) {
            try {
                manager.setPrimaryClip(manager.getPrimaryClip());
                manager.setText(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
