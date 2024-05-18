package com.coldlake.app.payment.service.payment;

/**
 * @Author: <a href="https://github.com/zoowayss">zoowayss</a>
 * @Date: 2024/5/17 09:27
 */
public interface LogService {

    /**
     * 获取 当前方法名@类名
     *
     * @return
     */
    static String cm() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        // 堆栈的第一个元素是当前线程的 getStackTrace 方法，第二个元素是调用 getStackTrace 方法的方法，因此取第三个元素
        StackTraceElement stackTraceElement = stackTrace[2];
        return stackTraceElement.getMethodName() + "@" + stackTraceElement.getMethodName();
    }
}
