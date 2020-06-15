package com.basestation.bean;

import com.basestation.common.ResultCode;

/**
 * 公共响应结构
 */
public class CommonResponse {
    private static final int META_SUCCESS = 0;
    private static final int META_ERROR = 1;

    /**
     * 响应结果数据
     */
    private Object data;
    /**
     * 分页信息
     */
    private Page page;
    /**
     * 响应元数据
     */
    private int s;

    private String m;

    public CommonResponse success(Object data){
        this.data = data;
        this.s = META_SUCCESS;
        this.m = "";
        return this;
    }

    public CommonResponse success(Object data,Page page){
        this.data = data;
        this.s = META_SUCCESS;
        this.m = "";
        this.page = page;
        return this;
    }


    public CommonResponse success(Object data,String m){
        this.data = data;
        this.s = META_SUCCESS;
        this.m = m;
        return this;
    }

    public CommonResponse error(String m){
        this.s = META_ERROR;
        this.m = m;
        return this;
    }
    public CommonResponse error(String m,int s){
        this.s = s;
        this.m = m;
        return this;
    }
    public CommonResponse error(ResultCode m){
        this.s = m.getCode();
        this.m = m.getMessage();
        return this;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public int getS() {
        return s;
    }

    public void setS(int s) {
        this.s = s;
    }

    public String getM() {
        return m;
    }

    public void setM(String m) {
        this.m = m;
    }
}

