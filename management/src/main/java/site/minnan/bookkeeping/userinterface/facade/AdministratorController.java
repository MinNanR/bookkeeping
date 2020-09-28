package site.minnan.bookkeeping.userinterface.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import site.minnan.bookkeeping.aplication.service.AdministratorApplicationService;
import site.minnan.bookkeeping.domain.vo.QueryVO;
import site.minnan.bookkeeping.domain.vo.auth.AdministratorVO;
import site.minnan.bookkeeping.domain.vo.auth.JwtUser;
import site.minnan.bookkeeping.infrastructure.annocation.OperateLog;
import site.minnan.bookkeeping.infrastructure.annocation.Operation;
import site.minnan.bookkeeping.infrastructure.exception.EntityNotExistException;
import site.minnan.bookkeeping.infrastructure.exception.EntityAlreadyExistException;
import site.minnan.bookkeeping.userinterface.dto.auth.*;
import site.minnan.bookkeeping.userinterface.response.ResponseEntity;

import javax.validation.Valid;

@RestController
@RequestMapping("management/administrator")
public class AdministratorController {

    @Autowired
    private AdministratorApplicationService administratorApplicationService;

    /**
     * 创建管理员
     *
     * @param dto
     * @return
     */
    @OperateLog(operation = Operation.ADD, module = "管理员", content = "添加管理员")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("createAdministrator")
    public ResponseEntity<?> createAdministrator(@RequestBody @Valid AddAdministratorDTO dto) {
        try {
            administratorApplicationService.createAdministrator(dto);
            return ResponseEntity.success();
        } catch (EntityAlreadyExistException e) {
            return ResponseEntity.fail("用户名已存在");
        }
    }


    /**
     * 更新用户信息
     *
     * @param dto
     * @return
     */
    @OperateLog(operation = Operation.UPDATE, module = "管理员",content = "修改管理员信息")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("updateAdministrator")
    public ResponseEntity<?> updateAdministrator(@RequestBody UpdateAdministratorDTO dto) {
        if (dto.getId() == null) {
            JwtUser principal = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            dto.setId(principal.getId());
        }
        try {
            administratorApplicationService.updateAdministrator(dto);
            return ResponseEntity.success();
        } catch (EntityNotExistException e) {
            return ResponseEntity.fail("用户不存在");
        }
    }

    /**
     * 修改密码
     *
     * @param dto
     * @return
     */
    @OperateLog(operation = Operation.UPDATE, module = "管理员", content = "修改管理员密码")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("changePassword")
    public ResponseEntity<?> updatePassword(@RequestBody @Valid UpdatePasswordDTO dto) {
        try {
            JwtUser user = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            dto.setId(user.getId());
            administratorApplicationService.changePassword(dto);
            return ResponseEntity.success();
        } catch (EntityNotExistException | BadCredentialsException e) {
            return ResponseEntity.fail(e.getMessage());
        }
    }

    /**
     * 查询用户
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("getAdministratorList")
    public ResponseEntity<QueryVO<AdministratorVO>> getAdministratorList(@RequestBody @Valid GetAdministratorListDTO dto) {
        QueryVO<AdministratorVO> vo = administratorApplicationService.getAdministratorList(dto);
        return ResponseEntity.success(vo);
    }

    /**
     * 删除用户
     *
     * @param dto
     * @return
     */
    @OperateLog(operation = Operation.DELETE, module = "管理员", content = "删除管理员")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("deleteAdministrator")
    public ResponseEntity<?> deleteAdministrator(@RequestBody @Valid DeleteAdministratorDTO dto) {
        try {
            administratorApplicationService.deleteAdministrator(dto);
            return ResponseEntity.success();
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.fail("该id用户不存在");
        }
    }
}
