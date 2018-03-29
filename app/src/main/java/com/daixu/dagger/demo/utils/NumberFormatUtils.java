package com.daixu.dagger.demo.utils;

import java.text.DecimalFormat;

/**
 * Created by 32422 on 2017/10/14.
 */

public class NumberFormatUtils {

    /**
     * 使用volatile关键字保其可见性
     */
    volatile private static NumberFormatUtils instance = null;

    private NumberFormatUtils() {
    }

    public static NumberFormatUtils getInstance() {
        if (instance == null) {
            synchronized (NumberFormatUtils.class) {
                if (instance == null) {
                    //二次检查
                    instance = new NumberFormatUtils();
                }
            }
        }
        return instance;
    }

    /**
     * 传入一个float类型的数据,如果小数点后为0则格式化整数,否则格式化为两位小数
     *
     * @param number
     * @return strNumber
     */
    public String format(double number) {
        // 构造方法的字符格式这里如果小数不足2位,会以0补足.
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        String strNumber = "0";
        if (number != 0) {
            if (number % 1 == 0) {
                // 是这个整数，小数点后面是0
                strNumber = (int) number + "";
            } else {
                //不是整数，小数点后面不是0
                strNumber = decimalFormat.format(number);
                // format 返回的是字符串
            }
        }
        return strNumber;
    }

    /**
     * 传入一个float类型的数据,如果小数点后为0则格式化整数,否则格式化为两位小数
     *
     * @param number
     * @return strNumber
     */
    public String format2Decimal(double number) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        // 构造方法的字符格式这里如果小数不足2位,会以0补足.
        String strNumber = "0.00";
        if (number != 0) {
            strNumber = decimalFormat.format(number);
            // format 返回的是字符串
        }
        return strNumber;
    }
}
