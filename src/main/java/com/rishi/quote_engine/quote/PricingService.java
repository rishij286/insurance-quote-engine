package com.rishi.quote_engine.quote;

import org.springframework.stereotype.Service;

// Jackson imports for JSON handling
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.ArrayList;

@Service
public class PricingService {
    private final QuoteRepository quoteRepository;
    private final ObjectMapper objectMapper;

    // constructor
    public PricingService(QuoteRepository quoteRepository, ObjectMapper objectMapper) {
        this.quoteRepository = quoteRepository;
        this.objectMapper = objectMapper;
    }

    // method to create a new quote based on the request
    public QuoteResponse createQuote(QuoteRequest request) {
        double basePrice = 1000.0; // base price for calculation
        double finalPrice = basePrice;
        List<PricingAdjustment> adjustments = new ArrayList<>();

        // adjustment based on age
        if (request.getAge() < 25) {
            double adjustmentValue = 1.2; // 20% increase
            finalPrice *= adjustmentValue;
            adjustments.add(new PricingAdjustment("Age under 25", "MULTIPLY", adjustmentValue, finalPrice));
        }
        else if (request.getAge() > 64) {
            double adjustmentValue = 1.1; // 10% increase
            finalPrice *= adjustmentValue;
            adjustments.add(new PricingAdjustment("Age over 64", "MULTIPLY", adjustmentValue, finalPrice));
        } else { // no adjustment for ages between 25 and 64, lowest risk
            adjustments.add(new PricingAdjustment("Age between 25 and 64", "MULTIPLY", 1.0, finalPrice));
        }

        // adjustment based on risk score
        if (request.getRiskScore() > 70) {     
            finalPrice = finalPrice + 200; // $200 increase
            adjustments.add(new PricingAdjustment("High risk score", "ADD", 200.0, finalPrice));
        } else { // otherwise no adjustment
            adjustments.add(new PricingAdjustment("Acceptable risk score", "MULTIPLY", 1.0, finalPrice));
        }

        // adjustment based on region
        if ("high_risk_region".equalsIgnoreCase(request.getRegion())) {
            double adjustmentValue = 1.25; // 25% increase
            finalPrice *= adjustmentValue;
            adjustments.add(new PricingAdjustment("High risk region", "MULTIPLY", adjustmentValue, finalPrice));
        }

        else if ("low_risk_region".equalsIgnoreCase(request.getRegion())) {
            double adjustmentValue = 0.9; // 10% decrease
            finalPrice *= adjustmentValue;
            adjustments.add(new PricingAdjustment("Low risk region", "MULTIPLY", adjustmentValue, finalPrice));
        } else { // neutral region, no adjustment
            adjustments.add(new PricingAdjustment("Neutral risk region", "MULTIPLY", 1.0, finalPrice));
        }

        if (finalPrice < 300) {
            finalPrice = 300; // enforce minimum final price
        }

        // Create and save Quote entity
        Quote quote = new Quote();

        quote.setAge(request.getAge());
        quote.setRegion(request.getRegion());
        quote.setRiskScore(request.getRiskScore());

        quote.setBasePrice(basePrice);
        quote.setFinalPrice(finalPrice);


        Quote savedQuote = quoteRepository.save(quote);

        try {
            String explanationJson = objectMapper.writeValueAsString(adjustments);
            savedQuote.setExplanationJson(explanationJson);
            quoteRepository.save(savedQuote); // update with explanation
        } catch (JsonProcessingException e) { // handle exception
            e.printStackTrace();
        }

        // save now at the end to include explanationJson if needed
        quoteRepository.save(savedQuote);
        return new QuoteResponse(savedQuote.getId(), savedQuote.getBasePrice(), savedQuote.getFinalPrice(), adjustments);
    }

    // method to retrieve a quote by ID
    public QuoteResponse getQuote(Long id) {
        Quote quote = quoteRepository.findById(id).orElseThrow(() -> new RuntimeException("Quote not found: " + id));
        List<PricingAdjustment> adjustments = new ArrayList<>();

        try {
            // deserialize explanationJson back to List<PricingAdjustment>
            adjustments = objectMapper.readValue(quote.getExplanationJson(),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, PricingAdjustment.class));
        } catch (Exception e) {
            // handle exception
            adjustments = new ArrayList<>();
        }
        return new QuoteResponse(
                                quote.getId(), 
                                quote.getBasePrice(), 
                                quote.getFinalPrice(), 
                                adjustments
                            );
    }

    public void deleteById(Long id) {
        quoteRepository.deleteById(id);
    }
}
