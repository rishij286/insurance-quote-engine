package com.rishi.quote_engine.quote;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
// Represents a pricing adjustment made to a quote, to be used in the explanation
public class PricingAdjustment {
    private String reason;
    private String type; // "MULTIPLY" | "ADD"
    private Double value; // 1.2, 0.9 | +200, -150 (random numbers for now)
    private Double newPriceAfter; // price after applying this adjustment
}
