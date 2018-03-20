package com.foresightautomotive.portfolio;

public interface PortfolioService {

    float buy(String stockSymbol, int quantity);

    float sell(String stockSymbol);

    Portfolio find();

    class InsufficientFundsException extends RuntimeException {
        public InsufficientFundsException(String message) {
            super(message);
        }
    }

    class QuantityInvalid extends RuntimeException {
        public QuantityInvalid(String message)  {
            super(message);
        }
    }

    class StockAlreadyPurchased extends RuntimeException {
        public StockAlreadyPurchased(String message)  {
            super(message);
        }
    }
}
