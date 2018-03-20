package com.foresightautomotive.repository;

import com.foresightautomotive.stock.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

public class PortfolioRepositoryImpl implements CustomPortfolioRepository {

    private final MongoOperations mongo;

    @Autowired
    public PortfolioRepositoryImpl(MongoOperations mongo){
        this.mongo = mongo;
    }

    @Override
    public void updatePortfolio(String stockSymbol, double purchasePrice, int quantity) {
        Update update = new Update().set("purchasePrice", purchasePrice).set("quantity", quantity);
        mongo.findAndModify(new Query(Criteria.where("symbol").is(stockSymbol)), update, Stock.class);
    }
}
