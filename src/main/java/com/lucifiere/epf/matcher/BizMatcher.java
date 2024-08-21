package com.lucifiere.epf.matcher;

import java.lang.reflect.Method;

public interface BizMatcher {

    boolean isMatch(Object proxy, Method method, Object[] args);

}
