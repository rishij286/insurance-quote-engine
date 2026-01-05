package com.rishi.quote_engine.quote;

import org.springframework.data.jpa.repository.JpaRepository;

public interface QuoteRepository extends JpaRepository<Quote, Long> {
    // Add custom query methods here if needed
}
 