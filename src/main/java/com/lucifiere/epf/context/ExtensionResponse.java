package com.lucifiere.epf.context;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExtensionResponse<T> {

    private int code;

    private String message;

    private T data;

}
