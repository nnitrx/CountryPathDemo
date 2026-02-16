package com.nscott.countrypathdemo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nscott.countrypathdemo.constant.Region;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class CountryEntity implements Serializable {
    @Id
    @JsonProperty("cca3")
    private String id;

    private Region region;

    private List<Double> latlng;

    private List<String> borders;

    @Transient
    public Boolean isIsland() {
        return borders == null || borders.isEmpty();
    }
}
