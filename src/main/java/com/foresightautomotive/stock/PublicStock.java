package com.foresightautomotive.stock;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Getter
@Setter
public class PublicStock {
    private String symbol;
    @Id
    private String name;
    private String startOfCommerce;
    private float currentPrice;
}
