package com.nscott.countrypathdemo.service;

import com.nscott.countrypathdemo.model.CountryEntity;
import com.nscott.countrypathdemo.repository.CountryRepository;
import com.nscott.countrypathdemo.util.CountryUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Service
@AllArgsConstructor
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;

    @Override
    public List<String> getPathBetweenCountries(String sourceId, String destId) {
        CountryEntity start = countryRepository.findById(sourceId).orElseThrow();
        CountryEntity dest = countryRepository.findById(destId).orElseThrow();

        if(!CountryUtils.isTraversable(start, dest)) {
            return Collections.emptyList();
        }

        List<String> pathList = new LinkedList<>();
        pathList.add(sourceId);

        CountryEntity nextCountry = start;
        do {
            nextCountry = getNextCountryInPath(nextCountry, dest, pathList);
            pathList.add(nextCountry.getId());
        } while (!pathList.getLast().equals(destId));

        return pathList;
    }

    private CountryEntity getNextCountryInPath(CountryEntity currCountry, CountryEntity destCountry, List<String> pathList) {
        if (currCountry.getBorders().contains(destCountry.getId())) {
            return destCountry;
        }

        CountryEntity nextCountry = countryRepository.findAllById(currCountry.getBorders()).stream()
                .filter(x -> !pathList.contains(x.getId()))
                .reduce((x, y) -> {
                    List<Double> xLatLng = x.getLatlng();
                    List<Double> yLatLng = y.getLatlng();
                    List<Double> destLatLng = destCountry.getLatlng();

                    return CountryUtils.calculateDistance(xLatLng.get(0), xLatLng.get(1), destLatLng.get(0), destLatLng.get(1)) <
                            CountryUtils.calculateDistance(yLatLng.get(0), yLatLng.get(1), destLatLng.get(0), destLatLng.get(1)) ?
                            x : y;
                }).orElseThrow();

        return nextCountry;
    }
}
