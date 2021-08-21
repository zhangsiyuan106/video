package com.boot.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lm
 * @version 1.8
 * @date 2021/7/31 21:40
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class News {
        private Integer id;
        private String title;
        private String content;
        private String url;
}


