package site.minnan.bookkeeping.application.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import site.minnan.bookkeeping.infrastructure.enumeration.JournalDirection;
import site.minnan.bookkeeping.infrastructure.exception.EntityNotExistException;
import site.minnan.bookkeeping.userinterface.dto.*;

public interface RecordService {
    /**
     * 添加流水记录
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
    void modifyJournal(ModifyJournalDTO dto, JournalDirection direction) throws EntityNotExistException, JsonProcessingException;

    /**
     * 删除支出记录
     *
     * @param dto
     */
    void deleteExpense(DeleteExpenseDTO dto) throws EntityNotExistException;

    /**
     * 删除收入记录
     *
     * @param dto
     * @throws EntityNotExistException
     */
    void deleteIncome(DeleteIncomeDTO dto) throws EntityNotExistException;
}
