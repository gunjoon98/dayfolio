package jgj.common.repository;

import jakarta.persistence.EntityManager;
import jgj.common.entity.AssetType;
import jgj.common.entity.MarketCountry;
import jgj.common.entity.Stock;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("common")
@Transactional
class StockRepositoryTest {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    void crud() {
        // Create
        Stock stock = Stock.builder()
                .code("005930")
                .name("삼성전자")
                .type(AssetType.STOCK)
                .country(MarketCountry.KR)
                .order(1)
                .useYn(true)
                .build();
        Stock saved = stockRepository.save(stock);
        assertThat(saved.getSeqStock()).isNotNull();

        // Read
        Stock found = stockRepository.findById(saved.getSeqStock()).orElseThrow();
        assertThat(found.getName()).isEqualTo("삼성전자");

        Optional<Stock> byCode = stockRepository.findByCode("005930");
        assertThat(byCode).isPresent();

        List<Stock> all = stockRepository.findAll();
        assertThat(all).extracting(Stock::getCode).contains("005930");

        // Update — setter 없이 도메인 메서드로 변경 후 더티 체킹으로 반영
        found.changeUseYn(false);
        stockRepository.flush();
        entityManager.clear();

        Stock updated = stockRepository.findById(saved.getSeqStock()).orElseThrow();
        assertThat(updated.getUseYn()).isFalse();

        // Delete
        stockRepository.delete(updated);
        stockRepository.flush();
        assertThat(stockRepository.findById(saved.getSeqStock())).isEmpty();
    }
}
