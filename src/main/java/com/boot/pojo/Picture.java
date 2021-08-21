package com.boot.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * @Author: zsy
 * @Description
 * @Date: Create in 9:43 2021/6/16
 */
@Data
@ToString
@Document("picture_file")
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Picture implements Serializable {
    @Id
    //文件id
    private String pictureId;
    //文件url
    private String pictureUrl;
    //文章的id
    private String articleId;

}
