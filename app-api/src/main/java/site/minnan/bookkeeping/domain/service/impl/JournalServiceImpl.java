package site.minnan.bookkeeping.domain.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.minnan.bookkeeping.domain.aggreates.Journal;
import site.minnan.bookkeeping.domain.entity.JournalType;
import site.minnan.bookkeeping.domain.repository.WarehouseRepository;
import site.minnan.bookkeeping.domain.service.JournalService;
import site.minnan.bookkeeping.domain.service.JournalTypeService;
import site.minnan.bookkeeping.domain.service.WarehouseService;
import site.minnan.bookkeeping.domain.vo.journal.JournalVO;

@Service
public class JournalServiceImpl implements JournalService {

    @Autowired
    private WarehouseService warehouseService;

    @Autowired
    private JournalTypeService journalTypeService;

    /**
     * 将流水记录组装成值对象
     *
     * @param journal
     * @return
     */
    @Override
    public JournalVO assembleJournalVO(Journal journal) {
        String warehouseName = warehouseService.mapIdToName(journal.getWarehouseId());
        String journalTypeName = journalTypeService.mapIdToName(journal.getJournalTypeId());
        return new JournalVO(journal, warehouseName, journalTypeName);
    }
}
