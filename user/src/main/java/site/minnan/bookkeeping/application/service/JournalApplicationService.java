package site.minnan.bookkeeping.application.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import site.minnan.bookkeeping.domain.vo.JournalListItemVO;
import site.minnan.bookkeeping.domain.vo.QueryVO;
import site.minnan.bookkeeping.infrastructure.enumeration.JournalDirection;
import site.minnan.bookkeeping.userinterface.dto.journal.DeleteJournalDTO;
import site.minnan.bookkeeping.userinterface.dto.journal.QueryJournalDTO;
import site.minnan.bookkeeping.userinterface.dto.journal.UpdateJournalDTO;
import site.minnan.bookkeeping.userinterface.dto.journal.AddJournalDTO;

public interface JournalApplicationService {

    /**
     * 添加记录
     *
     * @param dto
     * @param direction
     */
    void addJournal(AddJournalDTO dto, JournalDirection direction);

    /**
     * 修改记录
     *
     * @param dto
     */
    void modifyJournal(UpdateJournalDTO dto) throws JsonProcessingException;

    /**
     * 删除记录
     *
     * @param dto
     */
    void deleteJournal(DeleteJournalDTO dto) throws JsonProcessingException;

    /**
     * 查询记录
     *
     * @param dto
     * @return
     */
    QueryVO<JournalListItemVO> getJournalList(QueryJournalDTO dto);
}
