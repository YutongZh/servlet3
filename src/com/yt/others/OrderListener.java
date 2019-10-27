package com.yt.others;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class OrderListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println("OrderListener init successful===========");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        System.out.println("OrderListener destroy successful===========");
    }
}
