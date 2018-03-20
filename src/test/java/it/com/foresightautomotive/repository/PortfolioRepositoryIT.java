package it.com.foresightautomotive.repository;

import com.foresightautomotive.portfolio.Portfolio;
import com.foresightautomotive.repository.PortfolioRepository;
import com.foresightautomotive.repository.StocksRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PortfolioRepositoryContext.class, StocksRepositoryContext.class})
public class PortfolioRepositoryIT {

    @Autowired
    PortfolioRepository portfolioRepository;

    @Before
    public void setUp() throws Exception {
        portfolioRepository.deleteAll();
        portfolioRepository.save(new Portfolio());
    }


    @Test
    public void shouldUpdateStock(){
        portfolioRepository.updatePortfolio("AMAZ", 32, 5);
    }
}
