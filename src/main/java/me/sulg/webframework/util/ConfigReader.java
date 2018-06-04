package me.sulg.webframework.util;

import me.sulg.webframework.constant.ConfigConstant;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * 配置文件读取类
 */
public final class ConfigReader {

    private static final Properties properties;

    //静态初始化配置文件
    static {
        properties = new Properties();
        String configFileName = ConfigReader.class.getResource("/").getPath() + ConfigConstant.CONFIG_FILE;
        try {
            properties.load(new FileInputStream(configFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取配置值
     */
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}
