package com.foresightautomotive.repository;

import com.foresightautomotive.portfolio.Portfolio;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PortfolioRepository extends MongoRepository<Portfolio, String>, CustomPortfolioRepository {

}
