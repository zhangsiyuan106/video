package com.boot.utils;

/**
 * @author hsw
 * @date 2021/8/13
 */

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 获取随机数
 *
 * @author qianyi
 */
public class RandomUtil {

    public String achieveCode() {
        String[] beforeShuffle = new String[]{"2", "3", "4", "5", "6", "7", "8", "9", "1"};
        List list = Arrays.asList(beforeShuffle);
        Collections.shuffle(list);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
        }
        String afterShuffle = sb.toString();
        String result = afterShuffle.substring(3, 9);
        return result;
    }
}
