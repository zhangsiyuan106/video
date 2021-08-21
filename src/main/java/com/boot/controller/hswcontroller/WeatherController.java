package com.boot.controller.hswcontroller;

import com.boot.pojo.Hot;
import com.boot.pojo.WeatherDetails;
import com.boot.utils.HotSearch;
import com.boot.utils.WeatherQuery;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static com.alibaba.fastjson.JSON.toJSONString;

/**
 * @author hsw
 * @date 2021/8/13
 */
@RestController
@RequestMapping("/api")
public class WeatherController {
    @ResponseBody
    @GetMapping("/getWeather")
    public String getWeather(String cityNum) {
        WeatherQuery weatherQuery = new WeatherQuery();
        List<WeatherDetails> weather = weatherQuery.getWeather(cityNum);
        return toJSONString(weather);
    }
}
