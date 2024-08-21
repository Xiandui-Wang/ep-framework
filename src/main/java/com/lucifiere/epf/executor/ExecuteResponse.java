package com.lucifiere.epf.executor;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExecuteResponse {

    private boolean error;

    private String errorMsg;

    private Object result;

}
