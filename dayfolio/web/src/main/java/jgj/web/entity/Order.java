package jgj.web.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orders_seq")
    @SequenceGenerator(name = "orders_seq", sequenceName = "ORDERS_SEQ", allocationSize = 1)
    private Long orderSeq;

    private Long memberSeq;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Builder
    public Order(Long memberSeq, LocalDateTime orderDate, OrderStatus status) {
        this.memberSeq = memberSeq;
        this.orderDate = orderDate;
        this.status = status;
    }
}
