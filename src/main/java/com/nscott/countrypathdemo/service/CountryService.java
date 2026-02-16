package com.nscott.countrypathdemo.service;

import java.util.List;

public interface CountryService {
    /**
     * Fetch land path between two countries.
     * @param sourceId
     * @param destId
     * @return List of each country code that's expected to be land traversed between start and destination country
     */
    List<String> getPathBetweenCountries(String sourceId, String destId);
}
