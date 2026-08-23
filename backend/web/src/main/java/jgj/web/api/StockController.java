package jgj.web.api;

import jgj.common.dto.StockCommand;
import jgj.common.dto.StockPriceResult;
import jgj.common.dto.StockResult;
import jgj.common.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/stocks")
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;

    @PostMapping
    public Response<StockResult> saveStock(@RequestBody StockCommand.Save command) {
        return Response.ok(stockService.saveStock(command));
    }

    @GetMapping
    public Response<List<StockResult>> findAllStock() {
        return Response.ok(stockService.findAllStock());
    }

    @GetMapping("/{seqStock}")
    public Response<StockResult> findOneStock(@PathVariable Long seqStock) {
        return Response.ok(stockService.findOneStock(seqStock));
    }

    @PutMapping("/{seqStock}")
    public Response<StockResult> updateStock(@PathVariable Long seqStock, @RequestBody StockCommand.Update command) {
        return Response.ok(stockService.updateStock(seqStock, command));
    }

    @DeleteMapping("/{seqStock}")
    public Response<Void> deleteStock(@PathVariable Long seqStock) {
        stockService.deleteStock(seqStock);
        return Response.ok(null);
    }

    @PostMapping("/prices")
    public Response<Void> saveStockPrice(@RequestBody StockCommand.CreatePrice command) {
        stockService.saveStockPrice(command);
        return Response.ok(null);
    }

    @PostMapping("/prices/bulk")
    public Response<Void> saveStockPriceList(@RequestBody List<StockCommand.CreatePrice> commands) {
        stockService.saveStockPriceList(commands);
        return Response.ok(null);
    }

    @GetMapping("/prices")
    public Response<List<StockPriceResult>> findAllStockPrice() {
        return Response.ok(stockService.findAllStockPrice());
    }
}
