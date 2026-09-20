package jgj.batch.provider.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 특정 시세 제공사(KIS 등)의 원본 응답을 가공한, 호출부가 사용할 벤더 중립 반환 모델.
 * common 모듈의 엔티티/Command에 의존하지 않는다 — seqStock 매핑 등 도메인 결합은
 * 이 provider를 호출하는 배치 Job(Service) 쪽에서 담당한다.
 */
public record DailyPriceResult(
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

