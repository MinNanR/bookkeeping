package site.minnan.bookkeeping.domain.repository;

import cn.hutool.core.util.StrUtil;
import org.hibernate.criterion.CriteriaQuery;
import org.springframework.data.jpa.domain.Specification;

public class SpecificationGenerator {

    /**
     * 等值查询
     *
     * @param property
     * @param param
     * @return
     */
    public static<T> Specification<T> equal(String property, String param) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(property), param);
    }

    /**
     * 模糊查询
     *
     * @param property
     * @param param
     * @return
     */
    public static<T> Specification<T> like(String property, String param) {
        String formattedParam = StrUtil.format("%{}%", param);
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(property), formattedParam);
    }

    /**
     * 左模糊查询
     *
     * @param property
     * @param param
     * @return
     */
    public static<T> Specification<T> startWith(String property, String param) {
        String formattedParam = StrUtil.format("{}%", param);
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(property), formattedParam);
    }

    /**
     * 右模糊查询
     *
     * @param property
     * @param param
     * @return
     */
    public static<T> Specification<T> endWith(String property, String param) {
        String formattedParam = StrUtil.format("%{}", param);
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(property), formattedParam);
    }

    /**
     * 大于某值
     * @param property
     * @param param
     * @param <Y>
     * @return
     */
    public static<Y extends Comparable<? super Y>, T> Specification<T> graterThan(String property, Y param) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThan(root.get(property), param);
    }

    /**
     * 小于某值
     * @param property
     * @param param
     * @param <Y>
     * @return
     */
    public static <Y extends Comparable<? super Y>, T> Specification<T> lessThan(String property, Y param) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThan(root.get(property), param);
    }

    /**
     * 在两值之间
     * @param property
     * @param start
     * @param end
     * @param <Y>
     * @return
     */
    public static <Y extends Comparable<? super Y>, T> Specification<T> between(String property, Y start, Y end) {
        assert (start.compareTo(end) <= 0);
        return (root, query, criteriaBuilder) -> criteriaBuilder.between(root.get(property), start, end);
    }

}
