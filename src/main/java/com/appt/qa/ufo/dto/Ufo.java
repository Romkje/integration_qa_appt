package com.appt.qa.ufo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Ufo {
    @JsonProperty("id")
    public Long id;

    @JsonProperty("occurred_at")
    public String occurredAt;

    @JsonProperty("city")
    public String city;

    @JsonProperty("state")
    public String state;

    @JsonProperty("country")
    public String country;

    @JsonProperty("shape")
    public String shape;

    @JsonProperty("duration_seconds")
    public Double durationSeconds;

    @JsonProperty("duration_text")
    public String durationText;

    @JsonProperty("description")
    public String description;

    @JsonProperty("reported_on")
    public String reportedOn;

    @JsonProperty("latitude")
    public Double latitude;

    @JsonProperty("longitude")
    public Double longitude;
}
