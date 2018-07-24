package com.liyahong.customview.uitls;

import android.content.Context;
import android.os.storage.StorageManager;
import android.text.TextUtils;
import android.util.Log;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * author: LiYaHong
 * email: happyboy183@163.com
 * create time: 2018/5/30 0030 17:48
 */

public class SDCardPathUtils {
    /**
     * 获取SD卡路径
     * @param mContext
     * @param is_removale  true: 获取外置SD卡路径  false: 获取内置SD路径
     * @return
     */
    private static String getStoragePath(Context mContext, boolean is_removale) {
        StorageManager mStorageManager = (StorageManager) mContext.getSystemService(Context.STORAGE_SERVICE);
        Class<?> storageVolumeClazz = null;
        try {
            storageVolumeClazz = Class.forName("android.os.storage.StorageVolume");
            if (mStorageManager != null) {
                Method getVolumeList = mStorageManager.getClass().getMethod("getVolumeList");
                Method getPath = storageVolumeClazz.getMethod("getPath");
                Method isRemovable = storageVolumeClazz.getMethod("isRemovable");
                Object result = getVolumeList.invoke(mStorageManager);
                final int length = Array.getLength(result);
                for (int i = 0; i < length; i++) {
                    Object storageVolumeElement = Array.get(result, i);
                    String path = (String) getPath.invoke(storageVolumeElement);
                    boolean removable = (Boolean) isRemovable.invoke(storageVolumeElement);
                    if (is_removale == removable) {
                        return path;
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取SD路径
     * @param context
     * @return
     */
    public static String getSDPath(Context context) {
        String storagePath = SDCardPathUtils.getStoragePath(context, false);
        storagePath = TextUtils.isEmpty(storagePath) ? "/mnt/sdcard" : storagePath + "/";
        Log.e("storagePath", storagePath);
        return storagePath;
    }
}