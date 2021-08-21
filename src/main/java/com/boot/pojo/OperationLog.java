package com.boot.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @apiNote 日志
 * @author Yaming
 * @since 2019-01-24
 */
@Table(name = "operation_log")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OperationLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String content;

    private String description;

    private String ip;

    private String module;

    private String username;

    private Date createAt;

}