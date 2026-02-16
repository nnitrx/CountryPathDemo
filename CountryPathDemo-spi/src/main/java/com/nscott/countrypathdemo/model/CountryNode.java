package com.nscott.countrypathdemo.model;

import lombok.Data;

/**
 * Wrapper class used for traversing countries
 * Used to backtrack path once destination is found
 */
@Data
public class CountryNode {
    private CountryEntity country;
    private CountryNode prevNode;
    private Double borderScore;
    private Double destScore;

    public CountryNode(CountryEntity country) {
        this(country, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
    }

    public CountryNode(CountryEntity country, Double borderScore, Double destScore) {
        this.country = country;
        this.borderScore = borderScore;
        this.destScore = destScore;
    }
}
