package site.minnan.bookkeeping.infrastructure.enumeration;

public enum WarehouseType {

    WECHAT("微信"),
    ALI_PAY("支付宝"),
    BANK_CARD("银行卡"),
    CASH("现金");

    private String name;

    WarehouseType(String name){
        this.name = name;
    }
}
