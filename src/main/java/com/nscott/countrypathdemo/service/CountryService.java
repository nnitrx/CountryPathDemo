package com.nscott.countrypathdemo.service;

import java.util.List;

public interface CountryService {
    /**
     * Fetch land path between two countries.
     * @param startId Country code of starting country
     * @param destId Country code of destination country
     * @return List of each country code that's expected to be land traversed between start and destination country
     */
    List<String> getPathBetweenCountries(String startId, String destId);
}
