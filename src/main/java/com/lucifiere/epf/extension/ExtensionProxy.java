package com.lucifiere.epf.extension;


import cn.hutool.core.collection.CollUtil;
import com.lucifiere.epf.EpfException;
import com.lucifiere.epf.executor.ExecuteProxy;
import com.lucifiere.epf.executor.ExecuteRequest;
import com.lucifiere.epf.executor.ExecuteResponse;
import com.lucifiere.epf.utils.Exceptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.*;

public class ExtensionProxy implements InvocationHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExtensionProxy.class);

    /**
     * 扩展点接口类型
     */
    private final Class<?> interfaceClass;

    /**
     * 扩展点代理类beanName
     */
    private final String beanName;

    /**
     * 扩展点实现
     */
    private final List<ExtensionInstance> instances = CollUtil.newArrayList();

    public ExtensionProxy(Class<?> interfaceClass, String beanName) {
        this.interfaceClass = interfaceClass;
        this.beanName = beanName;
    }

    private static final Set<Method> ALL_OBJECT_METHODS = new HashSet<>();

    static {
        Collections.addAll(ALL_OBJECT_METHODS, Object.class.getDeclaredMethods());
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        try {
            if (ALL_OBJECT_METHODS.contains(method)) {
                return method.invoke(proxy, args);
            }

            ExtensionInstance instance = routeInstance(proxy, method, args);

            ExecuteProxy executeProxy = instance.getExecuteProxy();
            ExecuteRequest request = new ExecuteRequest();
            request.setProxy(proxy);
            request.setArgs(args);
            request.setMethod(method);
            ExecuteResponse response = executeProxy.execute(request);
            if (response.isError()) {
                throw new EpfException("扩展点执行失败：" + response.getErrorMsg());
            }

            return response.getResult();
        } catch (Throwable e) {
            throw Exceptions.unwrapThrowable(e);
        }
    }

    private ExtensionInstance routeInstance(Object proxy, Method method, Object[] args) {
        return instances.stream()
                .filter(inst -> inst.getMatchers().stream().anyMatch(m -> m.isMatch(proxy, method, args)))
                .min(Comparator.comparing(ExtensionInstance::getPriority))
                .orElseThrow(() -> new EpfException("匹配不到合适的实现类！"));
    }

    public Object getProxy() {
        return Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[]{interfaceClass}, this);
    }

    public void init(Object bean, ExtensionInstance instances) {
        if (!interfaceClass.isInstance(bean)) {
            return;
        }
        this.instances.add(instances);
    }

}