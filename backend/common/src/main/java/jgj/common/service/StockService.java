package jgj.common.service;

import jgj.common.dto.StockCommand;
import jgj.common.dto.StockPriceResult;
import jgj.common.dto.StockResult;
import jgj.common.entity.Stock;
import jgj.common.entity.StockPrice;
import jgj.common.repository.StockPriceRepository;
import jgj.common.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StockService {

    private final StockRepository stockRepository;
    private final StockPriceRepository stockPriceRepository;

    @Transactional
    public StockResult saveStock(StockCommand.Save command) {
        Stock stock = Stock.builder()
                .code(command.code())
                .name(command.name())
                .type(command.type())
                .country(command.country())
                .order(command.order())
                .useYn(command.useYn())
                .build();
        return StockResult.from(stockRepository.save(stock));
    }

    @Transactional
    public void saveStockPrice(StockCommand.CreatePrice command) {
        Stock stock = getStock(command.seqStock());
        StockPrice stockPrice = StockPrice.builder()
                .seqStock(stock)
                .baseDate(command.baseDate())
                .openPrice(command.openPrice())
                .highPrice(command.highPrice())
                .lowPrice(command.lowPrice())
                .closePrice(command.closePrice())
                .changeValue(command.changeValue())
                .changeRate(command.changeRate())
                .volume(command.volume())
                .build();
        stockPriceRepository.save(stockPrice);
    }

    @Transactional
    public void saveStockPriceList(List<StockCommand.CreatePrice> commands) {
        List<StockPrice> stockPrices = commands.stream()
                .map(command -> {
                    Stock stock = getStock(command.seqStock());
                    return StockPrice.builder()
                            .seqStock(stock)
                            .baseDate(command.baseDate())
                            .openPrice(command.openPrice())
                            .highPrice(command.highPrice())
                            .lowPrice(command.lowPrice())
                            .closePrice(command.closePrice())
                            .changeValue(command.changeValue())
                            .changeRate(command.changeRate())
                            .volume(command.volume())
                            .build();
                })
                .toList();
        stockPriceRepository.saveAll(stockPrices);
    }

    public StockResult findOneStock(Long seqStock) {
        return StockResult.from(getStock(seqStock));
    }

    public List<StockResult> findAllStock() {
        return stockRepository.findAll().stream()
                .map(StockResult::from)
                .toList();
    }

    public List<StockPriceResult> findAllStockPrice() {
        return stockPriceRepository.findAll().stream()
                .map(StockPriceResult::from)
                .toList();
    }

    @Transactional
    public StockResult updateStock(Long seqStock, StockCommand.Update command) {
        Stock stock = getStock(seqStock);
        stock.changeUseYn(command.useYn());
        return StockResult.from(stock);
    }

    @Transactional
    public void deleteStock(Long seqStock) {
        Stock stock = getStock(seqStock);
        stock.changeUseYn(false);
    }

    private Stock getStock(Long seqStock) {
        return stockRepository.findByIdAndUseYnTrue(seqStock)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 종목입니다: " + seqStock));
    }

    private Stock getStockByCode(String code) {
        return stockRepository.findByCode(code)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 종목입니다: " + code));
    }
}
