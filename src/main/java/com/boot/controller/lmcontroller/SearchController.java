package com.boot.controller.lmcontroller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.boot.constant.LexJSONResult;
import com.boot.pojo.Article;
import com.boot.pojo.News;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lm
 * @version 1.8
 * @date 2021/8/1 13:32
 */
@RestController
@RequestMapping("/searchArticle")
public class SearchController {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @GetMapping()
    public Object autoComplete(String term) throws Exception {
        Request request = new Request("GET","article/_search");
        request.setJsonEntity(String.format("{" +
                "  \"suggest\": {" +
                "    \"article_suggest\": {" +
                "      \"prefix\": \"%s\"," +
                "      \"completion\": {" +
                "        \"field\": \"headline\"," +
                "        \"size\":5," +
                "        \"skip_duplicates\":true" +
                "      }" +
                "    }" +
                "  }" +
                "}",term));

        Response response = restHighLevelClient.getLowLevelClient().performRequest(request);
        String s=  EntityUtils.toString(response.getEntity());

        JSONObject jsonObject = JSONObject.parseObject(s);
        JSONArray suggests = jsonObject.getJSONObject("suggest").getJSONArray("article_suggest");
        JSONArray options = suggests.getJSONObject(0).getJSONArray("options");
        List<String> results = new ArrayList<>();
        for (int i = 0; i < options.size(); i++) {
            JSONObject opt = options.getJSONObject(i);
            results.add(opt.getString("text"));
        }
        return results;

    }
    @GetMapping("/article")
    public LexJSONResult query(@RequestParam("term") String term) throws Exception{
        /**
         * 1.????????????????????????ES??????????????????????????????????????????????????????????????????????????????
         * 2.???????????????????????????title??????content?????????????????????????????????????????????????????????????????? title ??? content.
         */
        Request request = new Request("GET", "article/_search");
        request.setJsonEntity(String.format("{\n" +
                "  \"_source\": [\"id\",\"content\",\"headline\",\"publishtime\",\"username\",\"userid\"], \n" +
                "  \"query\": {\n" +
                "    \n" +
                "    \"multi_match\": {\n" +
                "      \"query\":\"%s\",\n" +
                "      \"fields\": [\"headline\",\"content\"]\n" +
                "    }\n" +
                "  },\n" +
                "  \"highlight\": {\n" +
                "    \"pre_tags\": \"<span class='highLight' style='color:red'>\",\n" +
                "    \"post_tags\": \"</span>\", \n" +
                "    \"fields\": {\n" +
                "      \"headline\": {},\n" +
                "      \"content\": {}\n" +
                "    }\n" +
                "  }\n" +
                "}", term));

        Response response = restHighLevelClient.getLowLevelClient().performRequest(request);

        JSONObject jsonObject = JSONObject.parseObject(EntityUtils.toString(response.getEntity()));

        JSONArray hits = jsonObject.getJSONObject("hits").getJSONArray("hits");

        List<Article> results = new ArrayList<>();

        for (int i = 0; i < hits.size(); i++) {
            Article article = new Article();
            JSONObject hit = hits.getJSONObject(i);
            JSONObject highLight = hit.getJSONObject("highlight");  //???????????????????????????

            JSONObject _source = hit.getJSONObject("_source"); //????????????????????????

            JSONArray highLightTitle = highLight.getJSONArray("headline");  //??????????????? title ??????
            JSONArray highLightContent = highLight.getJSONArray("content");

            if(null != highLightTitle) {
                StringBuffer highLightTitleStringBuffer = new StringBuffer();
                for (int j = 0; j < highLightTitle.size(); j++) {
                    String titleSegment = highLightTitle.getString(j);
                    highLightTitleStringBuffer.append(titleSegment);
                }
                article.setHeadLine(highLightTitleStringBuffer.toString());
            }else {  // ?????????????????????????????????????????????????????????
                article.setHeadLine(_source.getString("headline"));
            }

            if(null != highLightContent) {
                StringBuffer highLightContentStringBuffer = new StringBuffer();
                for (int j = 0; j < highLightContent.size(); j++) {
                    String contentSegment = highLightContent.getString(j);
                    highLightContentStringBuffer.append(contentSegment);
                }
                article.setContent(highLightContentStringBuffer.toString());
            }else {  // ?????????????????????????????????????????????????????????
                article.setContent(_source.getString("content"));
            }
            article.setId(_source.getLong("id"));
            article.setUserName(_source.getString("username"));
            article.setPublishtime(_source.getDate("publishtime"));
            article.setUserid(_source.getIntValue("userid"));
            results.add(article);
        }
        return LexJSONResult.ok(results);
    }
}
