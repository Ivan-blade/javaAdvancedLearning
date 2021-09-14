package com.ivan.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author hylu.ivan
 * @date 2021/9/12 下午10:04
 * @description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@TableName("user")
public class User {

    @TableId(type = IdType.AUTO)
    private long id;

    private String username;

    private String password;

}
