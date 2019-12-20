package com.github.gzuliyujiang.DynamicGridLayout.logger;

import com.blankj.utilcode.util.LogUtils;

/**
 * 自定义调试日志打印
 * Created by liyujiang on 2019/9/15.
 *
 * @author 大定府羡民
 */
public class UtilCodeLogger implements IPrinter {
    private static final String TAG = "liyujiang";

    public UtilCodeLogger() {
        Logger.ENABLE = true;
        Logger.TAG = TAG;
        Logger.usePrinter(this);
        LogUtils.getConfig().setLogSwitch(true);
        LogUtils.getConfig().setBorderSwitch(true);
        LogUtils.getConfig().setConsoleSwitch(true);
        LogUtils.getConfig().setLogHeadSwitch(true);
        LogUtils.getConfig().setLog2FileSwitch(false);
        LogUtils.getConfig().setGlobalTag(TAG);
        LogUtils.getConfig().setStackOffset(2);
        LogUtils.getConfig().setStackDeep(2);
    }

    @Override
    public void printLog(String log) {
        //部分国产机型默认屏蔽了WARN级别以下的LogCat，故使用WARN级别打印日志
        LogUtils.w(log);
    }

}
