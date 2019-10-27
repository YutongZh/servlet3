package com.yt.servlet;

import com.yt.others.OrderFilter;
import com.yt.others.OrderListener;
import com.yt.others.OrderServlet;

import javax.servlet.*;
import javax.servlet.annotation.HandlesTypes;
import java.util.EnumSet;
import java.util.Set;

@HandlesTypes(value = {OrderService.class})
public class MyServletContainerInitializer implements ServletContainerInitializer {

    //可利用反射 在容器启动的时候做一些校验
    @Override
    public void onStartup(Set<Class<?>> set, ServletContext servletContext) throws ServletException {
        //arg0:父类感兴趣的类的子类型 接口的实现等
        //servletContext 与三大组件有关 代表当前web应用 注册三大组件
        for (Class<?> aClass : set) {
            System.out.println("tomcat启动加载的类路径：" + aClass);
        }

        //注册PayServlet组件
        ServletRegistration.Dynamic orderServlet = servletContext.addServlet("orderServlet", new OrderServlet());
        orderServlet.addMapping("/orderServlet");

        //注册监听器listener
        servletContext.addListener(OrderListener.class);

        //注册过滤器
        FilterRegistration.Dynamic orderFilter = servletContext.addFilter("orderFilter", OrderFilter.class);
        orderFilter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, "/");
    }
}
