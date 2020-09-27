package site.minnan.bookkeeping.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * 通用查询结果
 *
 * @param <T>
 */
@Data
@AllArgsConstructor
public class QueryVO<T> {

    private List<T> list;

    private Long totalCount;

}
