package com.lcy.fcui.utils;

import com.lcy.fcui.App;

/**
 * @author FanCoder.LCY
 * @date 2018/8/15 16:39
 * @email 15708478830@163.com
 * @desc 显示相关工具类
 **/
public class DisplayUtil {
    /**
     * 将px转换为与之相等的dp
     */
    public static int px2dp(final float pxValue) {
        final float scale =  App.context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    /**
     * 将dp转换为与之相等的px
     */
    public static int dp2px(final float dipValue) {
        final float scale = App.context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * sp转px
     */
    public static int sp2px(final float spValue) {
        final float fontScale = App.context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * px转sp
     */
    public static int px2sp(final float pxValue) {
        final float fontScale = App.context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }
}
