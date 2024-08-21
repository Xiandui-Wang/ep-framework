package com.lucifiere.epf.matcher;

import com.googlecode.aviator.AviatorEvaluator;

import java.util.Map;

public class AviatorMatcher extends ExpressionMatcher {

    public AviatorMatcher(String expression) {
        super(expression);
    }

    @Override
    protected boolean evaluate(Map<String, Object> params) {
        return (Boolean) AviatorEvaluator.execute(expression, params);
    }

}
