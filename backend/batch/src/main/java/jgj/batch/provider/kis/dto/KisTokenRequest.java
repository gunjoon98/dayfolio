package jgj.batch.provider.kis.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * KIS 접근토큰발급 요청
 */
public record KisTokenRequest(
        @JsonProperty("grant_type") String grantType,
        String appkey,
        String appsecret
) {

    public static KisTokenRequest of(String appKey, String appSecret) {
        return new KisTokenRequest("client_credentials", appKey, appSecret);
    }
}
