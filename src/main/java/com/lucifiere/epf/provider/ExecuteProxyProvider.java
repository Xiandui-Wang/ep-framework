package com.lucifiere.epf.provider;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.lucifiere.epf.EpfException;
import com.lucifiere.epf.annotations.ExtensionSpec;
import com.lucifiere.epf.annotations.LocalExtension;
import com.lucifiere.epf.annotations.RemoteSpiExtension;
import com.lucifiere.epf.executor.LocalCodeExecutor;
import com.lucifiere.epf.executor.RemoteInvokeConfig;
import com.lucifiere.epf.executor.RemoteSpiExecutor;
import com.lucifiere.epf.extension.ExtensionInstance;
import com.lucifiere.epf.extension.ExtensionProxy;
import com.lucifiere.epf.matcher.AviatorMatcher;
import com.lucifiere.epf.matcher.BizLineMatcher;
import com.lucifiere.epf.spring.ExtensionDefineRewriter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
public class ExecuteProxyProvider implements BeanDefinitionRegistryPostProcessor, ApplicationContextAware, BeanPostProcessor, ApplicationListener<ContextRefreshedEvent> {

    private final static Map<String, ExtensionProxy> PROVIDER = MapUtil.newConcurrentHashMap();

    private final static Set<BeanDefinitionHolder> BEAN_DEFINITION_SETS = new HashSet<>();

    private ApplicationContext applicationContext;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        LocalExtension localExtension = AnnotationUtils.findAnnotation(bean.getClass(), LocalExtension.class);
        RemoteSpiExtension remoteSpiExtension = AnnotationUtils.findAnnotation(bean.getClass(), RemoteSpiExtension.class);
        if (localExtension != null && remoteSpiExtension != null) {
            throw new EpfException("扩展点类型定义冲突");
        }

        if (localExtension != null) {
            initLocalCodeExtension(bean, localExtension);
        }
        if (remoteSpiExtension != null) {
            initRemoteSpiExtension(bean, remoteSpiExtension);
        }

        return bean;
    }

    private void initLocalCodeExtension(Object bean, LocalExtension localExtension) {
        ExtensionProxy extensionProxy = PROVIDER.get(localExtension.id());
        ExtensionInstance inst = ExtensionInstance.builder()
                .priority(localExtension.priority())
                .desc(localExtension.desc())
                .instanceId(localExtension.id())
                .matchers(new ArrayList<>())
                .executeProxy(new LocalCodeExecutor())
                .build();

        inst.getMatchers().add(new BizLineMatcher(localExtension.biz()));
        if (StrUtil.isNotBlank(localExtension.expression())) {
            inst.getMatchers().add(new AviatorMatcher(localExtension.expression()));
        }

        extensionProxy.init(bean, inst);
    }

    private void initRemoteSpiExtension(Object bean, RemoteSpiExtension remoteSpiExtension) {
        ExtensionProxy extensionProxy = PROVIDER.get(remoteSpiExtension.id());

        RemoteInvokeConfig config = new RemoteInvokeConfig();
        config.setPort(remoteSpiExtension.port());
        config.setService(remoteSpiExtension.service());
        config.setTimeout(remoteSpiExtension.timeout());
        config.setAppKey(remoteSpiExtension.appKey());

        ExtensionInstance inst = ExtensionInstance.builder()
                .priority(remoteSpiExtension.priority())
                .desc(remoteSpiExtension.desc())
                .instanceId(remoteSpiExtension.id())
                .matchers(new ArrayList<>())
                .executeProxy(new RemoteSpiExecutor(config))
                .build();

        inst.getMatchers().add(new BizLineMatcher(remoteSpiExtension.biz()));
        if (StrUtil.isNotBlank(remoteSpiExtension.expression())) {
            inst.getMatchers().add(new AviatorMatcher(remoteSpiExtension.expression()));
        }

        extensionProxy.init(bean, inst);
    }

    /**
     * 装配结果校验
     *
     * @param event 刷新事件
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        validIfProviderInitCorrectly();
    }

    private void validIfProviderInitCorrectly() {

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        ExtensionDefineRewriter rewriter = new ExtensionDefineRewriter(registry);
        rewriter.addIncludeFilter(new AnnotationTypeFilter(ExtensionSpec.class));
        BEAN_DEFINITION_SETS.addAll(rewriter.doScan(""));
    }

}
