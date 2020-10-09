package site.minnan.bookkeeping.aplication.service.impl;

import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import site.minnan.bookkeeping.aplication.service.UserApplicationService;
import site.minnan.bookkeeping.domain.aggreates.CustomUser;
import site.minnan.bookkeeping.domain.entity.UserType;
import site.minnan.bookkeeping.domain.repository.CustomUserRepository;
import site.minnan.bookkeeping.domain.repository.SpecificationGenerator;
import site.minnan.bookkeeping.domain.vo.QueryVO;
import site.minnan.bookkeeping.domain.vo.user.UserVO;
import site.minnan.bookkeeping.userinterface.dto.GetUserListDTO;

import javax.persistence.criteria.Predicate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserApplicationServiceImpl implements UserApplicationService {

    @Autowired
    private CustomUserRepository customUserRepository;

    @Override
    public QueryVO<UserVO> getUserList(GetUserListDTO dto) {
        Optional<String> nickNameOptional = Optional.ofNullable(dto.getNickName());
        Optional<String> userTypeOptional = Optional.ofNullable(dto.getUserType());
        Page<CustomUser> customUserPage = customUserRepository.findAll((root, query, criteriaBuilder) -> {
            Predicate conjunction = criteriaBuilder.conjunction();
            nickNameOptional.ifPresent(nickName -> conjunction.getExpressions().add(criteriaBuilder.like(root.get(
                    "nickName"), StrUtil.format("%{}%", nickName))));
            userTypeOptional.ifPresent(userType -> conjunction.getExpressions().add(criteriaBuilder.equal(root.get(
                    "userType"), UserType.valueOf(userType))));
            return conjunction;
        }, PageRequest.of(dto.getPageIndex() - 1, dto.getPageSize(), Sort.by(Sort.Direction.DESC, "createTime")));
        List<UserVO> userVOList = customUserPage.get().map(UserVO::new).collect(Collectors.toList());
        return new QueryVO<>(userVOList, customUserPage.getTotalElements());
    }
}
