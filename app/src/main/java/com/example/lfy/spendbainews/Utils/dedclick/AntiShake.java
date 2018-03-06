package com.example.lfy.spendbainews.Utils.dedclick;

/**
 * author:ggband
 * date:2017/8/29 11:45
 * email:ggband520@163.com
 * desc:
 *
 *   if (AntiShake.check(view.getId())) {    //判断是否多次点击
 return;
 }
 */

public class AntiShake {

    private static LimitQueue<OneClick> queue = new LimitQueue<>(20);

    public static boolean check(Object o) {
        String flag;
        if(o == null) {
            flag = Thread.currentThread().getStackTrace()[2].getMethodName();
        } else {
            flag = o.toString();
        }
        for (OneClick util : queue.getArrayList()) {
            if (util.getMethodName().equals(flag)) {
                return util.check();
            }
        }
        OneClick clickUtil = new OneClick(flag);
        queue.offer(clickUtil);
        return clickUtil.check();
    }
}
