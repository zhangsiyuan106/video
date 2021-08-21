package com.boot.utils;

import com.boot.pojo.Article;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author memgzong
 * @create 2021/5/17
 */
//解析网页
@Component
public class HtmlParseUtil {
    public static void main(String[] args) throws IOException {
                parseJd("java").forEach(System.out::println);
        }

    public static  List<Article> parseJd(String keywords) throws IOException {
        String url = "https://search.jd.com/Search?keyword=" + keywords;
        //解析网页 document 返回就是js页面对象 document对象
        Document document = Jsoup.parse(new URL(url), 30000);
        //在js中可以使用的方法 都能使用
        Element goodsList = document.getElementById("J_goodsList");

        //获取所有的li元素
        Elements li = goodsList.getElementsByTag("li");
        List<Article> list = new ArrayList<>();
        for (Element element : li) {
            String title = element.getElementsByClass("p-name").eq(0).text();
            Article content=new Article();
            content.setHeadLine(title);
            list.add(content);
        }
        return list;
    }
}
