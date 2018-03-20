package com.foresightautomotive.stock.market;

import com.foresightautomotive.portfolio.PortfolioService;
import com.foresightautomotive.repository.StocksRepository;
import com.foresightautomotive.search.SearchData;
import com.foresightautomotive.search.SearchResponse;
import com.foresightautomotive.stock.PublicStock;
import com.foresightautomotive.transactions.BuyRequest;
import com.foresightautomotive.transactions.SellRequest;
import com.foresightautomotive.transactions.TransactionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/market")
public class StockMarketController {

    private StocksRepository stocksRepository;
    private PortfolioService portfolioService;

    @Autowired
    public StockMarketController(StocksRepository stocksRepository, PortfolioService portfolioService){
        this.stocksRepository = stocksRepository;
        this.portfolioService = portfolioService;
    }

    @RequestMapping(method = RequestMethod.POST, path = "search")
    @ResponseBody
    public ResponseEntity<SearchResponse> search(@RequestBody SearchData searchData){
        return ResponseEntity.ok(SearchResponse.builder().stocks(stocksRepository.searchStockBy(searchData.getSearchString())).build());
    }

    @RequestMapping(method = RequestMethod.POST, path = "buy")
    @ResponseBody
    public ResponseEntity<TransactionResponse> buyStock(@RequestBody BuyRequest buyRequest){
        return ResponseEntity.ok(TransactionResponse.builder().funds(portfolioService.buy(buyRequest.getStockSymbol(), buyRequest.getStockQuantity())).build());
    }

    @RequestMapping(method = RequestMethod.POST, path = "sell")
    @ResponseBody
    public ResponseEntity<TransactionResponse> sellStock(@RequestBody SellRequest sellRequest){
        return ResponseEntity.ok(TransactionResponse.builder().funds(portfolioService.sell(sellRequest.getStockSymbol())).build());
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<SearchResponse> stocksInformation(@RequestParam(value="symbol") String[] stocks){
        return ResponseEntity.ok(SearchResponse.builder().stocks(stocksRepository.listBy(stocks)).build());
    }
}
