package site.minnan.bookkeeping.domain.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.minnan.bookkeeping.domain.entity.JournalType;
import site.minnan.bookkeeping.domain.repository.JournalTypeRepository;
import site.minnan.bookkeeping.domain.service.JournalTypeService;
import site.minnan.bookkeeping.infrastructure.enumeration.JournalDirection;
import site.minnan.bookkeeping.infrastructure.exception.EntityAlreadyExistException;
import site.minnan.bookkeeping.infrastructure.exception.EntityNotExistException;

import javax.persistence.criteria.Predicate;
import java.util.Optional;

@Service
public class JournalTypeServiceImpl implements JournalTypeService {

    @Autowired
    private JournalTypeRepository journalTypeRepository;

    /**
     * 添加流水类型类型
     *
     * @param typeName
     * @param userId
     * @param journalDirection
     * @throws EntityAlreadyExistException 名称已存在
     */
    @Override
    public void addJournalType(String typeName, Integer userId, JournalDirection journalDirection) throws EntityAlreadyExistException {
        Optional<JournalType> journalType = journalTypeRepository.findOne((root, query, criteriaBuilder) -> {
            Predicate conjunction = criteriaBuilder.conjunction();
            conjunction.getExpressions().add(criteriaBuilder.equal(root.get("typeName"), typeName));
            conjunction.getExpressions().add(criteriaBuilder.equal(root.get("journalDirection"), journalDirection));
            return conjunction;
        });
        if (journalType.isPresent()) {
            throw new EntityAlreadyExistException("类型已存在");
        }
        JournalType newType = JournalType.of(typeName, userId, journalDirection);
        journalTypeRepository.save(newType);
    }

    /**
     * 修改流水类型
     *  @param id
     * @param newTypeName
     * @param userId
     * @param journalDirection
     */
    @Override
    public void updateJournalType(Integer id, String newTypeName, Integer userId, JournalDirection journalDirection) throws EntityNotExistException, EntityAlreadyExistException {
        JournalType journalType = journalTypeRepository.findById(id).orElseThrow(() -> new EntityNotExistException(
                "类型不存在"));
        Optional<JournalType> check = journalTypeRepository.findOne((root, query, criteriaBuilder) -> {
            Predicate conjunction = criteriaBuilder.conjunction();
            conjunction.getExpressions().add(criteriaBuilder.equal(root.get("typeName"), newTypeName));
            conjunction.getExpressions().add(criteriaBuilder.equal(root.get("journalDirection"), journalDirection));
            return conjunction;
        });
        if (check.isPresent()) {
            throw new EntityAlreadyExistException("类型已存在");
        }
        journalType.changeTypeName(newTypeName, userId);
        journalTypeRepository.save(journalType);
    }
}
