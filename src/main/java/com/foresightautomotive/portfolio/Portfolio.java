package com.foresightautomotive.portfolio;

import com.foresightautomotive.stock.PublicStock;
import com.foresightautomotive.stock.Stock;
import com.foresightautomotive.transactions.TransactionType;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.foresightautomotive.transactions.TransactionType.BUY;
import static com.foresightautomotive.transactions.TransactionType.SELL;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Getter
@Setter
public class Portfolio {
    @Id
    private String username = "stocker";
    private float funds = 10000;
    private List<Stock> myStocks = new ArrayList<>();

    public static Portfolio createFrom(TransactionType type, Portfolio portfolio, PublicStock publicStock, int quantity){
        return Portfolio.builder()
                .username(portfolio.getUsername())
                .funds(calcFunds(type, portfolio, publicStock, quantity))
                .myStocks(updateStocks(type, portfolio.getMyStocks(), Stock.createFrom(publicStock, quantity)))
                .build();
    }

    private static float calcFunds(TransactionType type, Portfolio portfolio, PublicStock publicStock, int quantity) {
        return type == BUY ? portfolio.getFunds() - (publicStock.getCurrentPrice() * quantity) : portfolio.getFunds() + (publicStock.getCurrentPrice() * quantity);
    }

    private static List<Stock> updateStocks(TransactionType type, List<Stock> currentStocks, Stock newStock){
        if(type == BUY) {
            updateStockAfterBuy(currentStocks, newStock);
        } else {
            updateStocksAfterSell(currentStocks, newStock);
        }
        return currentStocks;
    }

    private static void updateStockAfterBuy(List<Stock> currentStocks, Stock newStock){
        Optional<Stock> first = findBySymbol(currentStocks, newStock);
        if(first.isPresent()){
            first.get().setPurchasePrice(newStock.getPurchasePrice());
            first.get().setQuantity(newStock.getQuantity());
        } else {
            currentStocks.add(newStock);
        }

    }

    private static void updateStocksAfterSell(List<Stock> currentStocks, Stock stock) {
        Optional<Stock> first = findBySymbol(currentStocks, stock);
        if(first.isPresent()){
            currentStocks.removeIf(s-> s.getSymbol().equals(stock.getSymbol()));
        }
    }

    private static Optional<Stock> findBySymbol(List<Stock> currentStocks, Stock stock) {
        return currentStocks.stream().filter(s -> s.getSymbol().equals(stock.getSymbol())).findFirst();
    }
}
