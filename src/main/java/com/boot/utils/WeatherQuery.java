package com.boot.utils;

import com.boot.pojo.WeatherDetails;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hsw
 * @date 2021/8/13
 */
public class WeatherQuery {
    public List<WeatherDetails> getWeather(String cityNum) {
        try {
            String urlStr = "https://tianqi.2345.com/seventh-" + cityNum + ".htm";
            final Document doc = Jsoup.connect(urlStr).get();//获取html
            //天气温度
            Elements docElementsByClass = doc.getElementsByClass("seven-day");
            String text = docElementsByClass.text();
            String[] s = text.split(" ");
            ArrayList<WeatherDetails> list = new ArrayList<>();
            int j = 0;
            for (int i = 0; i < 6; i++) {
                WeatherDetails w = new WeatherDetails();
                w.setDayC(s[i + (j * 5)]).setDayS(s[i + 1 + (j * 5)])
                        .setWeather(s[i + 2 + (j * 5)])
                        .setTemperature(s[i + 3 + (j * 5)])
                        .setWindDirection(s[i + 4 + (j * 5)])
                        .setQuality(s[i + 5 + (j * 5)]);
                list.add(w);
                j++;
            }
            //空气质量
            Elements airElement = doc.getElementsByClass("aqi-map");
            String text1 = airElement.text();
            String[] s1 = text1.split(" ");
            list.get(0).setAir(s1);
            return list;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }
}
