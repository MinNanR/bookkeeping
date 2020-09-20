package site.minnan.bookkeeping.userinterface.response;

public enum ResponseCode {

    SUCCESS("000", "成功"),

    INVALID_USER("002","非法用户"),

    FAIL("001","操作失败"),

    INVALID_PARAM("005", "参数非法");

    private String code;

    private String message;

    ResponseCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String code() {
        return this.code;
    }

    public String message() {
        return this.message;
    }


}
