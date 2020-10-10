package site.minnan.bookkeeping.domain.vo.user;

import cn.hutool.core.date.DateUtil;
import lombok.Data;
import site.minnan.bookkeeping.domain.aggreates.CustomUser;

@Data
public class UserVO {

    private Integer id;

    private String username;

    private String nickName;

    private String createTime;

    private String userType;

    public UserVO(CustomUser user){
        this.id = user.getId();
        this.username = user.getUsername();
        this.nickName = user.getNickName();
        this.createTime = DateUtil.format(user.getCreateTime(), "yyyy-MM-dd HH:mm:ss");
        this.userType = user.getUserType();
    }
}
