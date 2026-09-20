package jgj.batch.provider.kis.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * KIS 접근토큰발급 응답
 */
public record KisTokenResponse(
        @JsonProperty("access_token") String accessToken,
        @JsonProperty("token_type") String tokenType,
        @JsonProperty("expires_in") Long expiresIn
) {
}
