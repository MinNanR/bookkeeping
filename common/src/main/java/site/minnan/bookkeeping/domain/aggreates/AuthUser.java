package site.minnan.bookkeeping.domain.aggreates;

import lombok.*;
import site.minnan.bookkeeping.infrastructure.enumeration.Role;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Table(name = "aut_user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class AuthUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "username", columnDefinition = "varchar(50) comment '用户名'")
    private String username;

    @Column(name = "password", columnDefinition = "varchar(100) comment '密码'")
    private String password;

    @Column(name = "role", columnDefinition = "varchar(20) comment '角色'")
    @Enumerated(value = EnumType.STRING)
    private Role role;

    @Column(name = "create_time", columnDefinition = "timestamp comment '创建时间'")
    private Timestamp createTime;

    @Column(name = "nick_name", columnDefinition = "varchar(50) comment '昵称'")
    private String nickName;

    @Column(name = "open_id", columnDefinition = "varchar(32) comment '微信OpenId'")
    private String openId;

    public static AuthUser of(String username, String password, Role role) {
        return new AuthUser(null, username, password, role, Timestamp.from(Instant.now()), username, null);
    }
}
