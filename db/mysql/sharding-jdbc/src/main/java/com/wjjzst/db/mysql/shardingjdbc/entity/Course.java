package com.wjjzst.db.mysql.shardingjdbc.entity;

import lombok.Data;

/**
 * @Author: Wjj
 * @Date: 2020/9/17 1:15 上午
 * @desc:
 */
@Data
public class Course {
    private Long cid;
    private String cname;
    private Long userId;
    private String cstatus;
}
