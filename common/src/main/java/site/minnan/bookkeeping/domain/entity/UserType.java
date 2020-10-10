package site.minnan.bookkeeping.domain.entity;

import org.springframework.security.core.userdetails.User;

import javax.persistence.AttributeConverter;
import java.util.Objects;

public enum UserType {

    STUDENT("学生"),
    WORKER("工作人士"),
    HOMEMAKER("家庭主妇");

    private final String typeName;

    UserType(String typeName) {
        this.typeName = typeName;
    }

    public String typeName() {
        return this.typeName;
    }

    public static UserType fromTypeName(String typeName){
        for (UserType userType : UserType.values()) {
            if (Objects.equals(typeName, userType.typeName())) {
                return userType;
            }
        }
        throw new IllegalArgumentException();
    }
}
