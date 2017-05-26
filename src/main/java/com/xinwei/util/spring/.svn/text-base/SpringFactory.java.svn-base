package com.xinwei.util.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by shaoyong on 2016/6/20.
 */
public class SpringFactory {

    private static ClassPathXmlApplicationContext factory = new ClassPathXmlApplicationContext("classpath:applicationContext.xml") ;

    private SpringFactory(){}

    public static Object getBean(String name){
        return factory.getBean(name);

    }

}
