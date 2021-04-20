package com.Ivan.io;

import java.io.InputStream;

/**
 * @author apple
 * @date 2021/4/2 上午10:37
 * @description
 */
public class Resources {

    // 根据配置文件路径，将配置文件加载为字节输入流存储在内存中
    public static InputStream getResourceAsStream(String path) {

        InputStream resourceAsStream = Resources.class.getClassLoader().getResourceAsStream(path);
        return resourceAsStream;
    }
}
