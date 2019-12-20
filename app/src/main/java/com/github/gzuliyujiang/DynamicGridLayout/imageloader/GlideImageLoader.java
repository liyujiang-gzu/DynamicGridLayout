package com.github.gzuliyujiang.DynamicGridLayout.imageloader;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.LogUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.util.concurrent.Executors;

/**
 * 使用Glide图片加载框架，参见 https://github.com/bumptech/glide
 * <p>
 * Created by liyujiang on 2018/8/28 14:56
 */
public class GlideImageLoader implements IImageLoader {

    @Override
    public <T> void display(final ImageView imageView, T urlOrRes, int placeholder) {
        if (urlOrRes instanceof Integer) {
            imageView.setImageResource((Integer) urlOrRes);
            return;
        }
        final ImageView.ScaleType originScaleType = imageView.getScaleType();
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        RequestOptions options = RequestOptions
                .errorOf(placeholder);
        Glide.with(imageView.getContext())
                .applyDefaultRequestOptions(options)
                .load(urlOrRes)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        imageView.setScaleType(originScaleType);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, com.bumptech.glide.load.DataSource dataSource, boolean isFirstResource) {
                        imageView.setScaleType(originScaleType);
                        return false;
                    }
                })
                .into(imageView);
    }

    @Override
    public void clearCache(final Context context) {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                Executors.newSingleThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        Glide.get(context).clearDiskCache();//必须运行在子线程
                    }
                });
                Glide.get(context).clearMemory();
                System.gc();
            } else {
                Glide.get(context).clearDiskCache();
                final Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Glide.get(context).clearMemory();//必须运行在主线程
                        System.gc();
                        handler.removeCallbacksAndMessages(null);
                    }
                });
            }
        } catch (Throwable throwable) {
            LogUtils.d(throwable);
        }
    }

    @Override
    public long getCacheSize(Context context) {
        return FileUtils.getLength(Glide.getPhotoCacheDir(context));
    }

}
