package jgj.batch.provider.kis;

import jgj.batch.provider.StockDataProvider;
import jgj.batch.provider.dto.DailyPriceResult;
import jgj.batch.provider.kis.dto.KisDailyPriceResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class KisStockDataProvider implements StockDataProvider {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd");
    // 국내주식 일자별 시세 조회 TR ID (실전투자 기준)
    private static final String TR_ID_DAILY_PRICE = "FHKST03010100";

    private final RestClient kisRestClient;
    private final KisOAuthTokenProvider tokenProvider;
    private final KisApiProperties properties;

    @Override
    public List<DailyPriceResult> fetchDailyPrices(String stockCode, LocalDate from, LocalDate to) {
        try {
            KisDailyPriceResponse response = kisRestClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/uapi/domestic-stock/v1/quotations/inquire-daily-itemchartprice")
                            .queryParam("FID_COND_MRKT_DIV_CODE", "J")
                            .queryParam("FID_INPUT_ISCD", stockCode)
                            .queryParam("FID_INPUT_DATE_1", from.format(DATE_FORMAT))
                            .queryParam("FID_INPUT_DATE_2", to.format(DATE_FORMAT))
                            .queryParam("FID_PERIOD_DIV_CODE", "D")
                            .queryParam("FID_ORG_ADJ_PRC", "0")
                            .build())
                    .header("authorization", "Bearer " + tokenProvider.getAccessToken())
                    .header("appkey", properties.appKey())
                    .header("appsecret", properties.appSecret())
                    .header("tr_id", TR_ID_DAILY_PRICE)
                    .header("custtype", "P")
                    .retrieve()
                    .body(KisDailyPriceResponse.class);

            if (response == null) {
                throw new KisApiException("KIS 일별시세 응답이 비어있습니다. code=" + stockCode);
            }
            if (!response.isSuccess()) {
                throw new KisApiException(
                        "KIS 일별시세 조회 실패. code=%s, msg=%s".formatted(stockCode, response.message()));
            }

            return KisDailyPriceMapper.toResults(response);
        } catch (RestClientException e) {
            log.error("KIS 일별시세 호출 중 통신 오류. code={}", stockCode, e);
            throw new KisApiException("KIS 일별시세 조회 중 통신 오류가 발생했습니다. code=" + stockCode, e);
        }
    }
}
