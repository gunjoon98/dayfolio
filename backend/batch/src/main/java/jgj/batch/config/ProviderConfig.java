package jgj.batch.config;

import jgj.batch.provider.kis.KisApiProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
public class ProviderConfig {

    @Bean
    public RestClient kisRestClient(KisApiProperties properties) {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout((int) properties.timeout().toMillis());
        requestFactory.setReadTimeout((int) properties.timeout().toMillis());
        // 운영 트래픽이 커지면 커넥션 풀을 쓰는 HttpComponentsClientHttpRequestFactory로 교체 고려.

        return RestClient.builder()
                .baseUrl(properties.baseUrl())
                .requestFactory(requestFactory)
                .defaultHeader("content-type", "application/json; charset=utf-8")
                .build();
    }
}
