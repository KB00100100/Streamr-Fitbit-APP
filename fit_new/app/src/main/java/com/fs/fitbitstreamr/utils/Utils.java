package com.fs.fitbitstreamr.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
    public static String getDateToString(String milSecond) {
        String pattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdr = new SimpleDateFormat(pattern);
        int i = Integer.parseInt(milSecond);
        String times = sdr.format(new Date(i * 1000L));
        return times;
    }
    public static boolean copy(Context mContex, String copyStr) {
        try {
            //获取剪贴板管理器
            ClipboardManager cm = (ClipboardManager) mContex.getSystemService(Context.CLIPBOARD_SERVICE);
            // 创建普通字符型ClipData
            ClipData mClipData = ClipData.newPlainText("Label", copyStr);
            // 将ClipData内容放到系统剪贴板里。
            cm.setPrimaryClip(mClipData);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
