package it.com.foresightautomotive.repository;


import com.foresightautomotive.repository.StocksRepository;
import com.foresightautomotive.stock.PublicStock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.Date;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {StocksRepositoryContext.class})
public class StocksRepositoryIT {

    private final PublicStock AMAZON = PublicStock.builder().name("amazon").currentPrice(100).startOfCommerce(new Date().toString()).symbol("AMAZ").build();
    private final PublicStock AMAZIA = PublicStock.builder().name("Amazia Inc").currentPrice(75).startOfCommerce(new Date().toString()).symbol("AMZI").build();
    private final PublicStock GOOGLE = PublicStock.builder().name("google").currentPrice(25).startOfCommerce(new Date().toString()).symbol("GOOG").build();


    @Autowired
    StocksRepository stocksRepository;

    @Before
    public void setUp() throws Exception {
        stocksRepository.deleteAll();
    }

    @Test
    public void shouldReceiveStocksBySearchString(){
        stocksRepository.save(AMAZON);
        stocksRepository.save(AMAZIA);
        stocksRepository.save(GOOGLE);

        assertThat(stocksRepository.searchStockBy("am"), is(Arrays.asList(AMAZIA, AMAZON)));
    }

    @Test
    public void shouldHandleSpacesInSearchString(){
        stocksRepository.save(AMAZON);
        stocksRepository.save(AMAZIA);
        assertThat(stocksRepository.searchStockBy(" a m "), is(Arrays.asList(AMAZIA, AMAZON)));
    }

    @Test
    public void shouldUpdateStockPrice(){
        stocksRepository.save(AMAZON);
        stocksRepository.updatePrice(AMAZON.getSymbol(), 200);
        assertThat(stocksRepository.findBySymbol(AMAZON.getSymbol()).getCurrentPrice(), is(200.0f));
    }

    @Test
    public void shouldReceiveListOfStocks(){
        stocksRepository.save(AMAZON);
        stocksRepository.save(AMAZIA);
        stocksRepository.save(GOOGLE);
        assertThat(stocksRepository.listBy(new String[]{AMAZIA.getSymbol(), AMAZON.getSymbol()}), is(Arrays.asList(AMAZON,AMAZIA)));
    }

    @Test
    public void shouldUpdateAllStocks(){
        stocksRepository.save(AMAZON);
        stocksRepository.save(AMAZIA);
        stocksRepository.save(GOOGLE);
        stocksRepository.updateAll();
        assertNotEquals(stocksRepository.findAll(), Arrays.asList(AMAZON, AMAZIA, GOOGLE));
    }
}
