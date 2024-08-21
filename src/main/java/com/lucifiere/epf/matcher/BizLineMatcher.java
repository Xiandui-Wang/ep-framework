package com.lucifiere.epf.matcher;

import com.lucifiere.epf.enums.BizLine;

import java.lang.reflect.Method;
import java.util.List;

public class BizLineMatcher implements BizMatcher {

    private final List<BizLine> bizLine;

    public BizLineMatcher(BizLine[] bizLine) {
        this.bizLine = List.of(bizLine);
    }

    @Override
    public boolean isMatch(Object proxy, Method method, Object[] args) {
        return bizLine.contains(null);
    }
}
