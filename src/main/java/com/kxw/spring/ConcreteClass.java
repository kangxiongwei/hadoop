package com.kxw.spring;

import org.springframework.stereotype.Service;

/**
 * Created by kangxiongwei3 on 2017/5/11 17:07.
 */
@Service
public class ConcreteClass extends AbstractClass {

    @Override
    public void say() {
        System.out.println("I am " + this.getClass().getSimpleName());
    }
}
