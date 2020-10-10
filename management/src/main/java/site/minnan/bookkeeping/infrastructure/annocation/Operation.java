package site.minnan.bookkeeping.infrastructure.annocation;

import site.minnan.bookkeeping.domain.entity.UserType;

import javax.persistence.AttributeConverter;

public enum Operation {

    ADD("添加"),

    UPDATE("修改"),

    DELETE("删除"),

    DOWNLOAD("下载"),

    LOGIN("登录"),

    LOGOUT("登出");

    private final String operationName;

    Operation(String operationName){
        this.operationName = operationName;
    }

    public String operationName(){
        return operationName;
    }

    @Override
    public String toString() {
        return this.operationName;
    }
}
