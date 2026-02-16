package com.nscott.countrypathdemo.constant;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Region {
    @JsonProperty("Africa")
    AFRICA,
    @JsonProperty("Americas")
    AMERICAS,
    @JsonProperty("Antarctic")
    ANTARCTIC,
    @JsonProperty("Asia")
    ASIA,
    @JsonProperty("Europe")
    EUROPE,
    @JsonProperty("Oceania")
    OCEANIA;
}
