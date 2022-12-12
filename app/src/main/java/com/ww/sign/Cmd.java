package com.ww.sign;

import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;

/**
 * 这个类暂时没有用到，留以备用
 */
public class Cmd {
    private static final String TAG = Cmd.class.getSimpleName();

    private String runCmd(String cmd) {
        Log.d(TAG, "chuxiao execRootCmd, runCmd: " + cmd);
        if (TextUtils.isEmpty(cmd) || cmd.length() < 11) {
            return "";
        }
        String cmdHead = cmd.substring(0, 9);
        if (!"adb shell".equals(cmdHead)) {
            return "";
        }
        return execRootCmd(cmd);
    }

    /**
     * 执行命令并且输出结果
     */
    public static String execRootCmd(String cmd) {
        String content = "";
        try {
            cmd = cmd.replace("adb shell", "");
            Process process = Runtime.getRuntime().exec(cmd);
            Log.d(TAG, "chuxiao execRootCmd, process " + process.toString());
            content = process.toString();
        } catch (IOException e) {
            Log.d(TAG, "chuxiao execRootCmd, exception " + e.toString());
            e.printStackTrace();
        }
        return content;
    }
}
