package com.ixinrun.base.utils;

import android.util.Log;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

/**
 * 描述：Log工具类
 * 1.直接使用是基于Logger封装{@link “https://github.com/orhanobut/logger”}；
 * 2.内部类NLog是原生log封装，且内部方法{@link NLog#log(int, String, String)}支持log过长多段打印；
 * </p>
 *
 * @author : ixinrun
 * @date : 2018/12/24
 */
public final class LoggerUtil {
    private static boolean sLoggable = true;
    private static String sDefaultTag = "LoggerUtil";
    private static ILogListener sLogListener;

    public static final int VERBOSE = 2;
    public static final int DEBUG = 3;
    public static final int INFO = 4;
    public static final int WARN = 5;
    public static final int ERROR = 6;
    public static final int JSON = 100;
    public static final int XML = 101;



    /**
     * log初始化
     *
     * @param loggable
     * @param defaultTag
     * @param logListener
     */
    public static void init(boolean loggable, String defaultTag, ILogListener logListener) {
        sLoggable = loggable;
        sDefaultTag = defaultTag;
        sLogListener = logListener;
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)
                .methodCount(2)
                .methodOffset(2)
                .tag(defaultTag)
                .build();
        Logger.addLogAdapter(
                new AndroidLogAdapter(formatStrategy) {
                    @Override
                    public boolean isLoggable(int priority, String tag) {
                        return sLoggable;
                    }
                }
        );
    }

    public interface ILogListener {
        void log(int priority, String tag, String msg, Throwable throwable);
    }

    public static void log(int priority, String tag, String msg, Throwable throwable) {
        if (msg == null) {
            return;
        }
        if (sLogListener != null) {
            sLogListener.log(priority, tag, msg, throwable);
        }

        switch (priority) {
            case VERBOSE:
                Logger.t(tag).v(msg);
                break;
            case DEBUG:
                Logger.t(tag).d(msg);
                break;
            case INFO:
                Logger.t(tag).i(msg);
                break;
            case WARN:
                Logger.t(tag).w(msg);
                break;
            case ERROR:
                Logger.t(tag).e(msg);
                break;
            case JSON:
                Logger.t(tag).json(msg);
                break;
            case XML:
                Logger.t(tag).xml(msg);
                break;
            default:
                break;
        }
    }

    public static void d(String msg) {
        log(DEBUG, sDefaultTag, msg, null);
    }

    public static void d(String tag, String msg) {
        log(DEBUG, tag, msg, null);
    }

    public static void e(String msg) {
        log(ERROR, sDefaultTag, msg, null);
    }

    public static void e(String tag, String msg) {
        log(ERROR, tag, msg, null);
    }

    public static void w(String msg) {
        log(WARN, sDefaultTag, msg, null);
    }

    public static void w(String tag, String msg) {
        log(WARN, tag, msg, null);
    }

    public static void v(String msg) {
        log(VERBOSE, sDefaultTag, msg, null);
    }

    public static void v(String tag, String msg) {
        log(VERBOSE, tag, msg, null);
    }

    public static void i(String msg) {
        log(INFO, sDefaultTag, msg, null);
    }

    public static void i(String tag, String msg) {
        log(INFO, tag, msg, null);
    }

    public static void json(String json) {
        log(JSON, sDefaultTag, json, null);
    }

    public static void xml(String xml) {
        log(XML, sDefaultTag, xml, null);
    }

    /**
     * 原生log
     */
    public static class NLog {

        /**
         * 支持log过长多段打印
         *
         * @param priority
         * @param tag
         * @param str
         */
        public static void log(int priority, String tag, String str) {
            if (null == str || "".equals(str)) {
                return;
            }
            final int STR_LENGTH = str.length();
            final int MAX = 3000;   //需要截取的最大长度,小于4k
            int beginIndex = 0;     //截取的起始位置
            int endIndex = MAX;     //截取的结束位置
            String sub;             //进行截取操作的string
            String indexTag;        //每段日志的tag
            while (beginIndex < STR_LENGTH) {
                if (STR_LENGTH < endIndex) {
                    endIndex = STR_LENGTH;
                }
                sub = str.substring(beginIndex, endIndex);
                indexTag = STR_LENGTH <= MAX ? tag : tag + "-<" + beginIndex + "," + endIndex + ">";

                //输出log
                switch (priority) {
                    case VERBOSE:
                        Log.v(indexTag, sub);
                        break;
                    case DEBUG:
                        Log.d(indexTag, sub);
                        break;
                    case INFO:
                        Log.i(indexTag, sub);
                        break;
                    case WARN:
                        Log.w(indexTag, sub);
                        break;
                    case ERROR:
                        Log.e(indexTag, sub);
                        break;
                    default:
                        return;
                }

                beginIndex = endIndex;
                endIndex += MAX;
            }
        }

        public static void d(String tag, String msg) {
            log(DEBUG, tag, msg);
        }

        public static void e(String tag, String msg) {
            log(ERROR, tag, msg);
        }

        public static void w(String tag, String msg) {
            log(WARN, tag, msg);
        }

        public static void v(String tag, String msg) {
            log(VERBOSE, tag, msg);
        }

        public static void i(String tag, String msg) {
            log(INFO, tag, msg);
        }
    }

}
