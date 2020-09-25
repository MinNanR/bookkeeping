package site.minnan.bookkeeping.domain.aggreates;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "log")
@Entity
public class OperationLog {

    /**
     * 主键id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 操作者id
     */
    @Column(name = "user_id", columnDefinition = "varchar(50) comment '操作者id'")
    private Integer userId;

    /**
     * 操作用户名
     */
    @Column(name = "username", columnDefinition = "varchar(50) comment '操作者用户名'")
    private String username;

    /**
     * 所进行的操作
     */
    @Column(name = "operation", columnDefinition = "varchar(50) comment '操作类型'")
    private String operation;

    /**
     * 模块
     */
    @Column(name = "module", columnDefinition = "varchar(50) comment '模块'")
    private String module;

    /**
     * 操作内容
     */
    @Column(name = "operate_content", columnDefinition = "varchar(50) comment '操作内容'")
    private String operateContent;

    /**
     * 请求的路径
     */
    @Column(name = "request_uri", columnDefinition = "varchar(50) comment '请求路径'")
    private String requestUri;


    /**
     * 登录IP
     */
    @Setter
    @Column(name = "ip", columnDefinition = "varchar(50) comment '登录ip'")
    private String ip;

    /**
     * 操作时间
     */
    @Setter
    @Column(name = "create_time", columnDefinition = "TIMESTAMP comment '操作时间'")
    private Timestamp createTime;

    public static OperationLog of(Integer userId, String username, String operation, String module, String operateContent,
                                  String requestUri,
                                  String ip, Timestamp createTime){
        return new OperationLog(null, userId, username, operation,module, operateContent, requestUri, ip ,createTime);
    }
}
