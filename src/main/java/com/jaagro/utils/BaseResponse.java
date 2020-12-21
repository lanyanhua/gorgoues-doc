//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.jaagro.utils;

import com.lancabbage.lancodeapi.enums.ResponseStatusCode;

import java.io.Serializable;
import java.util.Map;

public class BaseResponse<T> implements Serializable {
    private int statusCode;
    private String statusMsg;
    private T data;

}
