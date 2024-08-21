package com.lucifiere.epf.executor;

public class RemoteSpiExecutor implements ExecuteProxy {

    private final RemoteInvokeConfig config;

    public RemoteSpiExecutor(RemoteInvokeConfig config) {
        this.config = config;
    }

    @Override
    public ExecuteResponse execute(ExecuteRequest request) {
        return null;
    }
}
