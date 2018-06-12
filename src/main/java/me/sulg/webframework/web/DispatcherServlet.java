package me.sulg.webframework.web;

import me.sulg.webframework.annotation.ResponseBody;
import me.sulg.webframework.annotation.ResponseJsp;
import me.sulg.webframework.context.ContextInit;
import me.sulg.webframework.define.HandlerDefine;
import me.sulg.webframework.define.RequestDefine;
import me.sulg.webframework.util.JsonUtil;
import me.sulg.webframework.util.ReflectionUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 请求分发Servlet
 */
@WebServlet(urlPatterns = "/*", loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet {

    private final static String REQUEST_HANDLER_MAP = "request_handler_map";
    private final static String BEAN_MAP = "bean_map";

    /**
     * 初始化
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        //初始化并获取Context
        Map<RequestDefine, HandlerDefine> requestMap = ContextInit.getRequestMap();
        Map<Class<?>, Object> beanMap = ContextInit.getBeanMap();

        //将Context存入ServletContext上下文中
        ServletContext servletContext = config.getServletContext();
        servletContext.setAttribute(REQUEST_HANDLER_MAP, requestMap);
        servletContext.setAttribute(BEAN_MAP, beanMap);
    }

    /**
     * 请求分发
     */
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取请求方法与请求路径
        String requestMethod = req.getMethod().toLowerCase();
        String requestPath = req.getPathInfo();

        //根据请求获取处理Handler
        RequestDefine requestDefine = new RequestDefine(requestMethod, requestPath);
        HandlerDefine handlerDefine = ((Map<RequestDefine, HandlerDefine>) getServletContext().getAttribute(REQUEST_HANDLER_MAP)).get(requestDefine);

        if(handlerDefine != null){
            //获取对应Controller类的Bean
            Object controllerBean = ((Map<Class<?>,Object>)getServletContext().getAttribute(BEAN_MAP)).get(handlerDefine.getCls());

            //获取request的参数map
            Map<String, String> paramMap = new HashMap<>();
            Enumeration<String> paramNames = req.getParameterNames();
            while (paramNames.hasMoreElements()) {
                String paramName = paramNames.nextElement();
                String paramValue = req.getParameter(paramName);
                paramMap.put(paramName, paramValue);
            }

            //调用handler方法处理请求
            Method actionMethod = handlerDefine.getMethod();
            Object result = ReflectionUtil.invokeMethod(controllerBean, actionMethod, paramMap);

            if(actionMethod.isAnnotationPresent(ResponseBody.class)){//有ResponseBody注解,结果转为json
                resp.setContentType("application/json");
                resp.setCharacterEncoding("utf-8");
                PrintWriter writer = resp.getWriter();
                String json = JsonUtil.toJson(result);
                writer.write(json);
                writer.flush();
                writer.close();
            }else if(actionMethod.isAnnotationPresent(ResponseJsp.class)){//有ResponseJsp注解，返回JSP页面
                String[] results = ((String)result).split(":");
                if(RequestDefine.REDIRECT.equals(results[0])){
                    //重定向方式
                    resp.sendRedirect(req.getContextPath() + results[1]);
                }else{
                    //forward方式 未实现setAttribute功能
                    req.getRequestDispatcher(results[1]).forward(req,resp);
                }
            }

        }else{//没有对应的处理器

        }

    }

}
