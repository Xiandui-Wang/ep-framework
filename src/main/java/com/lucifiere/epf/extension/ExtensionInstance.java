package com.lucifiere.epf.extension;

import com.lucifiere.epf.executor.ExecuteProxy;
import com.lucifiere.epf.executor.RemoteInvokeConfig;
import com.lucifiere.epf.matcher.BizMatcher;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ExtensionInstance {

    /**
     * 实现类
     */
    private Object instance;

    /**
     * 匹配顺序
     */
    private Integer priority;

    /**
     * 能力描述
     */
    private String desc;

    /**
     * 实例ID
     */
    private String instanceId;

    /**
     * 执行器
     */
    private ExecuteProxy executeProxy;

    /**
     * 匹配器
     */
    private List<BizMatcher> matchers;

}
