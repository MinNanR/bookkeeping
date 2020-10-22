package site.minnan.bookkeeping.domain.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.minnan.bookkeeping.domain.aggreates.Journal;
import site.minnan.bookkeeping.domain.repository.WarehouseRepository;
import site.minnan.bookkeeping.domain.service.JournalService;
import site.minnan.bookkeeping.domain.service.WarehouseService;

@Service
public class JournalServiceImpl implements JournalService {

    @Autowired
    private WarehouseRepository warehouseRepository;

    /**
     * 修改账本
     *
     * @param source
     * @param target
     */
    @Override
    public void changeLedger(Journal source, Journal target) {

    }

    /**
     * 修改金额
     *
     * @param source
     * @param target
     */
    @Override
    public void changeAmount(Journal source, Journal target) {

    }
}
