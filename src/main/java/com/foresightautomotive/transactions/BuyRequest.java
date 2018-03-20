package com.foresightautomotive.transactions;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Getter
@Setter
public class BuyRequest {
    private String stockSymbol;
    private int stockQuantity;
}
