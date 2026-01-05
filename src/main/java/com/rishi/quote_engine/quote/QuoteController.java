package com.rishi.quote_engine.quote;

// Spring imports
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;


@RestController
@RequestMapping("/api/quotes")
public class QuoteController {
    private final PricingService pricingService;

    // constructor
    public QuoteController(PricingService pricingService) {
        this.pricingService = pricingService;
    }

    @PostMapping // handles POST requests to /api/quotes
    public ResponseEntity<QuoteResponse> createQuote(@Valid @RequestBody QuoteRequest request) {
        QuoteResponse response = pricingService.createQuote(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuoteResponse> getQuote(@PathVariable Long id) {
        QuoteResponse response = pricingService.getQuote(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuote(@PathVariable Long id) {
        pricingService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}