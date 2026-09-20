package jgj.batch.provider.kis;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@ConfigurationProperties(prefix = "external.kis")
public record KisApiProperties(
        String baseUrl,
        String appKey,
        String appSecret,
        Duration timeout
) {
}
