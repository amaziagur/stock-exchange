package com.foresightautomotive.repository;

public interface CustomPortfolioRepository {

    void updatePortfolio(String stockSymbol, double purchasePrice, int quantity);
}
