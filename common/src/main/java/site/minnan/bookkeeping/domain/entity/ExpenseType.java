package site.minnan.bookkeeping.domain.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Table(name = "dim_expense_type")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ExpenseType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "type_name", columnDefinition = "varchar(50) comment '类型名称'")
    private String typeName;

    @Column(name = "update_time", columnDefinition = "TIMESTAMP comment '创建时间'")
    private Timestamp updateTime;

    @Column(name = "update_user_id", columnDefinition ="int comment '创建人'")
    private Integer updateUserId;

    public static ExpenseType of(String typeName, Integer updateUserId){
        return new ExpenseType(null, typeName, Timestamp.from(Instant.now()), updateUserId);
    }

    public void changeTypeName(String typeName){
        this.typeName = typeName;
    }
}
