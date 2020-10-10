package site.minnan.bookkeeping.domain.aggreates;

import com.google.common.base.Objects;
import lombok.*;
import site.minnan.bookkeeping.domain.entity.UserType;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

@Entity
@Getter
@Table(name = "auth_user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class CustomUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "username", columnDefinition = "varchar(50) comment '用户名'")
    private String username;

    @Column(name = "password", columnDefinition = "varchar(100) comment '密码'")
    private String password;

    @Setter
    @Column(name = "role", length = 20, columnDefinition = "varchar(20) comment '角色'")
    private String role;

    @Column(name = "create_time", columnDefinition = "timestamp comment '创建时间'")
    private Timestamp createTime;

    @Column(name = "user_type", columnDefinition = "varchar(50) comment '用户类型'")
    private String userType;

    @Column(name = "nick_name", columnDefinition = "varchar(50) comment '昵称'")
    private String nickName;

    public static CustomUser of(String username, String password, String role) {
        return new CustomUser(null, username, password, role, Timestamp.from(Instant.now()), null,
                username);
    }

    public void changeName(Optional<String> nickName) {
        this.nickName = nickName.orElse(this.nickName);
    }

    public void changeUserType(Optional<String> userType) {
        userType.ifPresent(type -> this.userType = type);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomUser user = (CustomUser) o;
        return Objects.equal(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
