package com.ivan.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author hylu.ivan
 * @date 2021/9/29 下午9:32
 * @description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MethodInfo {

    private String methodname;

    private long times;

    private long endTimes;
}
