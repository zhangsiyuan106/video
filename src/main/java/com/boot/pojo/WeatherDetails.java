package com.boot.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author hsw
 * @date 2021/8/13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class WeatherDetails {
    private String dayC;
    private String dayS;
    private String weather;
    private String temperature;
    private String windDirection;
    private String quality;
    private String[] air;
}
