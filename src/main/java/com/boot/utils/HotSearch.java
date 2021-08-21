package com.boot.utils;

import com.boot.pojo.Desc;
import com.boot.pojo.Hot;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

/**
 * @author hsw
 * @date 2021/8/6
 */
public class HotSearch {

    public ArrayList<Hot> HotSearch() {
        try {

            ArrayList<Hot> hotlist = new ArrayList<>();
            String urlStr = "https://s.weibo.com/top/summary";
            final Document doc = Jsoup.connect(urlStr).get();//获取html
            Elements trs = doc.select("tbody").select("tr");//获取tbody下的所有tr下的html内容
            for (org.jsoup.nodes.Element tr : trs) {
                Elements tds = tr.select("td");
                String rank = tds.get(0).text();//排名
                String num = tds.get(1).select("span").text();//热度指数
                String title = tds.get(1).select("a").text();//热搜标题
                String url = tds.get(1).select("a").attr("href");//热搜URL网址(相对地址)
                String baseurl = "https://s.weibo.com";//和上述url组成完整可访问的单个热搜URL
                //以 排名+热搜标题+热搜指数+有效URL的形式输出
                Hot hot = new Hot();
                Hot hot1 = hot.setNum(num).setRank(rank).setTitle(title).setUrl(baseurl + url);
                hotlist.add(hot1);
            }
            return hotlist;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Desc> DescList(String urlStr) {
        try {
            final Document doc = Jsoup.connect(urlStr).get();//获取html
            String title = null;
            Elements select0 = doc.getElementsByClass("card-topic-a");
            String text0 = select0.text();
            String[] s0 = text0.split(" ");
            if (s0.length > 5) {
                title = s0[0] + s0[3] + s0[4];
            }


            Elements select = doc.getElementsByClass("card");
            String text = select.text();
            String[] s = text.split(" ");
            String dy = s[0];
            ArrayList<Desc> descs = new ArrayList<>();
            int index;
            for (int i = 1; i < s.length; i++) {
                if (Objects.equals(s[i], "投诉")) {
                    index = i + 1;
                    Desc desc = new Desc();
                    Desc desc1 = desc.setName(s[index]).setInf(s[index + 1]).setDy(dy).setTitle(title);
                    descs.add(desc1);
                }
            }
            int flag = descs.size();
            int num = 0;
            int sum = 0;
            Elements pngs = doc.select("img[src~=(?i)\\.(png|jpe?g)]");
            //遍历元素
            for (Element e : pngs) {
                String src = e.attr("src");//获取img中的src路径
                if (src.contains("https")) {
                    if (sum != 0) {
                        if (num != flag) {
                            descs.get(num).setImg(src);
                            num++;
                        } else {
                            break;
                        }
                    }
                }
                sum++;
            }
            return descs;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
