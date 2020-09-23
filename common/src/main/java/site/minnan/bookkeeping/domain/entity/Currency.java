package site.minnan.bookkeeping.domain.entity;

import lombok.Data;
import org.springframework.data.domain.AbstractAggregateRoot;

import java.io.Serializable;

@Data
public class Currency implements Serializable {

    private static final long serialVersionUID = -6862776949477467681L;

    private Integer id;

    private String code;

    private String name;

}
