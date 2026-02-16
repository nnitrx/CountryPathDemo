package com.nscott.countrypathdemo.service;

import com.nscott.countrypathdemo.constant.ErrorConstants;
import com.nscott.countrypathdemo.model.CountryEntity;
import com.nscott.countrypathdemo.repository.CountryRepository;
import com.nscott.countrypathdemo.util.CountryUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;

    @Override
    public List<String> getPathBetweenCountries(String startId, String destId) {

        if (startId.equals(destId)) {
            throw new IllegalArgumentException("Start and Destination countries must be different.");
        }

        CountryEntity start = countryRepository.findById(startId).orElseThrow(NoSuchElementException::new);
        CountryEntity dest = countryRepository.findById(destId).orElseThrow(NoSuchElementException::new);

        if(!CountryUtils.isTraversable(start, dest)) {
            throw new IllegalArgumentException(ErrorConstants.NO_POSSIBLE_PATH);
        }

        List<String> pathList = new LinkedList<>();
        pathList.add(startId);

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

        // Fetch next bordering country by finding the one 'closest' to the destination country
        CountryEntity nextCountry = countryRepository.findAllById(currCountry.getBorders()).stream()
                .filter(x -> !pathList.contains(x.getId()))
                .reduce((x, y) -> {
                    List<Double> xLatLng = x.getLatlng();
                    List<Double> yLatLng = y.getLatlng();
                    List<Double> destLatLng = destCountry.getLatlng();

                    return CountryUtils.calculateDistance(xLatLng.get(0), xLatLng.get(1), destLatLng.get(0), destLatLng.get(1)) <=
                            CountryUtils.calculateDistance(yLatLng.get(0), yLatLng.get(1), destLatLng.get(0), destLatLng.get(1)) ?
                            x : y;
                }).orElseThrow(() -> new IllegalArgumentException(ErrorConstants.NO_POSSIBLE_PATH));

        return nextCountry;
    }
}
