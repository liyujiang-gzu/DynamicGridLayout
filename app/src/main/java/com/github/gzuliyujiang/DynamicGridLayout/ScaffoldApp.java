package com.github.gzuliyujiang.DynamicGridLayout;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.multidex.MultiDex;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.CrashUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.PathUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.ProcessUtils;
import com.blankj.utilcode.util.ThrowableUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.github.gzuliyujiang.DynamicGridLayout.imageloader.GlideImageLoader;
import com.github.gzuliyujiang.DynamicGridLayout.imageloader.ImageLoader;
import com.github.gzuliyujiang.DynamicGridLayout.logger.Logger;
import com.github.gzuliyujiang.DynamicGridLayout.logger.UtilCodeLogger;

import java.util.List;
import java.util.Stack;

/**
 * 应用程序全局上下文
 * Created by liyujiang on 2019/8/20
 *
 * @author 大定府羡民
 */
@SuppressWarnings("unused")
public final class ScaffoldApp extends Application {
    private static Application application;
    private static Stack<Activity> activityStack = new Stack<>();

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        MultiDex.install(this);
        Utils.init(this);
        Logger.usePrinter(new UtilCodeLogger());
        initCrashHandler();
        Logger.usePrinter(new UtilCodeLogger());
        ToastUtils.setGravity(Gravity.CENTER, 0, 0);
        registerActivityLifecycleCallbacks(new SimpleLifecycle());
        String processName = ProcessUtils.getCurrentProcessName();
        if (!getPackageName().equals(processName)) {
            Logger.debug("当前非主进程，不需进行某些初始化：" + processName);
            return;
        }
        ImageLoader.useLoader(new GlideImageLoader());
    }

    @SuppressLint("MissingPermission")
    private void initCrashHandler() {
        boolean storageGranted = PermissionUtils.isGranted(Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (storageGranted) {
            CrashUtils.init(PathUtils.getExternalAppFilesPath() + "/crash",
                    (crashInfo, e) -> LogUtils.d(crashInfo + "\n" + ThrowableUtils.getFullStackTrace(e)));
        }
    }

    /**
     * 得到当前进程的全局上下文对象。
     */
    public static Context getAppContext() {
        synchronized (ScaffoldApp.class) {
            return application.getApplicationContext();
        }
    }

    /**
     * 退出APP
     *
     * @param forceKill 是否强制杀死进程
     */
    public static void exitApp(boolean forceKill) {
        ActivityUtils.finishAllActivities();
        clearAllActivityTask();
        if (forceKill) {
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        }
    }

    /**
     * 清除最近任务记录，以便在按任务键看不到任何活动的界面，从而使得用户不能手动杀掉APP
     */
    public static void clearAllActivityTask() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return;
        }
        ActivityManager am = (ActivityManager) getAppContext().getSystemService(Context.ACTIVITY_SERVICE);
        if (am == null) {
            return;
        }
        try {
            List<ActivityManager.AppTask> appTasks = am.getAppTasks();
            for (ActivityManager.AppTask appTask : appTasks) {
                appTask.finishAndRemoveTask();
            }
        } catch (SecurityException e) {
            Logger.debug(e);
        }
    }

    public static Resources fixFontScale(Resources resources) {
        Configuration configuration = resources.getConfiguration();
        if (configuration.fontScale != 1.0f) {
            Logger.debug("禁调字大小，防布局变形：systemScale=" + configuration.fontScale);
            configuration.fontScale = 1.0f;
            resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        }
        return resources;
    }

    /**
     * 生命周期回调
     */
    public static class SimpleLifecycle implements Application.ActivityLifecycleCallbacks {
        private FragmentManager fragmentManager;
        private FragmentLifecycle fragmentLifecycle = new FragmentLifecycle();

        @CallSuper
        @Override
        public void onActivityCreated(Activity activity, Bundle bundle) {
            Logger.debug("[LIFECYCLE]" + activity.getClass().getName() + ".onActivityCreated");
            if (activity instanceof FragmentActivity) {
                fragmentManager = ((FragmentActivity) activity).getSupportFragmentManager();
                fragmentManager.registerFragmentLifecycleCallbacks(fragmentLifecycle, true);
            }
        }

        @CallSuper
        @Override
        public void onActivityStarted(Activity activity) {
            Logger.debug("[LIFECYCLE]" + activity.getClass().getName() + ".onActivityStarted");
        }

        @CallSuper
        @Override
        public void onActivityResumed(Activity activity) {
            Logger.debug("[LIFECYCLE]" + activity.getClass().getName() + ".onActivityResumed");
        }

        @CallSuper
        @Override
        public void onActivityPaused(Activity activity) {
            Logger.debug("[LIFECYCLE]" + activity.getClass().getName() + ".onActivityPaused");
        }

        @CallSuper
        @Override
        public void onActivityStopped(Activity activity) {
            Logger.debug("[LIFECYCLE]" + activity.getClass().getName() + ".onActivityStopped");
        }

        @CallSuper
        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
            Logger.debug("[LIFECYCLE]" + activity.getClass().getName() + ".onActivitySaveInstanceState");
        }

        @CallSuper
        @Override
        public void onActivityDestroyed(Activity activity) {
            Logger.debug("[LIFECYCLE]" + activity.getClass().getName() + ".onActivityDestroyed");
            if (fragmentManager != null) {
                fragmentManager.unregisterFragmentLifecycleCallbacks(fragmentLifecycle);
            }
        }

    }

    private static class FragmentLifecycle extends FragmentManager.FragmentLifecycleCallbacks {

        @CallSuper
        @Override
        public void onFragmentPreAttached(@NonNull FragmentManager fm, @NonNull Fragment f, @NonNull Context context) {
            Logger.debug(f.getClass().getName() + ".onFragmentPreAttached");
        }

        @CallSuper
        @Override
        public void onFragmentAttached(@NonNull FragmentManager fm, @NonNull Fragment f, @NonNull Context context) {
            Logger.debug(f.getClass().getName() + ".onFragmentAttached");
        }

        @CallSuper
        @Override
        public void onFragmentPreCreated(@NonNull FragmentManager fm, @NonNull Fragment f, @Nullable Bundle savedInstanceState) {
            Logger.debug(f.getClass().getName() + ".onFragmentPreCreated");
        }

        @CallSuper
        @Override
        public void onFragmentCreated(@NonNull FragmentManager fm, @NonNull Fragment f, @Nullable Bundle savedInstanceState) {
            Logger.debug(f.getClass().getName() + ".onFragmentCreated");
        }

        @CallSuper
        @Override
        public void onFragmentActivityCreated(@NonNull FragmentManager fm, @NonNull Fragment f, @Nullable Bundle savedInstanceState) {
            Logger.debug(f.getClass().getName() + ".onFragmentActivityCreated");
        }

        @CallSuper
        @Override
        public void onFragmentViewCreated(@NonNull FragmentManager fm, @NonNull Fragment f, @NonNull View v, @Nullable Bundle savedInstanceState) {
            Logger.debug(f.getClass().getName() + ".onFragmentViewCreated");
        }

        @CallSuper
        @Override
        public void onFragmentStarted(@NonNull FragmentManager fm, @NonNull Fragment f) {
            Logger.debug(f.getClass().getName() + ".onFragmentStarted");
        }

        @CallSuper
        @Override
        public void onFragmentResumed(@NonNull FragmentManager fm, @NonNull Fragment f) {
            Logger.debug(f.getClass().getName() + ".onFragmentResumed");
        }

        @CallSuper
        @Override
        public void onFragmentPaused(@NonNull FragmentManager fm, @NonNull Fragment f) {
            Logger.debug(f.getClass().getName() + ".onFragmentPaused");
        }

        @CallSuper
        @Override
        public void onFragmentStopped(@NonNull FragmentManager fm, @NonNull Fragment f) {
            Logger.debug(f.getClass().getName() + ".onFragmentStopped");
        }

        @CallSuper
        @Override
        public void onFragmentSaveInstanceState(@NonNull FragmentManager fm, @NonNull Fragment f, @NonNull Bundle outState) {
            Logger.debug(f.getClass().getName() + ".onFragmentSaveInstanceState");
        }

        @CallSuper
        @Override
        public void onFragmentViewDestroyed(@NonNull FragmentManager fm, @NonNull Fragment f) {
            Logger.debug(f.getClass().getName() + ".onFragmentViewDestroyed");
        }

        @CallSuper
        @Override
        public void onFragmentDestroyed(@NonNull FragmentManager fm, @NonNull Fragment f) {
            Logger.debug(f.getClass().getName() + ".onFragmentDestroyed");
        }

        @CallSuper
        @Override
        public void onFragmentDetached(@NonNull FragmentManager fm, @NonNull Fragment f) {
            Logger.debug(f.getClass().getName() + ".onFragmentDetached");
        }

    }

}
