package com.atguigu.mybatisplus.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

/**
 * @author changchen
 * @create 2019-11-05 23:31
 */
@Data
public class User {
    /**
     * ID_WORKER 生成全局唯一id
     * AUTO 主键自增 表设计时需要添加自增
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private Integer age;
    private String email;
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    @Version
    private Integer version;
    @TableLogic
    private Integer deleted;
}
