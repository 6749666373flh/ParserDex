package com.fan.parserdex.utils;

import android.content.res.AssetManager;
import android.os.Environment;

import com.fan.parserdex.App;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

public class DexUtil {

    public static byte[] getDexBytes(String path) {
        byte[] srcByte = null;


        File file = new File(path);
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream(file);
            byte[] b = new byte[(int) file.length()];
            fis.read(b);
            fos = new FileOutputStream(file);
            fos.write(b);

            srcByte = b;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (srcByte == null) {
            LogUtils.e("get src error");
            return null;
        }
        return srcByte;
    }

    /**
     * 获取sdCard路径
     *
     * @return
     */
    public static String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(android.os.Environment.MEDIA_MOUNTED);//判断sd卡是否存在
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
        }
        assert sdDir != null;
        return sdDir.toString();
    }

    public static byte[] getAssetsDexBytes(String fileName) {
        byte[] srcByte = null;

        //获取assets目录下的文件
        try (AssetManager assets = App.getApplication().getAssets(); InputStream openInputStream = assets.open(fileName);
             ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();) {

            byte[] buffer = new byte[4096];
            int length;
            while ((length = openInputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, length);
            }
            srcByte = byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
//            e.printStackTrace();
            LogUtils.e("getAssetsDexBytes error", e.getMessage());
        }
        return srcByte;
    }
}
