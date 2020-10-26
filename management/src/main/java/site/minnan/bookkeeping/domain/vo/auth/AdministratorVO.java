package site.minnan.bookkeeping.domain.vo.auth;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import site.minnan.bookkeeping.domain.aggreates.CustomUser;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
public class AdministratorVO implements Serializable {

    private static final long serialVersionUID = -5809113276822733432L;

    private Integer id;

    private String username;

    private String nickName;

    private String role;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Timestamp createTime;

    public enum RoleEnum{
        ADMIN("管理员");

        private final String roleName;

        RoleEnum(String roleName){
            this.roleName = roleName;
        }

        public String roleName(){
            return this.roleName;
        }

        @Override
        public String toString() {
            return roleName();
        }
    };

    public AdministratorVO(Integer id, String username,String nickName, String role, Timestamp createTime){
        this.id = id;
        this.username = username;
        this.nickName = nickName;
        this.role = RoleEnum.valueOf(role).roleName();
        this.createTime = createTime;
    }

    public AdministratorVO(CustomUser administrator){
        this.id = administrator.getId();
        this.username = administrator.getUsername();
        this.nickName = administrator.getNickName();
        this.role = RoleEnum.valueOf(administrator.getRole().name()).roleName();
        this.createTime = administrator.getCreateTime();
    }
}
