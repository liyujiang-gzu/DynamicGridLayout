package com.github.gzuliyujiang.DynamicGridLayout.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.github.gzuliyujiang.DynamicGridLayout.logger.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 操作安装包中的“assets”目录下的文件
 * Created by liyujiang on 2013-11-2
 *
 * @author 大定府羡民
 */
@SuppressWarnings({"unused"})
public class AssetUtils {

    protected AssetUtils() {
        throw new UnsupportedOperationException("You can't instantiate me");
    }

    public static byte[] readBytes(Context context, String assetPath) {
        Logger.debug("read assets file as byte array: " + assetPath);
        try {
            return IOUtils.readBytesThrown(context.getAssets().open(assetPath));
        } catch (IOException e) {
            Logger.debug(e);
        }
        return null;
    }

    public static String readText(Context context, String assetPath) {
        Logger.debug("read assets file as text: " + assetPath);
        try {
            return IOUtils.readStringThrown(context.getAssets().open(assetPath), "utf-8");
        } catch (IOException e) {
            Logger.debug(e);
            return "";
        }
    }

    /**
     * 通过文件名从Assets中获得资源，以位图的形式返回
     *
     * @param assetPath 文件名应为assets文件下载绝对路径
     * @return 以位图的形式返回 bitmap
     */
    public static Bitmap readBitmap(Context context, String assetPath) {
        Logger.debug("read assets file as bitmap: " + assetPath);
        Bitmap bitmap = null;
        InputStream is = null;
        try {
            is = context.getAssets().open(assetPath);
            bitmap = BitmapFactory.decodeStream(is);
            // 96 dpi
            bitmap.setDensity(96);
        } catch (IOException e) {
            Logger.debug(e);
        } finally {
            IOUtils.closeSilently(is);
        }
        return bitmap;
    }

    /**
     * 复制assets下指定目录下的所有文件或目录到指定的目录下
     */
    public static void copyDir(Context context, String assetDirPath, String dirPath) {
        Logger.debug(String.format("copy assets folder %s to %s", assetDirPath, dirPath));
        throw new RuntimeException("未实现");
    }

    /**
     * 复制assets下的文件
     */
    public static void copyFile(Context context, String assetFilePath, String filePath) {
        Logger.debug(String.format("copy assets file %s to %s", assetFilePath, filePath));
        File outFile = new File(filePath);
        InputStream is = null;
        OutputStream out = null;
        try {
            is = context.getAssets().open(assetFilePath);
            if (outFile.exists()) {
                int size = is.available();
                if (outFile.length() == size) {
                    is.close();
                    Logger.debug("assets file length is " + size + ", equals " + outFile.getAbsolutePath());
                    return;
                } else {
                    //noinspection ResultOfMethodCallIgnored
                    outFile.delete();
                }
            } else {
                //noinspection ResultOfMethodCallIgnored
                outFile.getParentFile().mkdirs();
            }
            out = new FileOutputStream(outFile);
            // Transfer bytes from in to out
            byte[] buf = new byte[524288];
            while (true) {
                int len = is.read(buf);
                if (len == -1) {
                    break;
                } else {
                    out.write(buf, 0, len);
                }
            }
        } catch (IOException e) {
            Logger.debug(e);
        } finally {
            IOUtils.closeSilently(out);
            IOUtils.closeSilently(is);
        }
    }

    /**
     * 获取文件大小
     */
    public static long obtainLength(Context context, String assetFilePath) {
        Logger.debug("get assets file length: " + assetFilePath);
        long size = 0;
        InputStream is = null;
        try {
            is = context.getAssets().open(assetFilePath);
            size = is.available();
            is.close();
        } catch (IOException e) {
            Logger.debug(e);
        } finally {
            IOUtils.closeSilently(is);
        }
        return size;
    }

}
