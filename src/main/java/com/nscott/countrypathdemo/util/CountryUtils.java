package com.nscott.countrypathdemo.util;

import com.nscott.countrypathdemo.constant.Region;
import com.nscott.countrypathdemo.model.CountryEntity;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Set;

/**
 * Util class for common Country validations and calculations
 */
@Component
public class CountryUtils {

    private static final double EARTH_RADIUS = 6372.8;

    public static final Set<Region> afroEurasiaGroup = Set.of(
            Region.AFRICA,
            Region.ASIA,
            Region.EUROPE);

    public static boolean isTraversable(CountryEntity start, CountryEntity dest) {
        if (start.isIsland() || dest.isIsland()) {
            return false;
        }

        return start.getRegion().equals(dest.getRegion())
                || afroEurasiaGroup.containsAll(Arrays.asList(start.getRegion(), dest.getRegion()));
    }

    public static double calculateDistance(double lat1, double lng1, double lat2, double lng2) {
        double deltaLat = Math.toRadians(lat2 - lat1);
        double deltaLong = Math.toRadians(lng2 - lng1);
        double lat1Rad = Math.toRadians(lat1);
        double lat2Rad = Math.toRadians(lat2);

        double a = Math.pow(Math.sin(deltaLat / 2),2)
                + Math.pow(Math.sin(deltaLong / 2),2) * Math.cos(lat1Rad) * Math.cos(lat2Rad);
        double c = 2 * Math.asin(Math.sqrt(a));
        return EARTH_RADIUS * c;
    }
}
