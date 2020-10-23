package site.minnan.bookkeeping.application.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import site.minnan.bookkeeping.domain.vo.QueryVO;
import site.minnan.bookkeeping.domain.vo.journal.JournalVO;
import site.minnan.bookkeeping.infrastructure.enumeration.JournalDirection;
import site.minnan.bookkeeping.infrastructure.exception.EntityNotExistException;
import site.minnan.bookkeeping.userinterface.dto.journal.AddJournalDTO;
import site.minnan.bookkeeping.userinterface.dto.journal.DeleteJournalDTO;
import site.minnan.bookkeeping.userinterface.dto.journal.GetJournalListDTO;
import site.minnan.bookkeeping.userinterface.dto.journal.ModifyJournalDTO;

public interface RecordService {
    /**
     * 添加流水记录
     *
     * @param dto
     * @param direction
     * @throws EntityNotExistException
     */
    void addJournal(AddJournalDTO dto, JournalDirection direction) throws EntityNotExistException;

    /**
     * 修改支出记录
     *
     * @param dto
     * @throws EntityNotExistException
     */
    void modifyJournal(ModifyJournalDTO dto) throws EntityNotExistException, JsonProcessingException;

    /**
     * 删除支出记录
     *
     * @param dto
     */
    void deleteJournal(DeleteJournalDTO dto) throws EntityNotExistException, JsonProcessingException;

    /**
     * 查询流水记录
     *
     * @param dto
     * @return
     */
    QueryVO<JournalVO> getJournalList(GetJournalListDTO dto);
}
