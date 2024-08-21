package com.lucifiere.epf.executor;

import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Method;

@Getter
@Setter
public class ExecuteRequest {

    private Object proxy;

    private Method method;

    private Object[] args;

}
