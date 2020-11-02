package site.minnan.bookkeeping.application.service;

import site.minnan.bookkeeping.infrastructure.enumeration.JournalDirection;
import site.minnan.bookkeeping.userinterface.dto.journal.AddJournalDTO;

public interface JournalApplicationService {

    /**
     * 添加记录
     *
     * @param dto
     * @param direction
     */
    void addJournal(AddJournalDTO dto, JournalDirection direction);
}
