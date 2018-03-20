package com.foresightautomotive.transactions;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Getter
@Setter
public class SellRequest {
    private String stockSymbol;
}
