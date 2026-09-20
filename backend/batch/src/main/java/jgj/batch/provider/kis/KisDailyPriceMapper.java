package jgj.batch.provider.kis;

import jgj.batch.provider.dto.DailyPriceResult;
import jgj.batch.provider.kis.dto.KisDailyPriceResponse;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

final class KisDailyPriceMapper {

    private static final DateTimeFormatter BASE_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd");

    private KisDailyPriceMapper() {
    }

    static List<DailyPriceResult> toResults(KisDailyPriceResponse response) {
        return response.items().stream()
                .map(KisDailyPriceMapper::toResult)
                .toList();
    }

    private static DailyPriceResult toResult(KisDailyPriceResponse.Item item) {
        return new DailyPriceResult(
                LocalDate.parse(item.baseDate(), BASE_DATE_FORMAT),
                new BigDecimal(item.openPrice()),
                new BigDecimal(item.highPrice()),
                new BigDecimal(item.lowPrice()),
                new BigDecimal(item.closePrice()),
                new BigDecimal(item.changeValue()),
                new BigDecimal(item.changeRate()),
                Long.valueOf(item.volume())
        );
    }
}
