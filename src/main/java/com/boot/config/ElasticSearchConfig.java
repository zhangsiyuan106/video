package com.boot.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author memgzong  es服务器配置
 * @create 2021/5/16
 */
//配置 RestHighLevelClient ES 高级客户端
@Configuration
public class ElasticSearchConfig {
    @Bean //注入到spring
    public RestHighLevelClient restHighLevelClient(){
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(new HttpHost("127.0.0.1",9200,"http")));
        return client;
    }
}
