package com.thinkgem.jeesite.modules.ams.entity.common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xiaopo on 15/12/11.
 */
public class Message implements Serializable {
    private static final long serialVersionUID = 5207178535135769475L;

    /**
     * 0 失败 / 1 成功
     */
    private Integer code = 0;

    private String message = "操作成功";

    private Map<String, Object> result = new HashMap<String, Object>();

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, Object> getResult() {
        return result;
    }

    public void setResult(Map<String, Object> result) {
        this.result = result;
    }

    public Message() {
    }

    public Message(Integer code) {
        this(code, "操作失败", null);
    }

    public Message(Integer code, String message) {
        this(code, message, null);
    }

    public Message(Integer code, String message, Map<String, Object> result) {
        this.code = code;
        this.message = message;
        this.result = result;
    }

    public boolean isSuccess() {
        return code == 1;
    }

    public void setMsg(Integer code,String message){
        this.code = code;
        this.message = message;
    }
}
