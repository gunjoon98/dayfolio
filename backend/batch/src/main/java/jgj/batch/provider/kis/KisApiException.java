package jgj.batch.provider.kis;

/**
 * 한국투자증권 Open API 호출 과정에서 발생하는 예외.
 * 통신 오류, 응답 파싱 실패, API 자체 오류 응답(rt_cd != 0)을 모두 이 예외로 감싼다.
 */
public class KisApiException extends RuntimeException {

    public KisApiException(String message) {
        super(message);
    }

    public KisApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
