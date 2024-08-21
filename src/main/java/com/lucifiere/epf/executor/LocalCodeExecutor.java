package com.lucifiere.epf.executor;

public class LocalCodeExecutor implements ExecuteProxy {

    @Override
    public ExecuteResponse execute(ExecuteRequest request) throws Exception {
        Object result = request.getMethod().invoke(request.getProxy(), request.getArgs());
        ExecuteResponse response = new ExecuteResponse();
        response.setResult(result);
        return response;
    }

}
