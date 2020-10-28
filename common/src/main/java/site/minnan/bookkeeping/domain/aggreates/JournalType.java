package site.minnan.bookkeeping.domain.aggreates;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.minnan.bookkeeping.infrastructure.enumeration.JournalDirection;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Table(name = "dim_journal_type")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class JournalType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "type_name", columnDefinition = "varchar(50) comment '类型名称'")
    private String typeName;

    @Column(name = "create_time", columnDefinition = "timestamp comment '创建时间'")
    private Timestamp createTime;

    @Column(name = "user_id", columnDefinition = "int comment '用户id'")
    private Integer userId;

    @Column(name = "parent_id", columnDefinition = "int comment '父级分类'")
    private Integer parentId;

    @Column(name = "level", columnDefinition = "int comment '等级'")
    private Integer level;

    @Column(name = "journal_direction", columnDefinition = "varchar(10) comment '类型方向'")
    @Enumerated(EnumType.STRING)
    private JournalDirection journalDirection;


    public static JournalType firstLevel(String typeName,JournalDirection journalDirection, Integer userId) {
        return new JournalType(null, typeName, Timestamp.from(Instant.now()), userId, 0, 1, journalDirection);
    }

    public static  JournalType secondLevel(String typeName, JournalDirection journalDirection, Integer parentId,
                                           Integer userId){
        return new JournalType(null ,typeName, Timestamp.from(Instant.now()), userId, parentId, 2, journalDirection);
    }

}
