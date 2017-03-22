package com.stuhua.themechangedemo.utils;

import android.os.Environment;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * @ClassName: LogUtils
 * @Description: 这是一个Log的工具类，可以保存在sd/log/log.txt文件里
 * @author: liulh
 * @date: 2016年12月9日 上午11:45:23
 */
public class LogUtils {
    private static int DEFAULT_LEVEL = Log.DEBUG;// 默认level
    private static final String DEFAULT_DIRPATH = "/themechage/1/log/"; // 存放日志文件的所在路径
    private static final String DEFAULT_LOGNAME = "ThemeChangeDemo.txt"; // 存放日志的文本名
    private static final String DEFAULT_INFORMAT = "yyyy-MM-dd hh:mm:ss"; // 设置时间的格式

    private static final boolean isWrite = false; // isWrite:用于开关是否吧日志写入txt文件中
    private static boolean isDebug = false;// 默认显示log

    /**
     * @param throwable
     * @Title: print
     * @Description: 把异常用来输出日志的综合方法
     * @return: void
     */
    public static void print(Throwable throwable) {
        print(exToString(throwable));
        if (isWrite) {
            write(exToString(throwable));
        }
    }

    /**
     * @param msg
     * @Title: print
     * @Description: 使用类名作为tag，默认是Log.d
     * @return: void
     */
    public static void print(String msg) {
        StackTraceElement[] stackTraceElements = new Throwable().getStackTrace();
        performPrint(DEFAULT_LEVEL, stackTraceElements[1].getFileName(), msg);
        if (isWrite) {
            write(msg);
        }
    }

    /**
     * 用于把日志内容写入制定的文件
     *
     * @param @param tag 标识 @param @param msg 要输出的内容 @return void 返回类型 @throws
     */
    public static void write(String msg) {
        String path = createMkdirsAndFiles(DEFAULT_DIRPATH, DEFAULT_LOGNAME);
        if (TextUtils.isEmpty(path)) {
            return;
        }
        String threadName = Thread.currentThread().getName();
        String lineIndicator = getLineIndicator();
        String log = DateFormat.format(DEFAULT_INFORMAT, System.currentTimeMillis()) + " " + threadName + " "
                + lineIndicator + msg;
        System.out.println("path=" + path);
        write2File(path, log, true);
    }

    /**
     * @param tag
     * @param msg
     * @Title: print
     * @Description: 打印自定义TAG
     * @return: void
     */
    public static void print(String tag, String msg) {
        performPrint(DEFAULT_LEVEL, tag, msg);
        if (isWrite) {
            write(msg);
        }
    }

    /**
     * @param level
     * @param msg
     * @Title: print
     * @Description: 打印自定义level
     * @return: void
     */
    public static void print(int level, String msg) {
        StackTraceElement[] stackTraceElements = new Throwable().getStackTrace();
        performPrint(DEFAULT_LEVEL, stackTraceElements[1].getFileName(), msg);
        if (isWrite) {
            write(msg);
        }
    }

    /**
     * @param level
     * @param tag
     * @param msg
     * @Title: print
     * @Description: 打印自定义Tag和Level
     * @return: void
     */
    public static void print(int level, String tag, String msg) {
        performPrint(level, tag, msg);
        if (isWrite) {
            write(msg);
        }
    }

    /**
     * @param level
     * @param tag
     * @param msg
     * @Title: performPrint
     * @Description: 打印
     * @return: void
     */
    private static void performPrint(int level, String tag, String msg) {
        if (!isDebug) {
            return;
        }
        String threadName = Thread.currentThread().getName();
        String lineIndicator = getLineIndicator();
        Log.println(level, tag, threadName + " " + lineIndicator + " " + msg);
    }

    /**
     * @return
     * @Title: getLineIndicator
     * @Description: 获取文件名，行号，方法名
     * @return: String
     */
    private static String getLineIndicator() {
        // 3代表方法的调用深度：0-getLineIndicator，1-performPrint，2-print，3-调用该工具类的方法位置
        StackTraceElement element = ((new Exception()).getStackTrace())[3];
        StringBuffer sb = new StringBuffer("(").append(element.getFileName()).append(":")
                .append(element.getLineNumber()).append(") ").append(element.getMethodName()).append(":");
        return sb.toString();
    }

    /**
     * 把异常信息转化为字符串
     *
     * @param ex 异常信息
     * @return 异常信息字符串
     */
    private static String exToString(Throwable ex) {
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        printWriter.close();
        String result = writer.toString();
        return result;
    }

    /**
     * 判断sdcrad是否已经安装
     *
     * @return boolean true安装 false 未安装
     */
    public static boolean isSDCardMounted() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    /**
     * 得到sdcard的路径
     *
     * @return
     */
    public static String getSDCardRoot() {
        System.out.println(isSDCardMounted() + Environment.getExternalStorageState());
        if (isSDCardMounted()) {
            return "/mnt/sdcard";
            // 系统APP会报错
            // return
            // Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        return "";
    }

    /**
     * 创建文件的路径及文件
     *
     * @param path     路径，方法中以默认包含了sdcard的路径，path格式是"/path...."
     * @param filename 文件的名称
     * @return 返回文件的路径，创建失败的话返回为空
     */
    public static String createMkdirsAndFiles(String path, String filename) {
        if (TextUtils.isEmpty(path)) {
            throw new RuntimeException("路径为空");
        }
        path = getSDCardRoot() + path;
        File file = new File(path);
        if (!file.exists()) {
            try {
                file.mkdirs();
            } catch (Exception e) {
                throw new RuntimeException("创建文件夹不成功");
            }
        }
        File f = new File(file, filename);
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException("创建文件不成功");
            }
        }
        return f.getAbsolutePath();
    }

    /**
     * 把内容写入文件
     *
     * @param path 文件路径
     * @param text 内容
     */
    public static void write2File(String path, String text, boolean append) {
        BufferedWriter bw = null;
        try {
            // 1.创建流对象
            bw = new BufferedWriter(new FileWriter(path, append));
            // 2.写入文件
            bw.write(text);
            // 换行刷新
            bw.newLine();
            bw.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 4.关闭流资源
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 删除文件
     *
     * @param path
     * @return
     */
    public static boolean deleteFile(String path) {
        if (TextUtils.isEmpty(path)) {
            throw new RuntimeException("路径为空");
        }
        File file = new File(path);
        if (file.exists()) {
            try {
                file.delete();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}