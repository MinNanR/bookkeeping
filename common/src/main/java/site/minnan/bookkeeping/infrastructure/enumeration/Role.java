package site.minnan.bookkeeping.infrastructure.enumeration;

public enum Role {

    ADMIN("管理员"),

    USER("用户");

    private String roleName;

    Role(String roleName){
        this.roleName = roleName;
    }
}
