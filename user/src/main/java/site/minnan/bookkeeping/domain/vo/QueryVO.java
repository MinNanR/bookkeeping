package site.minnan.bookkeeping.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class QueryVO<T> {

    List<T> list;

    long totalCount;
}
