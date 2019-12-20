package com.github.gzuliyujiang.DynamicGridLayout.logger;

/**
 * 日志打印器
 * Created by liyujiang on 2019/8/20
 *
 * @author 大定府羡民
 */
public interface IPrinter {

    /**
     * 将调试日志打印到控制台，一般用于开发阶段
     */
    void printLog(String log);

}