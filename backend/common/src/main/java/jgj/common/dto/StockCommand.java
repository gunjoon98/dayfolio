package jgj.common.dto;

import jgj.common.entity.AssetType;
import jgj.common.entity.MarketCountry;

import java.math.BigDecimal;
import java.time.LocalDate;

public class StockCommand {

    public record Save(
            String code,
            String name,
            AssetType type,
            MarketCountry country,
            Integer order,
            Boolean useYn
    ) {
    }

    public record Update(
            Boolean useYn
    ) {
    }

    public record CreatePrice(
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
    }
}
