package com.nscott.countrypathdemo;

import com.nscott.countrypathdemo.constant.Region;
import com.nscott.countrypathdemo.model.CountryEntity;
import com.nscott.countrypathdemo.repository.CountryRepository;
import com.nscott.countrypathdemo.service.CountryServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CountryServiceImplTest {

    @Mock
    private CountryRepository countryRepository;

    @InjectMocks
    private CountryServiceImpl underTest;

    @Test
    void testGetPath() {
        CountryEntity start = new CountryEntity();
        start.setId("AAA");
        start.setRegion(Region.AMERICAS);
        start.setLatlng(List.of(60D, -95D));
        start.setBorders(List.of("CCC"));

        CountryEntity dest = new CountryEntity();
        dest.setId("BBB");
        dest.setRegion(Region.AMERICAS);
        dest.setLatlng(List.of(23D, -102D));
        dest.setBorders(List.of("CCC"));

        CountryEntity mid = new CountryEntity();
        mid.setId("CCC");
        mid.setRegion(Region.AMERICAS);
        mid.setLatlng(List.of(41D, 64D));
        mid.setBorders(List.of("AAA", "BBB"));

        when(countryRepository.findById("AAA")).thenReturn(Optional.of(start));
        when(countryRepository.findById("BBB")).thenReturn(Optional.of(dest));
        when(countryRepository.findAllById(any(List.class)))
                .thenReturn(List.of(mid))
                .thenReturn(List.of(start, dest));

        underTest.getPathBetweenCountries(start.getId(), dest.getId());
    }
}
