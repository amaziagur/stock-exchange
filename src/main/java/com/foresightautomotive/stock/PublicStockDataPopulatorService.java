package com.foresightautomotive.stock;

import com.foresightautomotive.portfolio.Portfolio;
import com.foresightautomotive.repository.PortfolioRepository;
import com.foresightautomotive.repository.StocksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

@Service
public class PublicStockDataPopulatorService {

    private StocksRepository stocksRepository;
    private PortfolioRepository portfolioRepository;

    @Autowired
    public PublicStockDataPopulatorService(StocksRepository stocksRepository, PortfolioRepository portfolioRepository){
        this.stocksRepository = stocksRepository;
        this.portfolioRepository = portfolioRepository;
    }

    @PostConstruct
    public void init(){
        reset();
        populate();
    }

    public void refreshPrices() {
        stocksRepository.updateAll();
    }

    private void reset() {
        this.stocksRepository.deleteAll();
        this.portfolioRepository.deleteAll();
    }

    private String getTime(int year, int month, int day) {
        return toISO8601UTC(new GregorianCalendar(year, month, day).getTime());
    }

    private static String toISO8601UTC(Date date) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        return df.format(date);
    }

    private void populate() {
        stocksRepository.save(PublicStock.builder().symbol("AMZN").startOfCommerce(getTime(1997, Calendar.MAY, 16)).currentPrice(119.58f).name("Amazon").build());
        stocksRepository.save(PublicStock.builder().symbol("APPL").startOfCommerce(getTime(1980, Calendar.DECEMBER, 1)).currentPrice(174.71f).name("Apple Inc").build());
        stocksRepository.save(PublicStock.builder().symbol("BB").startOfCommerce(getTime(1999, Calendar.FEBRUARY, 5)).currentPrice(10.94f).name("BlackBerry Ltd").build());
        stocksRepository.save(PublicStock.builder().symbol("DIS").startOfCommerce(getTime(1977, Calendar.DECEMBER, 23)).currentPrice(111.03f).name("Disney Co").build());
        stocksRepository.save(PublicStock.builder().symbol("EBAY").startOfCommerce(getTime(1998, Calendar.SEPTEMBER, 25)).currentPrice(38.18f).name("eBay Inc").build());
        stocksRepository.save(PublicStock.builder().symbol("FB").startOfCommerce(getTime(2012, Calendar.MAY, 18)).currentPrice(180.82f).name("Facebook, Inc").build());
        stocksRepository.save(PublicStock.builder().symbol("FRSX").startOfCommerce(getTime(2015, Calendar.MAY, 18)).currentPrice(180.82f).name("Foresight Autonomous Holdings Ltd.").build());
        stocksRepository.save(PublicStock.builder().symbol("GE").startOfCommerce(getTime(1962, Calendar.JANUARY, 1)).currentPrice(18.28f).name("General Electric Company").build());
        stocksRepository.save(PublicStock.builder().symbol("GOOGL").startOfCommerce(getTime(2004, Calendar.AUGUST, 20)).currentPrice(1085.09f).name("Google, Inc").build());
        stocksRepository.save(PublicStock.builder().symbol("HD").startOfCommerce(getTime(1981, Calendar.SEPTEMBER, 25)).currentPrice(184.73f).name("Home Depot Inc").build());
        stocksRepository.save(PublicStock.builder().symbol("IBM").startOfCommerce(getTime(1977, Calendar.DECEMBER, 23)).currentPrice(153.33f).name("IBM Common Stock").build());
        stocksRepository.save(PublicStock.builder().symbol("INTC").startOfCommerce(getTime(1980, Calendar.MARCH, 1)).currentPrice(44.74f).name("Intel Corporation").build());
        stocksRepository.save(PublicStock.builder().symbol("JPM").startOfCommerce(getTime(1977, Calendar.DECEMBER, 23)).currentPrice(106.96f).name("JPMorgan Chase & Co").build());
        stocksRepository.save(PublicStock.builder().symbol("KO").startOfCommerce(getTime(1977, Calendar.DECEMBER, 23)).currentPrice(45.93f).name("Coca-Cola Co").build());
        stocksRepository.save(PublicStock.builder().symbol("LL").startOfCommerce(getTime(2007, Calendar.NOVEMBER, 9)).currentPrice(31.05f).name("Lumber Liquidators Holdings Inc").build());
        stocksRepository.save(PublicStock.builder().symbol("MCD").startOfCommerce(getTime(1970, Calendar.JANUARY, 1)).currentPrice(173.93f).name("McDonald's Corporation").build());
        stocksRepository.save(PublicStock.builder().symbol("MSFT").startOfCommerce(getTime(1986, Calendar.MARCH, 1)).currentPrice(88.28f).name("Microsoft Corporation").build());
        stocksRepository.save(PublicStock.builder().symbol("NFLX").startOfCommerce(getTime(2002, Calendar.MAY, 24)).currentPrice(190.42f).name("Netflix, Inc.").build());
        stocksRepository.save(PublicStock.builder().symbol("OXY").startOfCommerce(getTime(1977, Calendar.DECEMBER, 23)).currentPrice(70.97f).name("Occidental Petroleum Corporation").build());
        stocksRepository.save(PublicStock.builder().symbol("PYPL").startOfCommerce(getTime(2015, Calendar.JULY, 10)).currentPrice(75.32f).name("Paypal Holdings Inc").build());
        stocksRepository.save(PublicStock.builder().symbol("RDSA").startOfCommerce(getTime(2015, Calendar.JULY, 22)).currentPrice(27.52f).name("Royal Dutch Shell").build());
        stocksRepository.save(PublicStock.builder().symbol("SNY").startOfCommerce(getTime(2000, Calendar.JANUARY, 7)).currentPrice(5116f).name("Sony Corp").build());
        stocksRepository.save(PublicStock.builder().symbol("TSLA").startOfCommerce(getTime(2010, Calendar.JUNE, 1)).currentPrice(336.41f).name("Tesla Inc").build());
        stocksRepository.save(PublicStock.builder().symbol("VOW3").startOfCommerce(getTime(1998, Calendar.JULY, 24)).currentPrice(118.81f).name("Volkswagen").build());
        stocksRepository.save(PublicStock.builder().symbol("WMT").startOfCommerce(getTime(1977, Calendar.DECEMBER, 23)).currentPrice(97.90f).name("Wal-Mart Stores Inc").build());
        stocksRepository.save(PublicStock.builder().symbol("XRX").startOfCommerce(getTime(1977, Calendar.DECEMBER, 23)).currentPrice(30.30f).name("Xerox Corp").build());
        stocksRepository.save(PublicStock.builder().symbol("ZNGA").startOfCommerce(getTime(2011, Calendar.DECEMBER, 16)).currentPrice(4.06f).name("Zynga Inc").build());

        portfolioRepository.save(new Portfolio());
    }
}
