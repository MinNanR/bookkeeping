package site.minnan.bookkeeping.domain.aggreates;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.awt.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Table(name = "dim_currency")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Currency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "code", columnDefinition = "varchar(20) comment '货币代码'")
    private String code;

    @Column(name = "name", columnDefinition = "varchar(20) comment '货币名称'")
    private String name;

    @Column(name = "update_user_id", columnDefinition = "int comment '更新人'")
    private Integer updateUserId;

    @Column(name = "update_time", columnDefinition = "timestamp comment '更新时间'")
    private Timestamp updateTime;

    @Transient
    private BigDecimal exchangeRate;

    public static Currency of(String code, String name, Integer updateUserId){
        return new Currency(null ,code, name, updateUserId, Timestamp.from(Instant.now()), null);
    }

    public Currency(Integer id, String name){
        this.id = id;
        this.name = name;
    }

    public void changeName(String name, Integer updateUserId){
        this.name  = name;
        this.updateUserId = updateUserId;
        this.updateTime = Timestamp.from(Instant.now());
    }

    public void changeCode(String code, Integer updateUserId){
        this.code = code;
        this.updateUserId = updateUserId;
        this.updateTime = Timestamp.from(Instant.now());
    }
}
