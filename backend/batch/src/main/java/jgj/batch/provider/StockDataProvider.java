package jgj.batch.provider;

import jgj.batch.provider.dto.DailyPriceResult;

import java.time.LocalDate;
import java.util.List;

/**
 * 주식 정보를 제공하는 인터페이스.
 */
public interface StockDataProvider {

    List<DailyPriceResult> fetchDailyPrices(String stockCode, LocalDate from, LocalDate to);
}
