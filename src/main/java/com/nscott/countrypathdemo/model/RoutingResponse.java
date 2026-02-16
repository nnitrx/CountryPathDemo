package com.nscott.countrypathdemo.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Response data for RoutingController
 */
@Data
public class RoutingResponse implements Serializable {

    private List<String> route;

    public RoutingResponse(List<String> route) {
        this.route = route;
    }
}
