package site.minnan.bookkeeping.domain.aggreates;

import com.google.common.base.Objects;
import lombok.*;

import javax.persistence.*;
import java.util.Optional;

@Entity
@Getter
@Table(name = "auth_administrator")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class Administrator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "username", length = 50, nullable = false, columnDefinition = "comment '用户名'")
    private String username;

    @Column(name = "password", length = 100, nullable = false, columnDefinition = "comment '密码'")
    private String password;

    @Column(name = "nick_name", length = 20, columnDefinition = "comment '昵称'")
    private String nickName;

    @Setter
    @Column(name = "role", length = 20, columnDefinition = "comment '角色'")
    private String role;

    public static Administrator of(String username, String password, String nickName) {
        return new Administrator(null, username, password, nickName, "ADMIN");
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

    public void changeInformation(Optional<String> nickName, Optional<String> password){
        nickName.ifPresent(value -> this.nickName = value);
        password.ifPresent(value -> this.password = value);
    }
}
