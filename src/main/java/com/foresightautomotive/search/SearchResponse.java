package com.foresightautomotive.search;

import com.foresightautomotive.stock.PublicStock;
import lombok.*;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Getter
@Setter
public class SearchResponse {
    private List<PublicStock> stocks;
}
