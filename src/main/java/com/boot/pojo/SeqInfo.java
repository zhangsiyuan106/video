package com.boot.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * @author lm
 * @version 1.8
 * @date 2021/7/19 16:07
 */
//定义序列实体类SeqInfo: 存储每个集合的ID记录
@Document(collection = "sequence")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeqInfo implements Serializable {
        @Id
        private String id;//主键
        private Long seqId;//序列值
        private String collName;//集合名称
}
