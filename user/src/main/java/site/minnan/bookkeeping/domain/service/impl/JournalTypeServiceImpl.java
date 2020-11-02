package site.minnan.bookkeeping.domain.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.minnan.bookkeeping.domain.aggreates.JournalType;
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
    private JournalTypeRepository repository;

    /**
     * 创建流水类型
     *
     * @param typeName
     * @param parentId
     * @param userId
     * @param direction
     */
    @Override
    public void createJournalType(String typeName, Integer parentId, Integer userId, JournalDirection direction) {
        JournalType journalType;
        if (parentId != null && parentId != 0) {
            Optional<JournalType> parentType = repository.findOne((root, query, criteriaBuilder) -> {
                Predicate conjunction = criteriaBuilder.conjunction();
                conjunction.getExpressions().add(criteriaBuilder.equal(root.get("id"), parentId));
                conjunction.getExpressions().add(criteriaBuilder.equal(root.get("userId"), userId));
                return conjunction;
            });
            if (!parentType.isPresent()) {
                throw new EntityNotExistException("父级分类不存在");
            }
            Optional<JournalType> check = repository.findOne((root, query, criteriaBuilder) -> {
                Predicate conjunction = criteriaBuilder.conjunction();
                conjunction.getExpressions().add(criteriaBuilder.equal(root.get("typeName"), typeName));
                conjunction.getExpressions().add(criteriaBuilder.equal(root.get("journalDirection"), direction));
                conjunction.getExpressions().add(criteriaBuilder.equal(root.get("parentId"), parentId));
                return conjunction;
            });
            if (check.isPresent()) {
                throw new EntityAlreadyExistException("同名分类已存在");
            }
            journalType = JournalType.secondLevel(typeName, direction, parentId, userId);
        } else {
            Optional<JournalType> check = repository.findOne((root, query, criteriaBuilder) -> {
                Predicate conjunction = criteriaBuilder.conjunction();
                conjunction.getExpressions().add(criteriaBuilder.equal(root.get("typeName"), typeName));
                conjunction.getExpressions().add(criteriaBuilder.equal(root.get("journalDirection"), direction));
                conjunction.getExpressions().add(criteriaBuilder.equal(root.get("parentId"), 0));
                conjunction.getExpressions().add(criteriaBuilder.equal(root.get("userId"), userId));
                return conjunction;
            });
            if (check.isPresent()) {
                throw new EntityAlreadyExistException("同名分类已存在");
            }
            journalType = JournalType.firstLevel(typeName, direction, userId);
        }
        repository.save(journalType);
    }
}
