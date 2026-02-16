package com.nscott.countrypathdemo.web;

import com.nscott.countrypathdemo.model.CountryEntity;
import com.nscott.countrypathdemo.repository.CountryRepository;
import com.nscott.countrypathdemo.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/routing")
public class RoutingController {

    private final CountryService countryService;

    @GetMapping("/{startId}/{destId}")
    public List<String> getById(@PathVariable String startId, @PathVariable String destId) {
        return countryService.getPathBetweenCountries(startId, destId);
    }
}
