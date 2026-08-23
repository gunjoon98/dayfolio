package jgj.common.dto;

import jgj.common.entity.StockPrice;

import java.math.BigDecimal;
import java.time.LocalDate;

public record StockPriceResult(
        Long seqStockPrice,
        Long seqStock,
        LocalDate baseDate,
        BigDecimal openPrice,
        BigDecimal highPrice,
        BigDecimal lowPrice,
        BigDecimal closePrice,
        BigDecimal changeValue,
        BigDecimal changeRate,
        Long volume
) {
    public static StockPriceResult from(StockPrice stockPrice) {
        return new StockPriceResult(
                stockPrice.getSeqStockPrice(),
                stockPrice.getSeqStock().getSeqStock(),
                stockPrice.getBaseDate(),
                stockPrice.getOpenPrice(),
                stockPrice.getHighPrice(),
                stockPrice.getLowPrice(),
                stockPrice.getClosePrice(),
                stockPrice.getChangeValue(),
                stockPrice.getChangeRate(),
                stockPrice.getVolume()
        );
    }
}
