package com.nscott.countrypathdemo;

import com.nscott.countrypathdemo.constant.Region;
import com.nscott.countrypathdemo.model.CountryEntity;
import com.nscott.countrypathdemo.util.CountryUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CountryUtilsTest {

    @Test
    void testIsTraversableOnIsland() {
        // New entities with empty border lists
        assertFalse(CountryUtils.isTraversable(new CountryEntity(), new CountryEntity()));
    }

    @Test
    void testIsTraversableOnAfroeurasia() {
        CountryEntity start = new CountryEntity();
        start.setId("AAA");
        start.setRegion(Region.ASIA);
        start.setBorders(List.of("CCC"));

        CountryEntity dest = new CountryEntity();
        dest.setId("BBB");
        dest.setRegion(Region.AFRICA);
        dest.setBorders(List.of("CCC"));

        assertTrue(CountryUtils.isTraversable(start, dest));
    }

    @Test
    void testIsTraversableSeparateLandmass() {
        CountryEntity start = new CountryEntity();
        start.setId("AAA");
        start.setRegion(Region.AMERICAS);
        start.setBorders(List.of("CCC"));

        CountryEntity dest = new CountryEntity();
        dest.setId("BBB");
        dest.setRegion(Region.AFRICA);
        dest.setBorders(List.of("CCC"));

        assertFalse(CountryUtils.isTraversable(start, dest));
    }

    @Test
    void testHaversine() {
        CountryEntity start = new CountryEntity();
        start.setLatlng(List.of(38D, -97D));

        CountryEntity dest = new CountryEntity();
        dest.setLatlng(List.of(-10D, -55D));

        assertEquals(6893.655618883586, CountryUtils.calculateHaversineDistance(start, dest));
    }
}
