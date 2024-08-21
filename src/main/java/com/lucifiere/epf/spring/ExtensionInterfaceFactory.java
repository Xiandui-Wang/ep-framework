package com.lucifiere.epf.spring;

import com.lucifiere.epf.extension.ExtensionProxy;
import org.springframework.beans.factory.FactoryBean;

public class ExtensionInterfaceFactory<T> implements FactoryBean<T> {

    /**
     * 扩展点接口类型
     */
    private Class<T> interfaceClass;
    /**
     * 扩展点代理类beanName
     */
    private String beanName;

    @Override
    public T getObject() throws Exception {
        return (T) new ExtensionProxy(interfaceClass, beanName).getProxy();
    }

    @Override
    public Class<T> getObjectType() {
        return interfaceClass;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public Class<T> getInterfaceClass() {
        return interfaceClass;
    }

    public void setInterfaceClass(Class<T> interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

}