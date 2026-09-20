package jgj.batch.provider.kis;

import jgj.batch.provider.kis.dto.KisTokenRequest;
import jgj.batch.provider.kis.dto.KisTokenResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.time.Instant;
import java.util.concurrent.locks.ReentrantLock;

/**
 * KIS 토큰(access_token)을 발급받고 메모리에 캐싱
 * KIS 토큰의 만료 임박 전까지는 재발급 요청 하지 않음
 * KIS 토큰의 유효기간은 24시간이며 (1일 1회발급 원칙), 갱신발급주기는 6시간
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class KisOAuthTokenProvider {

    // 실제 만료 시간보다 일찍 만료 시간을 설정하기 위함
    private static final long EXPIRY_BUFFER_SECONDS = 300;

    private final RestClient kisRestClient;
    private final KisApiProperties properties;

    private final ReentrantLock lock = new ReentrantLock();
    private volatile String cachedToken;
    private volatile Instant expiresAt = Instant.MIN;

    /**
     * 유효한 접근토큰을 반환한다. 캐시된 토큰이 만료됐거나 없으면 내부에서 재발급한다.
     */
    public String getAccessToken() {
        if (isValid()) {
            return cachedToken;
        }

        lock.lock();
        try {
            if (isValid()) {
                return cachedToken;
            }
            refreshToken();
            return cachedToken;
        } finally {
            lock.unlock();
        }
    }

    private boolean isValid() {
        return cachedToken != null && Instant.now().isBefore(expiresAt);
    }

    private void refreshToken() {
        try {
            KisTokenResponse response = kisRestClient.post()
                    .uri("/oauth2/tokenP")
                    .body(KisTokenRequest.of(properties.appKey(), properties.appSecret()))
                    .retrieve()
                    .body(KisTokenResponse.class);

            if (response == null || response.accessToken() == null) {
                throw new KisApiException("KIS 토큰 응답이 비어있습니다.");
            }

            this.cachedToken = response.accessToken();
            this.expiresAt = Instant.now().plusSeconds(response.expiresIn() - EXPIRY_BUFFER_SECONDS);
            log.info("KIS access token issued. expiresAt={}", expiresAt);
        } catch (RestClientException e) {
            throw new KisApiException("KIS 토큰 발급에 실패했습니다.", e);
        }
    }
}
