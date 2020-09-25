package site.minnan.bookkeeping.domain.vo.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class GetAdministratorListVO {

    List<AdministratorVO> administratorList;

    Long totalCount;
}
