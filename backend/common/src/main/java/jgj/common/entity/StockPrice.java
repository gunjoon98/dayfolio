package jgj.common.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"seq_stock", "base_date"}))
public class StockPrice extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_stock_price")
    @SequenceGenerator(name = "seq_stock_price", sequenceName = "SEQ_STOCK_PRICE", allocationSize = 1)
    private Long seqStockPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seq_stock")
    private Stock seqStock;

    private LocalDate baseDate;

    private BigDecimal openPrice;

    private BigDecimal highPrice;

    private BigDecimal lowPrice;

    private BigDecimal closePrice;

    private BigDecimal changeValue;

    private BigDecimal changeRate;

    private Long volume;

    @Builder
    public StockPrice(Stock seqStock, LocalDate baseDate, BigDecimal openPrice,
                       BigDecimal highPrice, BigDecimal lowPrice, BigDecimal closePrice,
                       BigDecimal changeValue, BigDecimal changeRate, Long volume) {
        this.seqStock = seqStock;
        this.baseDate = baseDate;
        this.openPrice = openPrice;
        this.highPrice = highPrice;
        this.lowPrice = lowPrice;
        this.closePrice = closePrice;
        this.changeValue = changeValue;
        this.changeRate = changeRate;
        this.volume = volume;
    }
}
