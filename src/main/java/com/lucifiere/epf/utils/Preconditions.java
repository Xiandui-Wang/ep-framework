package com.lucifiere.epf.utils;

import com.lucifiere.epf.EpfException;

public final class Preconditions {

    public static void check(boolean expression, String msg) {
        if (!expression) {
            throw new EpfException("condition check failed：" + msg);
        }
    }

    public static void check(boolean expression) {
       check(expression, "前置条件验证失败！");
    }

}
