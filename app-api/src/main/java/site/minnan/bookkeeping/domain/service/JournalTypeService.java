package site.minnan.bookkeeping.domain.service;

public interface JournalTypeService {

    /**
     * 根据id查找名称
     * @param id
     * @return
     */
    String mapIdToName(Integer id);
}
