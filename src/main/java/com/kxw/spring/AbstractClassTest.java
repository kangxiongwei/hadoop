package com.kxw.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by kangxiongwei3 on 2017/5/11 17:06.
 */
@Component
public class AbstractClassTest {

    @Resource
    private AbstractClass concreteClass;

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        AbstractClassTest test = (AbstractClassTest) context.getBean("abstractClassTest");
        test.doConcreteClass();
    }

    public void doConcreteClass() {
        this.concreteClass.say();
    }

}
