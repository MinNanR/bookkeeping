package site.minnan.bookkeeping.domain.service;

import site.minnan.bookkeeping.domain.aggreates.Journal;
import site.minnan.bookkeeping.domain.vo.journal.JournalVO;

public interface JournalService {

    /**
     * 将流水记录组装成值对象
     * @param journal
     * @return
     */
    JournalVO assembleJournalVO(Journal journal);
}
