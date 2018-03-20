package it.com.foresightautomotive.service;

import com.foresightautomotive.portfolio.Portfolio;
import com.foresightautomotive.portfolio.PortfolioPersistedService;
import com.foresightautomotive.portfolio.PortfolioService;
import com.foresightautomotive.portfolio.PortfolioService.InsufficientFundsException;
import com.foresightautomotive.portfolio.PortfolioService.QuantityInvalid;
import com.foresightautomotive.repository.PortfolioRepository;
import com.foresightautomotive.repository.StocksRepository;
import com.foresightautomotive.repository.StocksRepository.StockNotFound;
import com.foresightautomotive.stock.PublicStock;
import it.com.foresightautomotive.repository.PortfolioRepositoryContext;
import it.com.foresightautomotive.repository.StocksRepositoryContext;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {StocksRepositoryContext.class, PortfolioRepositoryContext.class})
public class PortfolioPersistedServiceIT {

    private final Portfolio MY_PORTFOLIO = new Portfolio();
    private final PublicStock AMAZON_STOCK = PublicStock.builder().name("amazon").currentPrice(100).startOfCommerce(new Date().toString()).symbol("AMAZ").build();
    private final PublicStock GOOGLE_STOCK = PublicStock.builder().name("google").currentPrice(505).startOfCommerce(new Date().toString()).symbol("GOOGL").build();

    @Autowired
    StocksRepository stocksRepository;

    @Autowired
    PortfolioRepository portfolioRepository;

    private PortfolioService portfolioPersistedService;

    @Before
    public void setUp() throws Exception {
        cleanup();
        portfolioPersistedService = new PortfolioPersistedService(portfolioRepository, stocksRepository);
        saveInitData();
    }

    private void saveInitData() {
        stocksRepository.save(AMAZON_STOCK);
        stocksRepository.save(GOOGLE_STOCK);
        portfolioRepository.save(MY_PORTFOLIO);
    }

    private void cleanup() {
        portfolioRepository.deleteAll();
        stocksRepository.deleteAll();
    }

    @Test
    public void shouldBuyStock(){
        assertTrue(portfolioPersistedService.buy("AMAZ", 3) == 9700.0);
        assertTrue(portfolioRepository.findOne("stocker").getFunds() == 9700.0);
        assertTrue(portfolioRepository.findOne("stocker").getMyStocks().get(0).getQuantity() == 3);
        assertTrue(portfolioRepository.findOne("stocker").getMyStocks().get(0).getPurchasePrice() == 100);
    }

    @Test
    public void shouldBuyNumberOfStocks(){
        assertTrue(portfolioPersistedService.buy("AMAZ", 3) == 9700.0);
        assertTrue(portfolioRepository.findOne("stocker").getFunds() == 9700.0);
        assertTrue(portfolioRepository.findOne("stocker").getMyStocks().get(0).getQuantity() == 3);
        assertTrue(portfolioRepository.findOne("stocker").getMyStocks().get(0).getPurchasePrice() == 100);
        assertTrue(portfolioPersistedService.buy("GOOGL", 12) ==3640.0);
        assertTrue(portfolioRepository.findOne("stocker").getMyStocks().get(1).getQuantity() == 12);
        assertTrue(portfolioRepository.findOne("stocker").getMyStocks().get(1).getPurchasePrice() == 505);
    }

    @Test(expected = InsufficientFundsException.class)
    public void throwInsufficientFundsException(){
        portfolioPersistedService.buy("AMAZ", 10000);
    }

    @Test(expected = StockNotFound.class)
    public void throwStockNotFoundException(){
        portfolioPersistedService.buy("NOT FOUND", 2);
    }

    @Test
    public void shouldSellStock(){
        assertTrue(portfolioPersistedService.buy("AMAZ", 5) ==9500.0);
        stocksRepository.updatePrice("AMAZ", 200);
        assertTrue(portfolioPersistedService.sell("AMAZ") ==10500.0);
        assertTrue(portfolioRepository.findOne("stocker").getMyStocks().size() == 0);
    }

    @Test
    public void shouldSellNumberOfStocks(){
        assertTrue(portfolioPersistedService.buy("AMAZ", 5) == 9500.0);
        assertTrue(portfolioPersistedService.buy("GOOGL", 12) ==3440.0);
        stocksRepository.updatePrice("AMAZ", 50);
        stocksRepository.updatePrice("GOOGL", 100);
        assertTrue(portfolioPersistedService.sell("AMAZ") == 3690.0);
        assertTrue(portfolioPersistedService.sell("GOOGL") == 4890.0);
    }

    @Test(expected = StockNotFound.class)
    public void throwStockNotFoundExceptionGivenStockNowInPortfolio(){
        assertTrue(portfolioPersistedService.buy("AMAZ", 5) ==9500.0);
        portfolioPersistedService.sell("GOOGL");
    }

    @Test(expected = QuantityInvalid.class)
    public void throwQuantityInvalid(){
        portfolioPersistedService.buy("AMAZ", -5);
    }

    @Test(expected = PortfolioService.StockAlreadyPurchased.class)
    public void throwStockAlreadyPurchased(){
        portfolioPersistedService.buy("AMAZ", 1);
        portfolioPersistedService.buy("AMAZ", 2);
    }

}
