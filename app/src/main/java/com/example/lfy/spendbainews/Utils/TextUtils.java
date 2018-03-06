package com.example.lfy.spendbainews.Utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * author:ggband
 * date:2017/8/2 10:58
 * email:ggband520@163.com
 * desc:
 */

public class TextUtils {

    //手机号
    public static String dosubtext(String src) {
        if (src == null || src.length() <= 8)
            return "";
        //字符串截取
        String bb = src.substring(3, 7);
        //字符串替换
        return src.replace(bb, "****");

    }

    //前4 后4银行卡
    public static String dosubtext424(String src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length() <= 8)
            return "";
        //字符串截取
        String bb = src.substring(3, src.length() - 4);
        //字符串替换
        for (int i = 0; i < bb.length(); i++) {
            stringBuilder.append("*");
        }
        return src.replace(bb, stringBuilder.toString());

    }

    //后4银行卡
    public static String sub4end(String src) {
        if (src == null || src.length() < 10)
            return "";
        return src.substring(src.length() - 4, src.length());
    }

    //前4银行卡
    public static String sub4start(String src) {
        if (src == null || src.length() <= 10)
            return "";
        return src.substring(0, 4);
    }


    //将手机号格式化 xxx xxxx xxxx
    public static void phoneNum(final EditText editText) {

        if (editText == null)
            return;

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //手机号格式化xxx xxxx xxxx
                if (s == null || s.length() == 0) return;
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < s.length(); i++) {
                    if (i != 3 && i != 8 && s.charAt(i) == ' ') {
                        continue;
                    } else {
                        sb.append(s.charAt(i));
                        if ((sb.length() == 4 || sb.length() == 9) && sb.charAt(sb.length() - 1) != ' ') {
                            sb.insert(sb.length() - 1, ' ');
                        }
                    }
                }
                if (!sb.toString().equals(s.toString())) {
                    int index = start + 1;
                    if (sb.charAt(start) == ' ') {
                        if (before == 0) {
                            index++;
                        } else {
                            index--;
                        }
                    } else {
                        if (before == 1) {
                            index--;
                        }
                    }
                    editText.setText(sb.toString());
                    editText.setSelection(index);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    //去除字符串所有空格
    public static String repaceTrim(String str) {
        String t = "";
        if (str == null)
            return t;
        if (!str.contains(" "))
            return str;
        return str.replaceAll(" ", t);
    }


    public static String replaceEnter(String str) {

        String dest = "";

        if (str != null) {

            Pattern p = Pattern.compile("\\s*|\t|\r|\n");

            Matcher m = p.matcher(str);

            dest = m.replaceAll("");

        }

        return dest;

    }


    public static String addTrim4(String src) {

        StringBuffer buffer = new StringBuffer();
        if (src == null)
            return "";
        char[] srcChar = src.toCharArray();
        int length = srcChar.length;
        for (int i = 0; i < length; i++) {
            if (i == 0) {
                buffer.append(srcChar[0]);
                continue;
            } else if (i % 4 == 0)
                buffer.append(" ");
            buffer.append(srcChar[i]);
        }
        return buffer.toString();

    }


    /*
     * 将时间戳转换为时间
     */
    public static String stampToDate(String s) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }


    /*
     * 将时间转换为时间戳
     */
    public static String dateToStamp(String s) throws ParseException {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(s);
        long ts = date.getTime();
        res = String.valueOf(ts);
        return res;
    }

    public static String decimalTwo(float src) {
        return new DecimalFormat("##.##").format(src);//format 返回的是字符串
    }
}
