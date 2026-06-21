package jgj.common.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_item_seq")
    @SequenceGenerator(name = "order_item_seq", sequenceName = "ORDER_ITEM_SEQ", allocationSize = 1)
    private Long orderItemSeq;

    private Long memberSeq;

    private Long orderSeq;

    private int orderPrice;

    private int count;

    @Builder
    public OrderItem(Long memberSeq, Long orderSeq, int orderPrice, int count) {
        this.memberSeq = memberSeq;
        this.orderSeq = orderSeq;
        this.orderPrice = orderPrice;
        this.count = count;
    }
}
