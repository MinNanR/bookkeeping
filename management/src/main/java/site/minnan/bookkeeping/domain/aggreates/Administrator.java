package site.minnan.bookkeeping.domain.aggreates;

import com.google.common.base.Objects;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

@Entity
@Getter
@Table(name = "auth_administrator")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class Administrator {

    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "username", nullable = false, columnDefinition = "varchar(50) comment '用户名'")
    private String username;

    @Column(name = "password", nullable = false, columnDefinition = "varchar(100) comment '密码'")
    private String password;

    @Column(name = "nick_name", columnDefinition = "varchar(20) comment '昵称'")
    private String nickName;

    @Setter
    @Column(name = "role", columnDefinition = "varchar(20) comment '角色'")
    private String role;

    @Column(name = "create_time", columnDefinition = "timestamp comment '创建时间'")
    private Timestamp createTime;

    public static Administrator of(String username, String password, String nickName) {
        return new Administrator(null, username, password, nickName, "ADMIN", Timestamp.from(Instant.now()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Administrator user = (Administrator) o;
        return Objects.equal(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public void changeInformation(Optional<String> nickName, Optional<String> password) {
        nickName.ifPresent(value -> this.nickName = value);
        password.ifPresent(value -> this.password = value);
    }

    public Administrator(Integer id, String username) {
        this.id = id;
        this.username = username;
    }
}
