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

    public static class UserTypeConverter implements AttributeConverter<UserType, String> {
        /**
         * Converts the value stored in the entity attribute into the
         * data representation to be stored in the database.
         *
         * @param attribute the entity attribute value to be converted
         * @return the converted data to be stored in the database
         * column
         */
        @Override
        public String convertToDatabaseColumn(UserType attribute) {

            return attribute == null ? null : attribute.typeName();
        }

        /**
         * Converts the data stored in the database column into the
         * value to be stored in the entity attribute.
         * Note that it is the responsibility of the converter writer to
         * specify the correct <code>dbData</code> type for the corresponding
         * column for use by the JDBC driver: i.e., persistence providers are
         * not expected to do such type conversion.
         *
         * @param dbData the data from the database column to be
         *               converted
         * @return the converted value to be stored in the entity
         * attribute
         */
        @Override
        public UserType convertToEntityAttribute(String dbData) {
            return dbData == null ? null : UserType.fromTypeName(dbData);
        }
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
