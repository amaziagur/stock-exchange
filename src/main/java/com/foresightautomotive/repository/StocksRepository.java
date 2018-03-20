package com.foresightautomotive.repository;

import com.foresightautomotive.stock.PublicStock;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StocksRepository extends MongoRepository<PublicStock, String> , CustomStocksRepository {

    PublicStock findBySymbol(String symbol);

    class StockNotFound extends RuntimeException {
        public StockNotFound(String message) {
            super(message);
        }
    }
}
