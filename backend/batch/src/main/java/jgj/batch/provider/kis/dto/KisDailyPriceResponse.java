package jgj.batch.provider.kis.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * 국내주식 일자별 시세(inquire-daily-itemchartprice) 원본 응답.
 * KIS 응답 필드명(snake_case, output2 등)을 그대로 유지해 API 스펙 변화를 이 클래스 안에 가둔다.
 */
public record KisDailyPriceResponse(
        @JsonProperty("rt_cd") String resultCode,
        @JsonProperty("msg_cd") String messageCode,
        @JsonProperty("msg1") String message,
        @JsonProperty("output2") List<Item> items
) {

    public boolean isSuccess() {
        return "0".equals(resultCode);
    }

    public record Item(
            @JsonProperty("stck_bsop_date") String baseDate,
            @JsonProperty("stck_oprc") String openPrice,
            @JsonProperty("stck_hgpr") String highPrice,
            @JsonProperty("stck_lwpr") String lowPrice,
            @JsonProperty("stck_clpr") String closePrice,
            @JsonProperty("prdy_vrss") String changeValue,
            @JsonProperty("prdy_ctrt") String changeRate,
            @JsonProperty("acml_vol") String volume
    ) {
    }
}
