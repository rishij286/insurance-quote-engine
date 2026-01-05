package com.rishi.quote_engine.quote;

// jakarta validation imports
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;

import lombok.Data;

@Data // generates getters, setters, toString, equals, and hashCode
public class QuoteRequest {

    @NotNull
    @Min(18)
    @Max(100)
    private Integer age;

    @NotBlank
    private String region;

    @NotNull
    @Min(0)
    @Max(100)
    private Integer riskScore;
}