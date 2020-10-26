package site.minnan.bookkeeping.application.service;

import site.minnan.bookkeeping.domain.vo.QueryVO;
import site.minnan.bookkeeping.domain.vo.user.UserVO;
import site.minnan.bookkeeping.userinterface.dto.GetUserListDTO;

public interface UserApplicationService {

    QueryVO<UserVO> getUserList(GetUserListDTO dto);
}
