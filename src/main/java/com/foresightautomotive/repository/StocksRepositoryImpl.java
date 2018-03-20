package com.foresightautomotive.repository;

import com.foresightautomotive.stock.PublicStock;
import com.foresightautomotive.stock.Stock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;
import java.util.Random;

@Slf4j
public class StocksRepositoryImpl implements CustomStocksRepository {

    private final MongoOperations mongo;

    @Autowired
    public StocksRepositoryImpl(MongoOperations mongo){
        this.mongo = mongo;
    }

    @Override
    public List<PublicStock> searchStockBy(String searchString) {
        return mongo.find(new Query(Criteria.where("name").regex("^" + searchString.replaceAll("\\s+",""), "i")), PublicStock.class);
    }

    @Override
    public void updatePrice(String stockSymbol, int newPrice) {
        mongo.findAndModify(new Query(Criteria.where("symbol").is(stockSymbol)), new Update().set("currentPrice", newPrice), PublicStock.class);
    }

    @Override
    public List<PublicStock> listBy(String[] stockSymbols) {
        return mongo.find(new Query(Criteria.where("symbol").in(stockSymbols)), PublicStock.class);
    }

    @Override
    public void updateAll() {
        log.info("going to update prices");
        List<PublicStock> all = mongo.findAll(PublicStock.class);
        all.forEach(ps -> updatePrice(ps));
        log.info("finished to update prices");
    }

    private void updatePrice(PublicStock ps) {
        ps.setCurrentPrice(100.0f + new Random().nextFloat() * (500.0f - 100.0f));
        mongo.save(ps);
    }
}
