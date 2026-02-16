package com.nscott.countrypathdemo.web;

import com.nscott.countrypathdemo.model.RoutingResponse;
import com.nscott.countrypathdemo.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/routing")
public class RoutingController {

    private final CountryService countryService;

    @GetMapping("/{startId}/{destId}")
    public RoutingResponse getById(@PathVariable String startId, @PathVariable String destId) {
        return new RoutingResponse(countryService.getPathBetweenCountries(startId, destId));
    }
}
