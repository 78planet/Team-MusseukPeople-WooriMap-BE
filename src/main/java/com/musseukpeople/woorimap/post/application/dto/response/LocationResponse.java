package com.musseukpeople.woorimap.post.application.dto.response;

import java.math.BigDecimal;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LocationResponse {

    private BigDecimal latitude;

    private BigDecimal longitude;

    public LocationResponse(BigDecimal latitude, BigDecimal longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
