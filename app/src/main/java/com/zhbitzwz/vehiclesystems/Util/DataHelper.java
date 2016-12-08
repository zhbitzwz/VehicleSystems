package com.zhbitzwz.vehiclesystems.Util;

import android.content.Context;
import android.widget.EditText;

import com.zhbitzwz.vehiclesystems.Application.App;

/**
 * 数据储存转换封装类
 * Author: ZWZ
 * Date: 2016/10/7 on 15:52.
 */
public class DataHelper {
    private static Context context = App.getAppContext();
    //判断字符串是否为空
    public static boolean isStringNull(String str){
        return str.equals("");
    }
    //获取输入
    public static String getInputString(EditText et){
        return et.getText().toString();
    }
    //TODO 暂时没有封装SharedPreferences类
}
