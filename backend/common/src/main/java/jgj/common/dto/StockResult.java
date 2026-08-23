package jgj.common.dto;

import jgj.common.entity.AssetType;
import jgj.common.entity.MarketCountry;
import jgj.common.entity.Stock;

public record StockResult(
        Long seqStock,
        String code,
        String name,
        AssetType type,
        MarketCountry country,
        Integer order,
        Boolean useYn
) {
    public static StockResult from(Stock stock) {
        return new StockResult(
                stock.getSeqStock(),
                stock.getCode(),
                stock.getName(),
                stock.getType(),
                stock.getCountry(),
                stock.getOrder(),
                stock.getUseYn()
        );
    }
}
