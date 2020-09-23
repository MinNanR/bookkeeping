package site.minnan.bookkeeping.userinterface.dto;

import lombok.Data;

import java.util.Optional;

@Data
public class UpdateAdministratorDTO {

    Integer id;

    Optional<String> nickName;
}
