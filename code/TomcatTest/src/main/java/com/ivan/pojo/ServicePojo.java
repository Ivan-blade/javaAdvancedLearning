package com.ivan.pojo;

/**
 * @author hylu.ivan
 * @date 2021/9/12 下午1:09
 * @description
 */
public class ServicePojo {

    private String host;
    private String port;
    private String appBase;

    public ServicePojo() {
    }

    public ServicePojo(String host, String port, String appBase) {
        this.host = host;
        this.port = port;
        this.appBase = appBase;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getAppBase() {
        return appBase;
    }

    public void setAppBase(String appBase) {
        this.appBase = appBase;
    }

    @Override
    public String toString() {
        return "ServicePojo{" +
                "host='" + host + '\'' +
                ", port='" + port + '\'' +
                ", appBase='" + appBase + '\'' +
                '}';
    }
}
