package com.github.gzuliyujiang.DynamicGridLayout.imageloader;

import android.content.Context;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.annotation.WorkerThread;

/**
 * 图片加载框架，在此可方便的切换其他图片加载框架
 * <p>
 * Created by liyujiang on 2018/9/19 15:42
 */
@SuppressWarnings("unused")
public final class ImageLoader {
    private static IImageLoader iImageLoader;
    private static int imagePlaceholder;

    public static void useLoader(IImageLoader loader) {
        iImageLoader = loader;
    }

    public static void setDefaultPlaceholder(@DrawableRes int placeholder) {
        imagePlaceholder = placeholder;
    }

    public static <T> void display(ImageView imageView, T urlOrPath) {
        checkImageLoader();
        iImageLoader.display(imageView, urlOrPath, imagePlaceholder);
    }

    public static <T> void display(ImageView imageView, T urlOrPath, @DrawableRes int placeholder) {
        checkImageLoader();
        iImageLoader.display(imageView, urlOrPath, placeholder);
    }

    public static void clearCache(Context context) {
        checkImageLoader();
        iImageLoader.clearCache(context);
    }

    @WorkerThread
    public static long getCacheSize(Context context) {
        checkImageLoader();
        return iImageLoader.getCacheSize(context);
    }

    private static void checkImageLoader() {
        if (iImageLoader == null) {
            throw new RuntimeException("Please use image loader in your Application");
        }
    }

}
