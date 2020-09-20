package site.minnan.bookkeeping.domain.vo.auth;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class JwtUser implements UserDetails {

    private static final long serialVersionUID = 8143267611621797072L;

    /**
     * 用户id
     */
    private Integer id;

    /**
     * 用户登陆名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 权限
     */
    private Collection<? extends GrantedAuthority> authorities;

    /**
     * 是否启用
     */
    private Boolean enabled;

    public static JwtUser of(Integer id, String username, String password, String authorities, Boolean enabled){
        return new JwtUser(id, username, password, mapToGrantedAuthorities(authorities), enabled);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<String>
                                                                   authorities) {
        return authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(String authority) {
        return Collections.singletonList(new SimpleGrantedAuthority(authority));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
