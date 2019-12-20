package com.github.gzuliyujiang.DynamicGridLayout.logger;

import android.util.Log;

/**
 * <pre>
 * 调试日志输出接口，{@link IPrinter}可选择使用以下开源项目进行实现：
 * https://github.com/orhanobut/logger
 * https://github.com/elvishew/xLog
 * https://github.com/ZhaoKaiQiang/KLog
 * https://github.com/fengzhizi715/SAF-Kotlin-log
 * https://github.com/EsotericSoftware/minlog
 * </pre>
 * Created by liyujiang on 2019/8/20
 *
 * @author 大定府羡民
 */
@SuppressWarnings({"unused"})
public final class Logger implements IPrinter {
    public static String TAG = "LYJ";
    public static boolean ENABLE = false;
    private static IPrinter printer;

    private Logger() {
    }

    @Override
    public void printLog(String log) {
        Log.d(Logger.TAG, log);
    }

    public static void usePrinter(IPrinter iPrinter) {
        if (iPrinter == null) {
            return;
        }
        printer = iPrinter;
    }

    /**
     * 打印调试日志，用于开发阶段
     */
    public static void debug(Object object) {
        if (!ENABLE) {
            return;
        }
        printer.printLog(objectToString(object));
    }

    private static String objectToString(Object object) {
        if (object instanceof Throwable) {
            return Log.getStackTraceString((Throwable) object);
        }
        return object.toString();
    }

}
