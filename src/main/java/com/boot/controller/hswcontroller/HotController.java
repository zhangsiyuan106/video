package com.boot.controller.hswcontroller;

import com.boot.pojo.Desc;
import com.boot.pojo.Hot;
import com.boot.utils.HotSearch;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

import static com.alibaba.fastjson.JSON.toJSONString;

/**
 * @author hsw
 * @date 2021/8/6
 */
@RestController
@RequestMapping("/api")
public class HotController {

    @ResponseBody
    @GetMapping("/hot")
    public String hot() {
        HotSearch hotSearch = new HotSearch();
        ArrayList<Hot> hots = hotSearch.HotSearch();
        return toJSONString(hots);

    }

    @ResponseBody
    @GetMapping("/desc")
    public String Descs(@RequestParam("url") String url) {
        System.out.println(url);
        HotSearch hotSearch = new HotSearch();
        ArrayList<Desc> descs = hotSearch.DescList(url);
        return toJSONString(descs);
    }

}
