package site.minnan.bookkeeping.userinterface.dto;

import com.google.common.base.Objects;
import lombok.Data;

import java.util.Optional;

@Data
public class OptionalDTO {
    Optional<String> nickName;
}
