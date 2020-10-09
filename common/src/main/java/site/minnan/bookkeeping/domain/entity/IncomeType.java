package site.minnan.bookkeeping.domain.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Table(name = "dim_income_type")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class IncomeType {

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

    public static IncomeType of(String typeName, Integer updateUserId){
        return new IncomeType( null, typeName, Timestamp.from(Instant.now()), updateUserId);
    }

    public void changeTypeName(String typeName, Integer updateUserId){
        this.typeName = typeName;
        this.updateUserId = updateUserId;
        this.updateTime = Timestamp.from(Instant.now());
    }
}