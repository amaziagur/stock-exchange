package com.foresightautomotive.stock;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Getter
@Setter
public class Stock {
    private String symbol;
    private float purchasePrice;
    private int quantity;

    public static Stock createFrom(PublicStock publicStock, int quantity){
        return Stock.builder()
                .purchasePrice(publicStock.getCurrentPrice())
                .symbol(publicStock.getSymbol())
                .quantity(quantity)
                .build();
    }
}
