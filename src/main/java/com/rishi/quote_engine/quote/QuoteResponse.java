package com.rishi.quote_engine.quote;

import java.util.List;
import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
// Response object for Quote operations
public class QuoteResponse {
    private Long id;
    private double basePrice, finalPrice;
    private List<PricingAdjustment> adjustments;
}
