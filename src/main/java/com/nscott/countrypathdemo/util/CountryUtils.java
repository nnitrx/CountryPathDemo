package com.nscott.countrypathdemo.util;

import com.nscott.countrypathdemo.constant.Region;
import com.nscott.countrypathdemo.model.CountryEntity;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Util class for common Country validations and calculations
 */
@Component
public class CountryUtils {

    private static final double EARTH_RADIUS = 6372.8;
    private static final Set<Region> afroEurasiaGroup = Set.of(
            Region.AFRICA,
            Region.ASIA,
            Region.EUROPE);

    /**
     * Make preliminary checks between start and destination country to see if a land-path is possible
     * @param start Start country
     * @param dest Destination country
     * @return true if land-path is possible
     */
    public static boolean isTraversable(CountryEntity start, CountryEntity dest) {
        if (start.isIsland() || dest.isIsland()) {
            return false;
        }

        return start.getRegion().equals(dest.getRegion())
                || afroEurasiaGroup.containsAll(Arrays.asList(start.getRegion(), dest.getRegion()));
    }

    /**
     * Returns calculation of distance between country lat/long coordinates using Haversine formula
     * @param start Staring country
     * @param dest Destination country
     * @return Solution of distance between countries
     */
    public static double calculateHaversineDistance(CountryEntity start, CountryEntity dest) {
        List<Double> startLatLng = start.getLatlng();
        List<Double> destLatLng = dest.getLatlng();

        double deltaLat = Math.toRadians(destLatLng.getFirst() - startLatLng.getFirst());
        double deltaLong = Math.toRadians(destLatLng.getLast() - startLatLng.getLast());
        double lat1Rad = Math.toRadians(startLatLng.getFirst());
        double lat2Rad = Math.toRadians(destLatLng.getFirst());

        double a = Math.pow(Math.sin(deltaLat / 2),2)
                + Math.pow(Math.sin(deltaLong / 2),2) * Math.cos(lat1Rad) * Math.cos(lat2Rad);
        double c = 2 * Math.asin(Math.sqrt(a));
        return EARTH_RADIUS * c;
    }
}
