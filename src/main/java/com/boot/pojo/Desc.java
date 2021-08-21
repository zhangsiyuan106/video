package com.boot.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author hsw
 * @date 2021/8/6
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Desc {
    private String title;
    private String name;
    private String inf;
    private String dy;
    private String img;
}
