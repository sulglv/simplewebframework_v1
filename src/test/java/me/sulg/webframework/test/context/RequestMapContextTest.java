package me.sulg.webframework.test.context;

import me.sulg.webframework.context.RequestMapContext;
import me.sulg.webframework.define.HandlerDefine;
import me.sulg.webframework.define.RequestDefine;

import java.util.Map;

public class RequestMapContextTest {
    public static void main(String[] args) {
        Map<RequestDefine, HandlerDefine> requestMap = RequestMapContext.getRequestMap();
        for (Map.Entry<RequestDefine, HandlerDefine> entry : requestMap.entrySet()) {
            System.out.println("request method:  " + entry.getKey().getRequestMethod());
            System.out.println("request path:    " + entry.getKey().getRequestPath());
            System.out.println("handler class:   " + entry.getValue().getCls());
            System.out.println("handler method:  " + entry.getValue().getMethod());
            System.out.println();
        }

    }
}
