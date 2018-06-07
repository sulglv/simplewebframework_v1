package me.sulg.webframework.define;

/**
 * 请求类
 */
public class RequestDefine {

    /**
     * 请求方法
     */
    public static final String GET = "get";
    public static final String POST= "post";
    public static final String PUT= "put";
    public static final String DELETE= "delete";

    /**
     * 请求方式 GET/POST/PUT/DELETE等
     */
    private String requestMethod;

    /**
     * 请求url路径
     */
    private String requestPath;

    public RequestDefine(String requestMethod, String requestPath) {
        this.requestMethod = requestMethod;
        this.requestPath = requestPath;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public String getRequestPath() {
        return requestPath;
    }

    public void setRequestPath(String requestPath) {
        this.requestPath = requestPath;
    }
}
