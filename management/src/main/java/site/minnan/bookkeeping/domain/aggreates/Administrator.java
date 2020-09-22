package site.minnan.bookkeeping.domain.aggreates;

import com.google.common.base.Objects;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "administrator")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class Administrator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "username", length = 50, nullable = false)
    private String username;

    @Column(name = "password", length = 64, nullable = false)
    private String password;


    @Setter
    @Column(name = "role", length = 20)
    private String role;

    public static Administrator of(String username, String password,  String role) {
        return new Administrator(null, username, password, role);
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
}
