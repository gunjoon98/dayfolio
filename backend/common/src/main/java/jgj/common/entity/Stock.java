package jgj.common.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Stock extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_stock")
    @SequenceGenerator(name = "seq_stock", sequenceName = "SEQ_STOCK", allocationSize = 1)
    private Long seqStock;

    private String code;

    private String name;

    @Enumerated(EnumType.STRING)
    private AssetType type;

    @Enumerated(EnumType.STRING)
    private MarketCountry country;

    private Integer order;

    private Boolean useYn;

    @Builder
    public Stock(String code, String name, AssetType type,
                 MarketCountry country, Integer order, Boolean useYn) {
        this.code = code;
        this.name = name;
        this.type = type;
        this.country = country;
        this.order = order;
        this.useYn = useYn;
    }
}