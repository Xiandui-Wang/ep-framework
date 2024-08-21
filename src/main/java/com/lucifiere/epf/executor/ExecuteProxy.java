package com.lucifiere.epf.executor;

public interface ExecuteProxy {

    ExecuteResponse execute(ExecuteRequest request) throws Exception;

}
