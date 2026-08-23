package jgj.common.repository;

import jgj.common.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, Long> {
    Optional<Stock> findByCode(String code);

    Optional<Stock> findByIdAndUseYnTrue(Long id);
}
