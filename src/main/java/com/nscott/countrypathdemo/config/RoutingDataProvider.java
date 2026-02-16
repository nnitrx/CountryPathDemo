package com.nscott.countrypathdemo.config;

import com.nscott.countrypathdemo.model.CountryEntity;
import com.nscott.countrypathdemo.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

/**
 * Provider that converts countries.json data into entity classes to be stored into the h2 database.
 * Runs on startup.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class RoutingDataProvider implements CommandLineRunner {

    private final CountryRepository countryRepository;
    private final ObjectMapper objectMapper;

    @Value("classpath:countries.json")
    private Resource jsonResource;

    @Override
    public void run(String... args) throws Exception {
        List<CountryEntity> countries = objectMapper.readValue(
                jsonResource.getInputStream(),
                objectMapper.getTypeFactory().constructCollectionType(List.class, CountryEntity.class)
        );

        countryRepository.saveAll(countries);
        log.info("{} country entities loaded.", countries.size());
    }
}
