package site.minnan.bookkeeping.infrastructure.annocation;

public enum Operation {

    ADD("添加"),

    UPDATE("修改"),

    DELETE("删除"),

    DOWNLOAD("下载"),

    LOGIN("登录"),

    LOGOUT("登出");

    private String operationName;

    Operation(String operationName){
        this.operationName = operationName;
    }
}
