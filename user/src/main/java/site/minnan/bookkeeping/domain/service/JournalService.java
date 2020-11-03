package site.minnan.bookkeeping.domain.service;

import site.minnan.bookkeeping.domain.aggreates.Journal;
import site.minnan.bookkeeping.domain.vo.JournalListItemVO;

import java.util.List;

public interface JournalService {

    /**
     * 装配流水记录
     * @param journalList
     * @return
     */
    List<JournalListItemVO> assembleJournal(List<Journal> journalList);
}
