package com.lucifiere.epf.executor;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RemoteInvokeConfig {

    private String appKey;

    private String port;

    private String service;

    private long timeout;

}
