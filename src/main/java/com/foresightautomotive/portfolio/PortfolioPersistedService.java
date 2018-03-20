package com.foresightautomotive.portfolio;

import com.foresightautomotive.repository.PortfolioRepository;
import com.foresightautomotive.repository.StocksRepository;
import com.foresightautomotive.repository.StocksRepository.StockNotFound;
import com.foresightautomotive.stock.PublicStock;
import com.foresightautomotive.stock.Stock;
import com.foresightautomotive.transactions.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.foresightautomotive.transactions.TransactionType.BUY;
import static com.foresightautomotive.transactions.TransactionType.SELL;

@Service
public class PortfolioPersistedService implements PortfolioService{

    private PortfolioRepository portfolioRepository;
    private StocksRepository stocksRepository;

    @Autowired
    public PortfolioPersistedService(PortfolioRepository portfolioRepository, StocksRepository stocksRepository){
        this.portfolioRepository = portfolioRepository;
        this.stocksRepository = stocksRepository;
    }

    @Override
    public float buy(String stockSymbol, int quantity) {
        Portfolio portfolio = findPortfolio();
        validateTransaction(stockSymbol, quantity, portfolio.getMyStocks());
        PublicStock publicStock = validateStockInSystem(stockSymbol);
        validateFunds(quantity, portfolio, publicStock.getCurrentPrice());
        saveUpdatedPortfolio(BUY, quantity, portfolio, publicStock);
        return findPortfolio().getFunds();
    }

    private void validateTransaction(String stockSymbol, int quantity, List<Stock> myStocks) {
        if(quantity <= 0){
            throw new QuantityInvalid("quantity must be grater than 0 in order to complete transaction");
        }

        Optional<Stock> stockInPortfolio = myStocks.stream().filter(s -> s.getSymbol().equals(stockSymbol)).findFirst();
        if(stockInPortfolio.isPresent()){
            throw new StockAlreadyPurchased("Stock: " + stockSymbol + " already purchased");
        }
    }

    @Override
    public float sell(String stockSymbol) {
        Portfolio portfolio = findPortfolio();
        PublicStock publicStock = validateStockInSystem(stockSymbol);
        Stock stock = validateStockInPortfolio(portfolio, stockSymbol);
        saveUpdatedPortfolio(SELL, stock.getQuantity(), portfolio, publicStock);
        return findPortfolio().getFunds();
    }

    @Override
    public Portfolio find() {
        return this.portfolioRepository.findOne("stocker");
    }

    private Stock validateStockInPortfolio(Portfolio portfolio, String stockSymbol) {
        Optional<Stock> stockInPortfolio = portfolio.getMyStocks().stream().filter(s -> s.getSymbol().equals(stockSymbol)).findFirst();
        if(!stockInPortfolio.isPresent()){
            throw new StockNotFound("Stock: " + stockSymbol + " not found in user portfolio");
        }

        return stockInPortfolio.get();
    }

    private void saveUpdatedPortfolio(TransactionType type, int quantity, Portfolio portfolio, PublicStock publicStock) {
        this.portfolioRepository.save(Portfolio.createFrom(type, portfolio, publicStock, quantity));
    }

    private Portfolio findPortfolio() {
        return this.portfolioRepository.findOne("stocker");
    }

    private PublicStock validateStockInSystem(String stockSymbol) {
        return Optional.ofNullable(this.stocksRepository.findBySymbol(stockSymbol))
                .orElseThrow(() -> new StockNotFound("stock with symbol: " + stockSymbol + " not found in system."));
    }

    private void validateFunds(int quantity, Portfolio portfolio, float currentStockPrice) {
        if(portfolio.getFunds() < currentStockPrice * quantity){
            throw new InsufficientFundsException("your account has insufficient funds to complete the current transaction");
        }
    }
}
