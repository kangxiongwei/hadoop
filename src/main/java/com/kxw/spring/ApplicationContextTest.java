package com.kxw.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * Created by kangxiongwei on 2017/4/26.
 */
@Component
public class ApplicationContextTest implements ApplicationContextAware, InitializingBean {


    public static void main(String[] args) {
        new ClassPathXmlApplicationContext("beans.xml");
    }



    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("hello world");
        Map<String, Object> map = applicationContext.getBeansWithAnnotation(RpcService.class);
        for (Object obj: map.values()) {
            Annotation annotation = obj.getClass().getAnnotation(RpcService.class);
            System.out.println(annotation);
            RpcService rpcService = (RpcService) annotation;
            System.out.println(rpcService.value());
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("good bye, world");
    }
}
