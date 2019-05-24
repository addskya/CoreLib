package cn.orange.core.util;

import java.util.UUID;

/**
 * Created by Orange on 19-1-4.
 * Email:addskya@163.com
 */
public class IDUtil {

    /**
     * 产生一个唯一的UUID
     *
     * @return 产生一个唯一的UUID
     */
    public static String obtainID() {
        return UUID.randomUUID().toString();
    }
}
