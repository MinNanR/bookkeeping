package site.minnan.bookkeeping.domain.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import site.minnan.bookkeeping.domain.repository.JournalTypeRepository;
import site.minnan.bookkeeping.domain.service.JournalTypeService;

@Service
public class JournalTypeServiceImpl implements JournalTypeService {

    @Autowired
    private JournalTypeRepository journalTypeRepository;

    /**
     * 根据id查找名称
     *
     * @param id
     * @return
     */
    @Override
    @Cacheable("JournalTypeName")
    public String mapIdToName(Integer id) {
        return journalTypeRepository.findJournalTypeNameById(id).orElse("");
    }
}
