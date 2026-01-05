package com.rishi.quote_engine.quote;

import jakarta.persistence.*;
import java.time.OffsetDateTime;

// lombok imports
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity // marks this class as a JPA entity
@Table(name = "quotes")
@Data // generates getters, setters, toString, equals, and hashCode
@NoArgsConstructor // generates no-args constructor
@AllArgsConstructor // generates all-args constructor
public class Quote {

    @Id // marks this field as the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer age;
    private String region;
    private Integer riskScore;

    private Double basePrice;
    private Double finalPrice;

    // timestamp of when the quote was created
    private OffsetDateTime createdAt;

    @Column(columnDefinition = "TEXT") // indicates that this field should be treated as a large object
    private String explanationJson; // store explanation as JSON string

    @PrePersist // lifecycle callback to set createdAt before persisting
    protected void onCreate() {
        this.createdAt = OffsetDateTime.now();
    }
}