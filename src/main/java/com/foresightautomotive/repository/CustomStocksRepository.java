package com.foresightautomotive.repository;

import com.foresightautomotive.stock.PublicStock;
import com.foresightautomotive.stock.Stock;

import java.util.List;

public interface CustomStocksRepository {

    List<PublicStock> searchStockBy(String searchString);

    void updatePrice(String stockSymbol, int newPrice);

    List<PublicStock> listBy(String[] stockSymbols);

    void updateAll();
}
