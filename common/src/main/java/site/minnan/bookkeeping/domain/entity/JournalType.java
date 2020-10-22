package site.minnan.bookkeeping.domain.entity;

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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class JournalType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "type_name", columnDefinition = "varchar(50) comment '类型名称'")
    private String typeName;

    @Column(name = "update_time", columnDefinition = "TIMESTAMP comment '创建时间'")
    private Timestamp updateTime;

    @Column(name = "update_user_id", columnDefinition ="int comment '创建人'")
    private Integer updateUserId;

    @Enumerated(value = EnumType.STRING)
    private JournalDirection journalDirection;

    public static JournalType of(String typeName, Integer updateUserId, JournalDirection journalDirection){
        return new JournalType( null, typeName, Timestamp.from(Instant.now()), updateUserId, journalDirection);
    }

    public void changeTypeName(String typeName, Integer updateUserId){
        this.typeName = typeName;
        this.updateUserId = updateUserId;
        this.updateTime = Timestamp.from(Instant.now());
    }
}
