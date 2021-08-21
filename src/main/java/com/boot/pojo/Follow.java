package com.boot.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Follow {
    private int fid;
    private int userid;
    private int followid;

    public Follow(int userid, int followid) {
        this.userid = userid;
        this.followid = followid;
    }
}
