package com.nscott.countrypathdemo.service;

import java.util.List;

public interface CountryService {
    List<String> getPathBetweenCountries(String sourceId, String destId);
}
