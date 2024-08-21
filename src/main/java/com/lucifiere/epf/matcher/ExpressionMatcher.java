package com.lucifiere.epf.matcher;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.lucifiere.epf.annotations.ExtContext;
import com.lucifiere.epf.context.ExtensionRequest;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public abstract class ExpressionMatcher implements BizMatcher {

    protected final String expression;

    public ExpressionMatcher(String expression) {
        this.expression = expression;
    }

    @Override
    public boolean isMatch(Object proxy, Method method, Object[] args) {
        Map<String, Object> paramsMap = new HashMap<>();
        mergeExpressionParams(paramsMap, args);
        mergeExtensionMethodContext(paramsMap, method, args);
        return evaluate(paramsMap);
    }

    private void mergeExpressionParams(Map<String, Object> allExpressionParams, Object[] args) {
        if (args.length == 1 && args[0] != null && args[0] instanceof ExtensionRequest) {
            BeanUtil.beanToMap(args[0], allExpressionParams, CopyOptions.create().setEditable(ExtensionRequest.class));
        }
    }

    private void mergeExtensionMethodContext(Map<String, Object> allExpressionParams, Method method, Object[] args) {
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        for (int i = 0; i < parameterAnnotations.length; i++) {
            for (Annotation annotation : parameterAnnotations[i]) {
                if (annotation instanceof ExtContext extContext) {
                    allExpressionParams.put(extContext.value(), args[i]);
                }
            }
        }
    }

    protected abstract boolean evaluate(Map<String, Object> params);

}
