package com.huawl.spring.servlet;

import com.huawl.spring.annocation.Controller;
import com.huawl.spring.annocation.RequestMapping;
import com.huawl.spring.context.AbstractApplicationContext;
import com.huawl.spring.context.ApplicationContext;
import com.huawl.spring.mvc.HandlerMapping;
import com.huawl.spring.util.AnnotationUtil;
import com.huawl.tomcat.connector.HttpServletRequest;
import com.huawl.tomcat.connector.HttpServletResponse;
import com.huawl.tomcat.processor.HttpServlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 将所有的controller组装
 * @author Luther
 * @version 1.0
 * @date 2021/8/17 15:49
 */
public class DispatcherServlet implements HttpServlet {

    private ApplicationContext applicationContext;

    public DispatcherServlet(ApplicationContext applicationContext){
        this.applicationContext = applicationContext;
    }

    private Map<String, HandlerMapping> handlerMappingMap;

    @Override
    public void init() {
        AbstractApplicationContext abstractApplicationContext = (AbstractApplicationContext) applicationContext;
        try {
            handlerMappingMap = new HashMap<>();
            List<Object> controllers =  abstractApplicationContext.getBeanByAnnotation(Controller.class);
            for (int i = 0; i < controllers.size(); i++) {
                Object controller = controllers.get(i);
                Class clazz = controller.getClass();
                RequestMapping baseMapping = (RequestMapping) clazz.getAnnotation(RequestMapping.class);
                String baseRequestUrl = baseMapping.value();
                Method[] methods = clazz.getMethods();
                for (int j = 0; j < methods.length; j++) {
                    Method method = methods[j];
                    if(!AnnotationUtil.isMethod(method,false,RequestMapping.class)){
                        continue;
                    }
                    RequestMapping oneMapping = (RequestMapping) method.getAnnotation(RequestMapping.class);
                    String oneRequestUrl = oneMapping.value();
                    String key = baseRequestUrl + oneRequestUrl;
                    HandlerMapping handlerMapping = new HandlerMapping();
                    handlerMapping.setTarget(controller);
                    handlerMapping.setMethod(method);
                    handlerMappingMap.put(key,handlerMapping);
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestUrl = request.getUri();
        HandlerMapping handlerMapping = handlerMappingMap.get(requestUrl);
        try {
            Object result = handlerMapping.getMethod().invoke(handlerMapping.getTarget(),request,response);
            response.setBody(result);
        } catch (Exception e) {
            e.printStackTrace();
            response.setBody(e.getMessage());
        }
    }

}
